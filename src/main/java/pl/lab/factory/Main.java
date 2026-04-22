package pl.lab.factory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import pl.lab.factory.assemblyline.BoardProducer;
import pl.lab.factory.assemblyline.SmartBoardProducer;
import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;
import pl.lab.factory.electronicboards.firmware.HighLevelFirmware;
import pl.lab.factory.electronicboards.firmware.HighLevelFirmwareAdapter;
import pl.lab.factory.electronicboards.firmware.ProprietaryFirmware;
import pl.lab.factory.electronicboards.firmware.ProprietaryFirmwareAdapter;
import pl.lab.factory.electronicboards.firmware.programmers.FaultyDevice;
import pl.lab.factory.electronicboards.firmware.programmers.FirmwareProgrammer;
import pl.lab.factory.electronicboards.firmware.programmers.InternalProgrammer;
import pl.lab.factory.electronicboards.firmware.programmers.OldFirmwareProgrammer;
import pl.lab.factory.electronicboards.firmware.programmers.ProgrammingDevice;
import pl.lab.factory.electronicboards.firmware.programmers.STLinkDevice;
import pl.lab.factory.electronicboards.firmware.programmers.SeggerJLinkDevice;
import pl.lab.factory.manufacturing.BaseBoardOrderProcessor;
import pl.lab.factory.manufacturing.BoardPrototypes;
import pl.lab.factory.manufacturing.ElectronicBoardFactory;
import pl.lab.factory.manufacturing.ElectronicBoardOrderProcessor;
import pl.lab.factory.manufacturing.OrderProcessor;
import pl.lab.factory.manufacturing.StandardBoardFactory;
import pl.lab.factory.manufacturing.SuperElectronicBoardFactory;
import pl.lab.factory.mediator.CentralControlMediator;
import pl.lab.factory.mediator.ProductionDepartment;
import pl.lab.factory.mediator.ReportingDepartment;
import pl.lab.factory.mediator.WarehouseDepartment;
import pl.lab.factory.orders.OrderSpecification;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.ProductionOrderFactory;
import pl.lab.factory.orders.ProductionQueue;
import pl.lab.factory.orders.command.AddMultipleOrdersCommand;
import pl.lab.factory.orders.command.AddOrderCommand;
import pl.lab.factory.orders.command.ClearQueueCommand;
import pl.lab.factory.orders.command.OrderQueueCommand;
import pl.lab.factory.orders.command.QueueCommandInvoker;
import pl.lab.factory.orders.command.RemoveOrderCommand;
import pl.lab.factory.orders.invoice.OrderInvoice;
import pl.lab.factory.orders.invoice.dispatch.B2bApiInvoiceDispatcher;
import pl.lab.factory.orders.invoice.dispatch.EmailInvoiceDispatcher;
import pl.lab.factory.orders.invoice.dispatch.InvoiceDispatcher;
import pl.lab.factory.orders.iterator.OrderCollection;
import pl.lab.factory.orders.iterator.OrderIterator;
import pl.lab.factory.orders.loyalty.CustomerLoyaltyService;
import pl.lab.factory.orders.loyalty.TierConfig;
import pl.lab.factory.orders.loyalty.VolumeBonusStrategy;
import pl.lab.factory.orders.observer.ClientEmailNotifier;
import pl.lab.factory.orders.observer.ErpSystemUpdater;
import pl.lab.factory.orders.observer.OrderObserver;
import pl.lab.factory.orders.pricing.BulkPricingStrategy;
import pl.lab.factory.orders.pricing.HolidayPricingStrategy;
import pl.lab.factory.orders.pricing.PricingStrategy;
import pl.lab.factory.orders.pricing.RushOrderPricingStrategy;
import pl.lab.factory.orders.pricing.StandardPricingStrategy;
import pl.lab.factory.orders.pricing.VipPricingStrategy;
import pl.lab.factory.orders.visitor.EuVatVisitor;
import pl.lab.factory.orders.visitor.UsaCustomsVisitor;
import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.monitoring.CountingVisitor;
import pl.lab.factory.pcbboards.monitoring.ProcessObserver;
import pl.lab.factory.pcbboards.monitoring.reporting.CostReport;
import pl.lab.factory.reporting.BoardReporter;
import pl.lab.factory.reporting.CachingBoardReporterProxy;
import pl.lab.factory.reporting.CompletedOnlyProductionReport;
import pl.lab.factory.reporting.ConsoleReportFormatter;
import pl.lab.factory.reporting.LegacyAuditSystem;
import pl.lab.factory.reporting.LegacyAuditSystemAdapter;
import pl.lab.factory.reporting.ProductionReport;
import pl.lab.factory.reporting.SummaryProductionReport;
import pl.lab.factory.reporting.interpreter.AbstractExpression;
import pl.lab.factory.reporting.interpreter.AndExpression;
import pl.lab.factory.reporting.interpreter.OrExpression;
import pl.lab.factory.reporting.interpreter.QuantityExpression;
import pl.lab.factory.reporting.interpreter.StatusExpression;
import pl.lab.factory.reporting.interpreter.TypeExpression;
import pl.lab.factory.reporting.invoicereport.*;
import pl.lab.factory.reporting.reportingfacade.ReportFormat;
import pl.lab.factory.reporting.reportingfacade.ReportType;
import pl.lab.factory.reporting.reportingfacade.ReportingFacade;
import pl.lab.factory.reporting.reportingsystem.*;
import pl.lab.factory.safetymediator.SafetyHistory;
import pl.lab.factory.safetymediator.SafetySystemMemento;
import pl.lab.factory.safetymediator.components.AlarmSiren;
import pl.lab.factory.safetymediator.components.FireGate;
import pl.lab.factory.safetymediator.components.SmokeDetector;
import pl.lab.factory.safetymediator.components.SprinklerSystem;
import pl.lab.factory.scheduling.ProductionBatch;
import pl.lab.factory.scheduling.SingleOrderTask;
import pl.lab.factory.warehouse.LegacyWarehouseSystem;
import pl.lab.factory.warehouse.WarehouseAccessProxy;
import pl.lab.factory.warehouse.WarehouseAdapter;
import pl.lab.factory.warehouse.WarehouseAssetsManager;
import pl.lab.factory.warehouse.WarehouseSafetyCenter;
import pl.lab.factory.warehouse.WarehouseService;
import pl.lab.factory.warehouse.equipment.EquipmentIterator;
import pl.lab.factory.warehouse.equipment.WarehouseEquipment;
import pl.lab.factory.warehouse.equipment.maintenance.ForkliftMaintenance;
import pl.lab.factory.warehouse.equipment.maintenance.MaintenanceProtocol;
import pl.lab.factory.warehouse.equipment.maintenance.ScannerMaintenance;
import pl.lab.factory.warehouse.equipment.observer.MaintenanceService;
import pl.lab.factory.warehouse.interpreter.OrderAndExpression;
import pl.lab.factory.warehouse.interpreter.OrderExpression;
import pl.lab.factory.warehouse.interpreter.OrderMinQuantityExpression;
import pl.lab.factory.warehouse.interpreter.OrderStatusExpression;
import pl.lab.factory.warehouse.packing.AntiStaticBagDecorator;
import pl.lab.factory.warehouse.packing.FoamProtectionDecorator;
import pl.lab.factory.warehouse.packing.PackageFactory;
import pl.lab.factory.warehouse.packing.PackageImplementor;
import pl.lab.factory.warehouse.shipping.PriorityShipping;
import pl.lab.factory.warehouse.shipping.ShippingFacade;
import pl.lab.factory.warehouse.shipping.ShippingStrategy;
import pl.lab.factory.warehouse.shipping.StandardShipping;
import pl.lab.factory.warehouse.shipping.command.ShipOrderCommand;
import pl.lab.factory.warehouse.shipping.command.ShippingCommand;
import pl.lab.factory.warehouse.shipping.command.ShippingManager;
import pl.lab.factory.warehouse.shipping.strategy.PriorityFirstDispatcher;
import pl.lab.factory.warehouse.state.LockedState;
import pl.lab.factory.warehouse.state.OpenState;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SystemLogger.INSTANCE.logInfo("MAIN", "This is the PCB/Electronic board factory!");

        FirmwareAdapter firmwareAdapter;
        firmwareAdapter = new HighLevelFirmwareAdapter(new HighLevelFirmware("v0.0.1-prototype"));
        BaseElectronicBoard sqcEBoard = SuperElectronicBoardFactory.createBoard(ElectronicType.NORMAL);
        ProcessObserver observer = new ProcessObserver();
        observer.setBoard(sqcEBoard.getBaseBoard());
        BoardProducer smartProducer = new SmartBoardProducer();
        CostReport costSummary = new CostReport();
        costSummary.generate(sqcEBoard.getBaseBoard());
        smartProducer.produce(sqcEBoard.getBaseBoard());

        ProgrammingDevice faultyDevice = new FaultyDevice();
        FirmwareProgrammer oldProgrammer = new OldFirmwareProgrammer(faultyDevice);
        oldProgrammer.program(sqcEBoard, firmwareAdapter);
        sqcEBoard.getBaseBoard().getComponentInfo();
        CountingVisitor counter = new CountingVisitor();
        sqcEBoard.getBaseBoard().accept(counter);
        System.out.println("[COMPONENT INFO] Board contains " + counter.getResistors() + " resistors.");

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



        BaseElectronicBoard electronicBoardPrototype = ElectronicBoardFactory.createBoard(ElectronicType.HIGHLY_DEPENDANT);

        BoardPrototypes boardPrototypes = new BoardPrototypes();
        boardPrototypes.registerPrototype("highly-dependant-v1", electronicBoardPrototype);

        BaseElectronicBoard electronicBoardA = boardPrototypes.createFromPrototype("highly-dependant-v1");
        firmwareAdapter = new HighLevelFirmwareAdapter(new HighLevelFirmware("v1.0.0-release"));
        InternalProgrammer internalProgrammer = new InternalProgrammer(new SeggerJLinkDevice());
        internalProgrammer.program(electronicBoardA, firmwareAdapter);
        BaseElectronicBoard electronicBoardB = boardPrototypes.createFromPrototype("highly-dependant-v1");
        System.out.println("[COMPONENT INFO] Gathering information about boards components!");
        electronicBoardB.getBaseBoard().getComponentInfo();
        counter = new CountingVisitor();
        electronicBoardB.getBaseBoard().accept(counter);
        System.out.println("[COMPONENT INFO] Board contains " + counter.getResistors() + " resistors.");
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


        // -- Order Invoice --

        SystemLogger.INSTANCE.logInfo("MAIN", "=== STATE 1 AND STRATEGY 1 PATTERNS TEST (INVOICE LIFECYCLE) ===");

        PricingStrategy standardPricing = new StandardPricingStrategy();
        PricingStrategy vipPricing = new VipPricingStrategy();
        PricingStrategy bulkPricing = new BulkPricingStrategy();

        ProductionOrder orderZ = ProductionOrderFactory.createOrder("ORD-Z", boardA, 150, specPcb);
        OrderInvoice invoiceA = new OrderInvoice(orderZ, standardPricing);
        SystemLogger.INSTANCE.logInfo("MAIN", "Initial draft amount: $" + String.format("%.2f", invoiceA.getAmount()));

        // Can't pay and generate receipt before confirming
        invoiceA.pay();
        invoiceA.generateReceipt();

        invoiceA.setPricingStrategy(vipPricing);
        invoiceA.recalculatePrice();
        invoiceA.applyDiscount(10.0);

        invoiceA.confirm();

        // Can't change price or apply discount after confirmation
        invoiceA.setPricingStrategy(bulkPricing);
        invoiceA.recalculatePrice();
        invoiceA.applyDiscount(10.0);

        // Can't generate receipt before payment
        invoiceA.generateReceipt();

        invoiceA.pay();

        // Can't cancel after payment
        invoiceA.cancel();

        invoiceA.generateReceipt();

        OrderInvoice invoiceB = new OrderInvoice(orderB, standardPricing);
        invoiceB.cancel();

        SystemLogger.INSTANCE.logInfo("MAIN", "=== END OF STATE 1 AND STRATEGY 1 PATTERNS TEST ===");



        SystemLogger.INSTANCE.logInfo("MAIN", "=== SRP 1 TEST (REVENUE CALCULATION) ===");

        OrderInvoice invoiceC = new OrderInvoice(orderC, standardPricing);
        OrderInvoice invoiceD = new OrderInvoice(orderBaseClone, standardPricing);

        invoiceC.recalculatePrice(); invoiceC.confirm(); invoiceC.pay();
        invoiceD.recalculatePrice(); invoiceD.confirm(); invoiceD.pay();

        RevenuePolicy policy = new PaidInvoiceRevenuePolicy();
        RevenueFilter filter = new RevenueFilter(policy);
        RevenueCalculator calculator = new RevenueCalculator();

        RevenueService revenueService = new RevenueService(filter, calculator);

        List<OrderInvoice> allInvoices = List.of(invoiceA, invoiceB, invoiceC, invoiceD);
        double totalRevenue = revenueService.calculateRevenue(allInvoices);

        SystemLogger.INSTANCE.logInfo("REVENUE-REPORT", "Calculated Total Revenue: $" + String.format("%.2f", totalRevenue));

        SystemLogger.INSTANCE.logInfo("MAIN", "=== END OF SRP 1 TEST ===");




        SystemLogger.INSTANCE.logInfo("MAIN", "=== OCP 1 TEST (CUSTOMER LOYALTY POINTS) ===");

        CustomerLoyaltyService loyaltyService = new CustomerLoyaltyService();

        loyaltyService.calculateByStrategy(orderA, new VolumeBonusStrategy());
        loyaltyService.calculateByTier(orderB, "GOLD");

        loyaltyService.registerTier("PLATINUM", new TierConfig(500, 5, 1000));
        loyaltyService.calculateByTier(orderC, "PLATINUM");

        SystemLogger.INSTANCE.logInfo("MAIN", "=== END OF OCP 1 TEST ===");



        // -- Invoice Dispatch --

        SystemLogger.INSTANCE.logInfo("MAIN", "=== TEMPLATE METHOD 1 PATTERN TEST (INVOICE DISPATCH) ===");

        InvoiceDispatcher emailDispatcher = new EmailInvoiceDispatcher();
        InvoiceDispatcher b2bApiDispatcher = new B2bApiInvoiceDispatcher();

        b2bApiDispatcher.dispatch(invoiceA);
        emailDispatcher.dispatch(invoiceA);
        emailDispatcher.dispatch(invoiceB);

        SystemLogger.INSTANCE.logInfo("MAIN", "=== END OF TEMPLATE METHOD 1 PATTERN TEST ===");



        SystemLogger.INSTANCE.logInfo("MAIN", "=== VISITOR 1 PATTERN TEST (TAX/CUSTOMS CALCULATOR) ===");

        UsaCustomsVisitor usaVisitor = new UsaCustomsVisitor();
        EuVatVisitor euVisitor = new EuVatVisitor();

        orderA.accept(usaVisitor);
        orderA.accept(euVisitor);

        orderB.accept(usaVisitor);
        orderB.accept(euVisitor);

        SystemLogger.INSTANCE.logInfo("TAX-SUMMARY",
                "Total USA Customs expected: $" + String.format("%.2f", usaVisitor.getTotalCustomsTax()));
        SystemLogger.INSTANCE.logInfo("TAX-SUMMARY",
                "Total EU VAT expected: $" + String.format("%.2f", euVisitor.getTotalVat()));

        SystemLogger.INSTANCE.logInfo("MAIN", "=== END OF VISITOR 1 PATTERN TEST ===");



        // -- CentralControlMediator --

        LegacyWarehouseSystem legacyWarehouse = new LegacyWarehouseSystem();
        Set<String> unapproved = Set.of("ORD-999", "ORD-888");
        WarehouseAdapter warehouseAdapter = new WarehouseAdapter(legacyWarehouse);
        WarehouseService warehouse = new WarehouseAccessProxy(
                warehouseAdapter,
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


        // -- Order Observers --

        SystemLogger.INSTANCE.logInfo("MAIN", "=== ORDER OBSERVER 1 PATTERN TEST ===");
        OrderObserver acmeCorpNotifier = new ClientEmailNotifier("contact@acmecorp.com");
        OrderObserver globalErpSync = new ErpSystemUpdater();

        orderA.addObserver(acmeCorpNotifier);
        orderA.addObserver(globalErpSync);


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

        warehouse.printLegacyWarehouse();
        warehouse.printWarehouse();

        PackageImplementor cardboardBoxPackaging = new FoamProtectionDecorator(new AntiStaticBagDecorator(PackageFactory.getPackage("cardboard")));
        PackageImplementor plasticBoxPackaging = new FoamProtectionDecorator(new AntiStaticBagDecorator(PackageFactory.getPackage("plastic")));

        ShippingStrategy standardShipping = new StandardShipping(cardboardBoxPackaging);
        ShippingStrategy priorityShipping = new PriorityShipping(plasticBoxPackaging);

        ShippingFacade shippingFacade = new ShippingFacade(warehouse);
        ShippingManager shippingManager = new ShippingManager();


        ShippingCommand shipCommandA = new ShipOrderCommand(shippingFacade, standardShipping, orderA);
        ShippingCommand shipCommandB = new ShipOrderCommand(shippingFacade, priorityShipping, orderB);
        ShippingCommand shipCommandC = new ShipOrderCommand(shippingFacade, standardShipping, orderC);

        shippingManager.addCommand(shipCommandB);
        shippingManager.addCommand(shipCommandA);
        shippingManager.addCommand(shipCommandC);

        shippingManager.setDispatcher(new PriorityFirstDispatcher());
        shippingManager.shipEverything();

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

        //Equipment
        WarehouseAssetsManager assetsManager = new WarehouseAssetsManager();

        WarehouseEquipment forklift = new WarehouseEquipment("FORK-001", "Toyota Forklift", LocalDate.now().minusMonths(14), 498);
        WarehouseEquipment scan = new WarehouseEquipment("SCAN-052", "Zebra Scanner", LocalDate.now().minusMonths(2), 540);
        WarehouseEquipment solderer = new WarehouseEquipment("SOLDER-20", "Weller Station", LocalDate.now().minusMonths(1), 120);
        WarehouseEquipment terminal = new WarehouseEquipment("TERM-03", "Logistics Terminal");

        assetsManager.addEquipment(forklift);
        assetsManager.addEquipment(scan);
        assetsManager.addEquipment(solderer);
        assetsManager.addEquipment(terminal);

        EquipmentIterator maintenanceIt = assetsManager.getMaintenanceIterator();

        SystemLogger.INSTANCE.logInfo("MAINTENANCE", "Devices requiring immediate service:");

        while(maintenanceIt.hasNext()){
            WarehouseEquipment equipment = maintenanceIt.next();
            SystemLogger.INSTANCE.logInfo("MAINTENANCE", "Check needed: " + equipment.toString());
        }

        MaintenanceService serviceDept = new MaintenanceService();

        assetsManager.subscribeAll(serviceDept);

        forklift.use(5);

        MaintenanceProtocol forkliftService = new ForkliftMaintenance();
        MaintenanceProtocol scannerService = new ScannerMaintenance();

        scannerService.registerServiceDuration("SHORT", 20);

        forkliftService.executeService(forklift, "STANDARD");
        scannerService.executeService(scan, "SHORT");

        // Fire Alarm

        WarehouseSafetyCenter warehouseSafetyCenter = new WarehouseSafetyCenter();
        SafetyHistory safetyHistory = new SafetyHistory();

        SmokeDetector sensor = new SmokeDetector(warehouseSafetyCenter);
        FireGate gate = new FireGate(warehouseSafetyCenter);
        SprinklerSystem sprinkler = new SprinklerSystem(warehouseSafetyCenter);
        AlarmSiren siren = new AlarmSiren(warehouseSafetyCenter);

        warehouseSafetyCenter.setComponents(sensor, gate, sprinkler, siren);

        safetyHistory.save(warehouseSafetyCenter.save());

        sensor.detectFire();

        warehouseAdapter.getZoneA().setState(new LockedState());

        ProductionOrder fireOrder = ProductionOrderFactory.createOrder("FIRE-01", boardA, 1, specPcb);
        warehouse.storeOrder(fireOrder);

        SafetySystemMemento lastState = safetyHistory.undo();
        if (lastState != null) {
            warehouseSafetyCenter.restore(lastState);
            warehouseAdapter.getZoneA().setState(new OpenState());
        }

        warehouse.storeOrder(fireOrder);


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
        OrderIterator pendingOrdersIterator = orderCollection.statusIterator(OrderStatus.PENDING);
        while (pendingOrdersIterator.hasNext()) {
            ProductionOrder order = pendingOrdersIterator.next();
            System.out.println(order.toString());
        }

        System.out.println("\n--- Searching for PENDING orders with quantity > 6 ---");
        pendingOrdersIterator.reset();
        while (pendingOrdersIterator.hasNextByQuantity(">", 6)) {
            ProductionOrder order = pendingOrdersIterator.nextByQuantity();
            System.out.println(order.toString());
        }

        SystemLogger.INSTANCE.logInfo("MAIN", "All orders processed. End.");




        SystemLogger.INSTANCE.logInfo("MAIN", "--- ISP 1 TEST ---");


        ReportGenerator generator = new SimpleReportGenerator();
        ReportFormatter formatter = new SimpleReportFormatter();
        ReportDispatcher dispatcher = new SimpleReportDispatcher();

        generator.generateReport(orderA);

        String csvData = formatter.formatAsCsv(orderA);
        SystemLogger.INSTANCE.logInfo("MAIN", "Formatted data: " + csvData);

        dispatcher.archiveReport(orderA);
        dispatcher.sendReportByEmail(orderA);

        SystemLogger.INSTANCE.logInfo("MAIN", "--- END OF ISP 1 TEST ---");





        SystemLogger.INSTANCE.logInfo("MAIN", "--- LSP 1 AND LSP 4 TEST ---");

        // Klasa bazowa (konkretna)
        StandardPricingStrategy standardPricing1 = new StandardPricingStrategy();

        // Klasy pochodne z typem klasy pochodnej
        HolidayPricingStrategy holidayPricing1 = new HolidayPricingStrategy();
        RushOrderPricingStrategy rushPricing1 = new RushOrderPricingStrategy();

        // Klasa pochodna z typem klasy bazowej (konkretnej)
        StandardPricingStrategy rushPricing2 = new RushOrderPricingStrategy();

        testPriceCalculation(standardPricing1, orderA);
        testPriceCalculation(holidayPricing1, orderA);
        testPriceCalculation(rushPricing1, orderA);
        testPriceCalculation(rushPricing2, orderA);


        // Klasy pochodne z typem klasy pochodnej
        CompletedOnlyProductionReport reportCompletedDerived = new CompletedOnlyProductionReport(new ConsoleReportFormatter());
        SummaryProductionReport reportAllDerived = new SummaryProductionReport(new ConsoleReportFormatter());
        reportCompletedDerived.generate(allOrders);
        reportAllDerived.generate(allOrders);

        // Klasy pochodne z typem klasy bazowej (abstrakcyjnej)
        ProductionReport reportCompletedBase = new CompletedOnlyProductionReport(new ConsoleReportFormatter());
        ProductionReport reportAllBase = new SummaryProductionReport(new ConsoleReportFormatter());
        reportCompletedBase.generate(allOrders);
        reportAllBase.generate(allOrders);

        SystemLogger.INSTANCE.logInfo("MAIN", "--- END OF LSP 1 AND LSP 4 TEST ---");




        SystemLogger.INSTANCE.logInfo("MAIN", "All orders processed. End.");
    }

    public static void testPriceCalculation(StandardPricingStrategy strategy, ProductionOrder order) {
        double price = strategy.calculatePrice(order);
        SystemLogger.INSTANCE.logInfo("MAIN", "Calculated price for " + order.getId()
                + " using the base (or derived) class. Result: $" + price);
    }
}