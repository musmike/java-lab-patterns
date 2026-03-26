package pl.lab.factory;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.electronicboards.firmware.*;
import pl.lab.factory.electronicboards.firmware.programmers.InternalProgrammer;
import pl.lab.factory.electronicboards.firmware.programmers.STLinkDevice;
import pl.lab.factory.electronicboards.firmware.programmers.SeggerJLinkDevice;
import pl.lab.factory.manufacturing.*;
import pl.lab.factory.mediator.CentralControlMediator;
import pl.lab.factory.mediator.ProductionDepartment;
import pl.lab.factory.mediator.ReportingDepartment;
import pl.lab.factory.mediator.WarehouseDepartment;
import pl.lab.factory.orders.*;
import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.reporting.*;
import pl.lab.factory.reporting.reportingfacade.ReportFormat;
import pl.lab.factory.reporting.reportingfacade.ReportType;
import pl.lab.factory.reporting.reportingfacade.ReportingFacade;
import pl.lab.factory.warehouse.*;
import pl.lab.factory.scheduling.ProductionBatch;
import pl.lab.factory.scheduling.SingleOrderTask;
import pl.lab.factory.warehouse.packing.AntiStaticBagDecorator;
import pl.lab.factory.warehouse.packing.FoamProtectionDecorator;
import pl.lab.factory.warehouse.packing.PackageFactory;
import pl.lab.factory.warehouse.packing.PackageImplementor;
import pl.lab.factory.warehouse.shipping.*;
import pl.lab.factory.warehouse.shipping.command.ShipOrderCommand;
import pl.lab.factory.warehouse.shipping.command.ShippingCommand;
import pl.lab.factory.warehouse.shipping.command.ShippingManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SystemLogger.INSTANCE.logInfo("MAIN", "This is the PCB/Electronic board factory!");

        // Creating orders

        StandardBoardFactory boardFactory = new StandardBoardFactory();
        OrderProcessor pcbProcessor = new BaseBoardOrderProcessor();
        OrderProcessor electronicBoardProcessor = new ElectronicBoardOrderProcessor();

        OrderSpecification rohs = OrderSpecification.builder()
                .voltageTolerance(5.0)
                .thicknessTolerance(0.1)
                .addCertificate("RoHS")
                .addCertificate("ISO9001")
                .qualityStandard("Class 2")
                .build();

        OrderSpecification specPcb = rohs.clone();
        specPcb.addCertificate("IPC-A-610");
        specPcb.displayCertificates("ORD-001");

        OrderSpecification specElectronic = rohs.clone();
        specElectronic.setQualityStandard("Class 3");
        specElectronic.setVoltageTolerance(2.5);

        FirmwareAdapter firmwareAdapter;

        BaseElectronicBoard electronicBoardPrototype = ElectronicBoardFactory.createBoard(ElectronicType.HIGHLY_DEPENDANT);

        BoardPrototypes boardPrototypes = new BoardPrototypes();
        boardPrototypes.registerPrototype("highly-dependant-v1", electronicBoardPrototype);

        BaseElectronicBoard electronicBoardA = boardPrototypes.createFromPrototype("highly-dependant-v1");
        firmwareAdapter = new HighLevelFirmwareAdapter(new HighLevelFirmware("v1.0.0-release"));
        InternalProgrammer internalProgrammer = new InternalProgrammer(new SeggerJLinkDevice());
        internalProgrammer.program(electronicBoardA, firmwareAdapter);
        BaseElectronicBoard electronicBoardB = boardPrototypes.createFromPrototype("highly-dependant-v1");
        firmwareAdapter = new ProprietaryFirmwareAdapter(new ProprietaryFirmware("v1.1.0-hotfix"));
        internalProgrammer = new InternalProgrammer(new STLinkDevice());
        internalProgrammer.program(electronicBoardB, firmwareAdapter);

        SystemLogger.INSTANCE.logInfo("PROTOTYPE", "Prototype: firmware=" + electronicBoardPrototype.getFirmware());
        SystemLogger.INSTANCE.logInfo("PROTOTYPE", "Clone A:   firmware=" + electronicBoardA.getFirmware());
        SystemLogger.INSTANCE.logInfo("PROTOTYPE", "Clone B:   firmware=" + electronicBoardB.getFirmware());

        BaseBoard boardA = boardFactory.createBoard();

        ProductionOrder orderA = ProductionOrderFactory.createOrder("ORD-001", boardA, 10, specPcb);
        ProductionOrder orderB = ProductionOrderFactory.createOrder("ORD-002", electronicBoardA, 3, specElectronic);
        ProductionOrder orderC = ProductionOrderFactory.createOrder("ORD-003", electronicBoardB, 2, specElectronic);
        ProductionOrder orderBaseClone = orderA.clone("ORD-004");
        ProductionOrder orderElectronicClone = orderB.clone("ORD-005");

        List<ProductionOrder> allOrders = new ArrayList<>(List.of(orderA, orderB, orderC, orderBaseClone, orderElectronicClone));


        // CentralControlMediator

        LegacyWarehouseSystem legacyWarehouse = new LegacyWarehouseSystem();
        Set<String> unapproved = Set.of("ORD-999", "ORD-888");
        WarehouseService warehouse = new WarehouseAccessProxy(
                new WarehouseAdapter(legacyWarehouse),
                unapproved
        );

        BoardReporter boardReporter = new CachingBoardReporterProxy(
                new LegacyAuditSystemAdapter(new LegacyAuditSystem())
        );

        CentralControlMediator mediator = new CentralControlMediator();

        ProductionDepartment prodDept = new ProductionDepartment(mediator, pcbProcessor, electronicBoardProcessor);
        WarehouseDepartment wareDept = new WarehouseDepartment(mediator, warehouse);
        ReportingDepartment repDept = new ReportingDepartment(mediator, boardReporter);

        mediator.setProductionDepartment(prodDept);
        mediator.setWarehouseDepartment(wareDept);
        mediator.setReportingDepartment(repDept);



        // Order queue

        ProductionBatch weeklyPlan = new ProductionBatch("Weekly plan");
        ProductionQueue queue = ProductionQueue.getInstance();

        allOrders.forEach(queue::addOrder);
        queue.printQueue();

        TimeUnit.SECONDS.sleep(1);

        while (!queue.isEmpty()) {
            ProductionOrder order = queue.nextOrder();
            weeklyPlan.add(new SingleOrderTask(order, prodDept));
        }

        System.out.println(weeklyPlan.getDescription(""));
        weeklyPlan.execute();



        // Warehouse

        ProductionOrder unapprovedOrder = orderA.clone("ORD-999");
        warehouse.storeOrder(unapprovedOrder);

        legacyWarehouse.printStorage();
        warehouse.printWarehouse();

        PackageImplementor cardboardBoxPackaging = new FoamProtectionDecorator(new AntiStaticBagDecorator(PackageFactory.getPackage("cardboard")));
        PackageImplementor plasticBoxPackaging = new FoamProtectionDecorator(new AntiStaticBagDecorator(PackageFactory.getPackage("plastic")));

        ShippingStrategy standardShipping = new StandardShipping(cardboardBoxPackaging);
        ShippingStrategy priorityShipping = new PriorityShipping(plasticBoxPackaging);

        ShippingFacade shippingFacade = new ShippingFacade(warehouse);
        ShippingManager shippingManager = new ShippingManager();

        ShippingCommand shipCommandB = new ShipOrderCommand(shippingFacade, priorityShipping, orderB);
        ShippingCommand shipCommandA = new ShipOrderCommand(shippingFacade, standardShipping, orderA);

        shippingManager.processCommand(shipCommandB);
        shippingManager.processCommand(shipCommandA);

        shippingManager.undoLastAction();

        warehouse.printWarehouse();



        // Reports

        // not completed order
        ProductionOrder orderD = ProductionOrderFactory.createOrder("ORD-006", boardA, 5, specPcb);
        allOrders.add(orderD);

        ReportingFacade.generate(ReportType.SUMMARY, ReportFormat.CONSOLE, allOrders);
        ReportingFacade.generate(ReportType.SUMMARY, ReportFormat.CSV, allOrders);
        ReportingFacade.generate(ReportType.COMPLETED_ONLY, ReportFormat.CONSOLE, allOrders);
        ReportingFacade.generate(ReportType.COMPLETED_ONLY, ReportFormat.CSV, allOrders);

        orderD.setStatus(OrderStatus.COMPLETED);
        orderD.setStatus(OrderStatus.PACKED);
        orderD.setStatus(OrderStatus.SHIPPED);

        orderD.restoreStatus();
        orderD.restoreStatus();
        orderD.restoreStatus();
        orderD.restoreStatus();

        SystemLogger.INSTANCE.logInfo("MAIN", "All orders processed. End.");
    }
}