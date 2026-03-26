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
import pl.lab.factory.orders.command.*;
import pl.lab.factory.orders.iterator.OrderCollection;
import pl.lab.factory.orders.iterator.OrderIterator;
import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.reporting.*;
import pl.lab.factory.reporting.interpreter.*;
import pl.lab.factory.reporting.reportingfacade.ReportFormat;
import pl.lab.factory.reporting.reportingfacade.ReportType;
import pl.lab.factory.reporting.reportingfacade.ReportingFacade;
import pl.lab.factory.warehouse.*;
import pl.lab.factory.scheduling.ProductionBatch;
import pl.lab.factory.scheduling.SingleOrderTask;
import pl.lab.factory.warehouse.interpreter.OrderAndExpression;
import pl.lab.factory.warehouse.interpreter.OrderExpression;
import pl.lab.factory.warehouse.interpreter.OrderMinQuantityExpression;
import pl.lab.factory.warehouse.interpreter.OrderStatusExpression;
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

        // -- Creating orders --

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


        // -- CentralControlMediator --

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



        // -- Order queue --

        ProductionBatch weeklyPlan = new ProductionBatch("Weekly plan");
        ProductionQueue queue = ProductionQueue.getInstance();

        SystemLogger.INSTANCE.logInfo("MAIN", "=== COMMAND PATTERN TEST (UNDO/REDO FOR QUEUE) ===");

        // Invoker
        QueueCommandInvoker queueCommandInvoker = new QueueCommandInvoker();

        // Concrete Commands
        OrderQueueCommand addA = new AddOrderCommand(orderA);
        OrderQueueCommand addB = new AddOrderCommand(orderB);
        OrderQueueCommand clearAll = new ClearQueueCommand();

        queueCommandInvoker.executeCommand(addA);
        queueCommandInvoker.executeCommand(addB);
        queue.printQueue(); // State: [ orderA, orderB]

        OrderQueueCommand removeA = new RemoveOrderCommand(orderA);
        queueCommandInvoker.executeCommand(removeA);
        queue.printQueue(); // State: [orderB]

        queueCommandInvoker.undo();
        queue.printQueue(); // State: [orderA, orderB]

        queueCommandInvoker.executeCommand(clearAll);
        queue.printQueue(); // State: empty

        queueCommandInvoker.undo();
        queue.printQueue(); // State: [orderA, orderB]

        queueCommandInvoker.executeCommand(clearAll);
        queue.printQueue(); // State: empty

        OrderQueueCommand addAllOrders = new AddMultipleOrdersCommand(allOrders);
        queueCommandInvoker.executeCommand(addAllOrders);
        queue.printQueue(); // State: allOrders

        TimeUnit.SECONDS.sleep(1);

        while (!queue.isEmpty()) {
            ProductionOrder order = queue.nextOrder();
            weeklyPlan.add(new SingleOrderTask(order, prodDept));
        }

        System.out.println(weeklyPlan.getDescription(""));
        weeklyPlan.execute();



        // -- Warehouse --

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

        // Filtering
        OrderExpression isCompleted = new OrderStatusExpression(OrderStatus.COMPLETED);
        OrderExpression isLarge = new OrderMinQuantityExpression(5);

        OrderExpression complexRule = new OrderAndExpression(isCompleted, isLarge);

        SystemLogger.INSTANCE.logInfo("INTERPRETER", "Filtering completed orders with quantity >= 5");
        allOrders.stream()
                .filter(complexRule::interpret)
                .forEach(o -> SystemLogger.INSTANCE.logInfo("[FILTERING]", "Match found: " + o.getId()));

        // -- Reports --

        // not completed order example
        ProductionOrder orderD = ProductionOrderFactory.createOrder("ORD-006", boardA, 5, specPcb);
        allOrders.add(orderD);

        // Reporting Facade

        ReportingFacade.generate(ReportType.SUMMARY, ReportFormat.CONSOLE, allOrders);
        ReportingFacade.generate(ReportType.SUMMARY, ReportFormat.CSV, allOrders);
        ReportingFacade.generate(ReportType.COMPLETED_ONLY, ReportFormat.CONSOLE, allOrders);
        ReportingFacade.generate(ReportType.COMPLETED_ONLY, ReportFormat.CSV, allOrders);

        // Reporting Interpreter

        AbstractExpression query1 = new StatusExpression(OrderStatus.COMPLETED);

        AbstractExpression query2 = new AndExpression(
            new StatusExpression(OrderStatus.PENDING),
            new QuantityExpression("<", 6)
        );

        AbstractExpression query3 = new OrExpression(
            new TypeExpression("BaseBoardProductionOrder"),
            new StatusExpression(OrderStatus.COMPLETED)
        );

        ReportingFacade.generateDynamic(query1, ReportFormat.CONSOLE, allOrders);
        ReportingFacade.generateDynamic(query2, ReportFormat.CONSOLE, allOrders);
        ReportingFacade.generateDynamic(query3, ReportFormat.CSV, allOrders);


        // Order Status Memento
        orderD.setStatus(OrderStatus.COMPLETED);
        orderD.setStatus(OrderStatus.PACKED);
        orderD.setStatus(OrderStatus.SHIPPED);

        orderD.restoreStatus();
        orderD.restoreStatus();
        orderD.restoreStatus();
        orderD.restoreStatus();


        SystemLogger.INSTANCE.logInfo("MAIN", "=== ORDER ITERATOR PATTERN TEST ===");

        ProductionOrder orderE = ProductionOrderFactory.createOrder("ITER-01", boardA, 2, specPcb);
        ProductionOrder orderF = ProductionOrderFactory.createOrder("ITER-02", electronicBoardB, 8, specElectronic);
        ProductionOrder orderG = ProductionOrderFactory.createOrder("ITER-03", boardA, 5, specPcb);
        ProductionOrder orderH = ProductionOrderFactory.createOrder("ITER-04", electronicBoardB, 9, specElectronic);
        ProductionOrder orderI = ProductionOrderFactory.createOrder("ITER-05", boardA, 6, specPcb);
        ProductionOrder orderJ = ProductionOrderFactory.createOrder("ITER-06", electronicBoardB, 7, specElectronic);

        orderE.setStatus(OrderStatus.COMPLETED);
        orderF.setStatus(OrderStatus.COMPLETED);

        OrderCollection orderCollection = ProductionQueue.getInstance();
        queueCommandInvoker.executeCommand(clearAll);

        orderCollection.addOrder(orderE);
        orderCollection.addOrder(orderF);
        orderCollection.addOrder(orderG);
        orderCollection.addOrder(orderH);
        orderCollection.addOrder(orderI);
        orderCollection.addOrder(orderJ);

        System.out.println("\n--- Searching for all orders with status: COMPLETED ---");
        OrderIterator completedOrdersIterator = orderCollection.statusIterator(OrderStatus.COMPLETED);
        while (completedOrdersIterator.hasNext()) {
            ProductionOrder order = completedOrdersIterator.next();
            System.out.println(order.toString());
        }

        System.out.println("\n--- Searching for all orders with status: PENDING ---");
        OrderIterator pendingsOrderIterator = orderCollection.statusIterator(OrderStatus.PENDING);
        while (pendingsOrderIterator.hasNext()) {
            ProductionOrder order = pendingsOrderIterator.next();
            System.out.println(order.toString());
        }

        System.out.println("\n--- Searching for PENDING orders with quantity > 6 ---");
        pendingsOrderIterator.reset();
        while (pendingsOrderIterator.hasNextByQuantity(">", 6)) {
            ProductionOrder order = pendingsOrderIterator.nextByQuantity();
            System.out.println(order.toString());
        }

        SystemLogger.INSTANCE.logInfo("MAIN", "All orders processed. End.");
    }
}