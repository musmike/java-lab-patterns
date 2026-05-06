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
import pl.lab.factory.electronicboards.firmware.*;
import pl.lab.factory.electronicboards.firmware.programmers.*;
import pl.lab.factory.manufacturing.*;
import pl.lab.factory.mediator.*;
import pl.lab.factory.orders.*;
import pl.lab.factory.orders.command.*;
import pl.lab.factory.orders.invoice.OrderInvoice;
import pl.lab.factory.orders.invoice.dispatch.*;
import pl.lab.factory.orders.iterator.*;
import pl.lab.factory.orders.loyalty.*;
import pl.lab.factory.orders.observer.*;
import pl.lab.factory.orders.pricing.*;
import pl.lab.factory.orders.visitor.*;
import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.monitoring.CountingVisitor;
import pl.lab.factory.pcbboards.monitoring.ProcessObserver;
import pl.lab.factory.pcbboards.monitoring.reporting.CostReport;
import pl.lab.factory.reporting.*;
import pl.lab.factory.reporting.interpreter.*;
import pl.lab.factory.reporting.invoicereport.*;
import pl.lab.factory.reporting.reportingfacade.*;
import pl.lab.factory.reporting.reportingsystem.*;
import pl.lab.factory.reporting.reportingsystem.ReportFormatter;
import pl.lab.factory.safetymediator.SafetyHistory;
import pl.lab.factory.safetymediator.SafetySystemMemento;
import pl.lab.factory.safetymediator.components.*;
import pl.lab.factory.scheduling.ProductionBatch;
import pl.lab.factory.scheduling.SingleOrderTask;
import pl.lab.factory.warehouse.*;
import pl.lab.factory.warehouse.equipment.*;
import pl.lab.factory.warehouse.equipment.maintenance.*;
import pl.lab.factory.warehouse.equipment.observer.MaintenanceService;
import pl.lab.factory.warehouse.interpreter.*;
import pl.lab.factory.warehouse.packing.*;
import pl.lab.factory.warehouse.shipping.*;
import pl.lab.factory.warehouse.shipping.command.*;
import pl.lab.factory.warehouse.shipping.strategy.PriorityFirstDispatcher;
import pl.lab.factory.warehouse.state.*;

public class Main {

    private BaseBoard boardA;
    private BaseElectronicBoard electronicBoardA;
    private BaseElectronicBoard electronicBoardB;

    private OrderSpecification specPcb;
    private OrderSpecification specElectronic;

    private ProductionOrder orderA, orderB, orderC, orderBaseClone, orderElectronicClone;
    private List<ProductionOrder> allOrders;

    private OrderInvoice invoiceA, invoiceB;

    private OrderProcessor pcbProcessor, electronicBoardProcessor;
    private WarehouseAdapter warehouseAdapter;
    private WarehouseService warehouse;
    private ProductionDepartment prodDept;

    public static void main(String[] args) throws InterruptedException {
        SystemLogger.INSTANCE.logInfo("MAIN", "This is the PCB/Electronic board factory!");
        new Main().runAllDemos();
        SystemLogger.INSTANCE.logInfo("MAIN", "All orders processed. End.");
    }

    private void runAllDemos() throws InterruptedException {
        demoSmartBoardAndFirmware();
        demoCreatingOrdersSpecs();
        demoCreatingPrototypes();
        demoCreatingOrdersFinal();

        demoOrderInvoiceLifecycle();
        demoRevenueCalculation();
        demoCustomerLoyaltyPoints();
        demoInvoiceDispatch();
        demoTaxAndCustomsCalculator();

        demoCentralControlMediator();
        demoOrderObservers();
        demoOrderQueue();

        demoWarehouseAndShipping();
        demoWarehouseFiltering();
        demoWarehouseEquipmentAndMaintenance();
        demoFireAlarm();

        demoReports();
        demoOrderStatusMemento();
        demoOrderIteratorSetup();
        demoOrderIteratorSearches();

        demoIspTest();
        demoLspTest();
    }

    private void demoSmartBoardAndFirmware() throws InterruptedException {
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
    }

    private void demoCreatingOrdersSpecs() {
        // -- Creating orders --

        StandardBoardFactory boardFactory = new StandardBoardFactory();
        pcbProcessor = new BaseBoardOrderProcessor();
        electronicBoardProcessor = new ElectronicBoardOrderProcessor();

        OrderSpecification rohs = OrderSpecification.builder()
                .voltageTolerance(5.0)
                .thicknessTolerance(0.1)
                .addCertificate("RoHS")
                .addCertificate("ISO9001")
                .qualityStandard("Class 2")
                .build();

        specPcb = rohs.clone();
        specPcb.addCertificate("IPC-A-610");
        specPcb.displayCertificates("ORD-001");

        specElectronic = rohs.clone();
        specElectronic.setQualityStandard("Class 3");
        specElectronic.setVoltageTolerance(2.5);
    }

    private void demoCreatingPrototypes() {
        BaseElectronicBoard electronicBoardPrototype = ElectronicBoardFactory.createBoard(ElectronicType.HIGHLY_DEPENDANT);

        BoardPrototypes boardPrototypes = new BoardPrototypes();
        boardPrototypes.registerPrototype("highly-dependant-v1", electronicBoardPrototype);

        electronicBoardA = boardPrototypes.createFromPrototype("highly-dependant-v1");
        FirmwareAdapter firmwareAdapterA = new HighLevelFirmwareAdapter(new HighLevelFirmware("v1.0.0-release"));
        InternalProgrammer internalProgrammerA = new InternalProgrammer(new SeggerJLinkDevice());
        internalProgrammerA.program(electronicBoardA, firmwareAdapterA);

        electronicBoardB = boardPrototypes.createFromPrototype("highly-dependant-v1");
        System.out.println("[COMPONENT INFO] Gathering information about boards components!");
        electronicBoardB.getBaseBoard().getComponentInfo();
        CountingVisitor counter = new CountingVisitor();
        electronicBoardB.getBaseBoard().accept(counter);
        System.out.println("[COMPONENT INFO] Board contains " + counter.getResistors() + " resistors.");

        FirmwareAdapter firmwareAdapterB = new ProprietaryFirmwareAdapter(new ProprietaryFirmware("v1.1.0-hotfix"));
        InternalProgrammer internalProgrammerB = new InternalProgrammer(new STLinkDevice());
        internalProgrammerB.program(electronicBoardB, firmwareAdapterB);

        SystemLogger.INSTANCE.logInfo("PROTOTYPE", "Prototype: firmware=" + electronicBoardPrototype.getFirmware());
        SystemLogger.INSTANCE.logInfo("PROTOTYPE", "Clone A:   firmware=" + electronicBoardA.getFirmware());
        SystemLogger.INSTANCE.logInfo("PROTOTYPE", "Clone B:   firmware=" + electronicBoardB.getFirmware());
    }

    private void demoCreatingOrdersFinal() {
        boardA = new StandardBoardFactory().createBoard();

        orderA = ProductionOrderFactory.createOrder(boardA, new OrderContext("ORD-001", 10, specPcb));
        orderB = ProductionOrderFactory.createOrder(electronicBoardA, new OrderContext("ORD-002", 3, specElectronic));
        orderC = ProductionOrderFactory.createOrder(electronicBoardB, new OrderContext("ORD-003", 2, specElectronic));
        orderBaseClone = orderA.clone("ORD-004");
        orderElectronicClone = orderB.clone("ORD-005");

        allOrders = new ArrayList<>(List.of(orderA, orderB, orderC, orderBaseClone, orderElectronicClone));
    }

    private void demoOrderInvoiceLifecycle() {
        // -- Order Invoice --

        SystemLogger.INSTANCE.logInfo("MAIN", "=== STATE 1 AND STRATEGY 1 PATTERNS TEST (INVOICE LIFECYCLE) ===");

        PricingStrategy standardPricing = new StandardPricingStrategy();
        PricingStrategy vipPricing = new VipPricingStrategy();
        PricingStrategy bulkPricing = new BulkPricingStrategy();

        ProductionOrder orderZ = ProductionOrderFactory.createOrder(boardA, new OrderContext("ORD-Z", 150, specPcb));
        invoiceA = new OrderInvoice(orderZ, standardPricing);
        SystemLogger.INSTANCE.logInfo("MAIN", "Initial draft amount: $" + CurrencyFormatter.format(invoiceA.getAmount()));

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

        invoiceB = new OrderInvoice(orderB, standardPricing);
        invoiceB.cancel();

        SystemLogger.INSTANCE.logInfo("MAIN", "=== END OF STATE 1 AND STRATEGY 1 PATTERNS TEST ===");
    }

    private void demoRevenueCalculation() {
        SystemLogger.INSTANCE.logInfo("MAIN", "=== SRP 1 TEST (REVENUE CALCULATION) ===");

        PricingStrategy standardPricing = new StandardPricingStrategy();
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

        SystemLogger.INSTANCE.logInfo("REVENUE-REPORT", "Calculated Total Revenue: $" + CurrencyFormatter.format(totalRevenue));

        SystemLogger.INSTANCE.logInfo("MAIN", "=== END OF SRP 1 TEST ===");
    }

    private void demoCustomerLoyaltyPoints() {
        SystemLogger.INSTANCE.logInfo("MAIN", "=== OCP 1 TEST (CUSTOMER LOYALTY POINTS) ===");

        CustomerLoyaltyService loyaltyService = new CustomerLoyaltyService();

        loyaltyService.calculateByStrategy(orderA, new VolumeBonusStrategy());
        loyaltyService.calculateByTier(orderB, "GOLD");

        loyaltyService.registerTier("PLATINUM", new TierConfig(500, 5, 1000));
        loyaltyService.calculateByTier(orderC, "PLATINUM");

        SystemLogger.INSTANCE.logInfo("MAIN", "=== END OF OCP 1 TEST ===");
    }

    private void demoInvoiceDispatch() {
        // -- Invoice Dispatch --

        SystemLogger.INSTANCE.logInfo("MAIN", "=== TEMPLATE METHOD 1 PATTERN TEST (INVOICE DISPATCH) ===");

        InvoiceDispatcher emailDispatcher = new EmailInvoiceDispatcher();
        InvoiceDispatcher b2bApiDispatcher = new B2bApiInvoiceDispatcher();

        b2bApiDispatcher.dispatch(invoiceA);
        emailDispatcher.dispatch(invoiceA);
        emailDispatcher.dispatch(invoiceB);

        SystemLogger.INSTANCE.logInfo("MAIN", "=== END OF TEMPLATE METHOD 1 PATTERN TEST ===");
    }

    private void demoTaxAndCustomsCalculator() {
        SystemLogger.INSTANCE.logInfo("MAIN", "=== VISITOR 1 PATTERN TEST (TAX/CUSTOMS CALCULATOR) ===");

        UsaCustomsVisitor usaVisitor = new UsaCustomsVisitor();
        EuVatVisitor euVisitor = new EuVatVisitor();

        orderA.accept(usaVisitor);
        orderA.accept(euVisitor);

        orderB.accept(usaVisitor);
        orderB.accept(euVisitor);

        SystemLogger.INSTANCE.logInfo("TAX-SUMMARY",
                "Total USA Customs expected: $" + CurrencyFormatter.format(usaVisitor.getTotalCustomsTax()));
        SystemLogger.INSTANCE.logInfo("TAX-SUMMARY",
                "Total EU VAT expected: $" + CurrencyFormatter.format(euVisitor.getTotalVat()));

        SystemLogger.INSTANCE.logInfo("MAIN", "=== END OF VISITOR 1 PATTERN TEST ===");
    }

    private void demoCentralControlMediator() {
        // -- CentralControlMediator --

        LegacyWarehouseSystem legacyWarehouse = new LegacyWarehouseSystem();
        Set<String> unapproved = Set.of("ORD-999", "ORD-888");
        warehouseAdapter = new WarehouseAdapter(legacyWarehouse);
        warehouse = new WarehouseAccessProxy(
                warehouseAdapter,
                unapproved
        );

        BoardReporter boardReporter = new CachingBoardReporterProxy(
                new LegacyAuditSystemAdapter(new LegacyAuditSystem())
        );

        CentralControlMediator mediator = new CentralControlMediator();

        prodDept = new ProductionDepartment(mediator, pcbProcessor, electronicBoardProcessor);
        WarehouseDepartment wareDept = new WarehouseDepartment(mediator, warehouse);
        ReportingDepartment repDept = new ReportingDepartment(mediator, boardReporter);

        mediator.setProductionDepartment(prodDept);
        mediator.setWarehouseDepartment(wareDept);
        mediator.setReportingDepartment(repDept);
    }

    private void demoOrderObservers() {
        // -- Order Observers --

        SystemLogger.INSTANCE.logInfo("MAIN", "=== ORDER OBSERVER 1 PATTERN TEST ===");
        OrderObserver acmeCorpNotifier = new ClientEmailNotifier("contact@acmecorp.com");
        OrderObserver globalErpSync = new ErpSystemUpdater();

        orderA.addObserver(acmeCorpNotifier);
        orderA.addObserver(globalErpSync);
    }

    private void demoOrderQueue() throws InterruptedException {
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
    }

    private void demoWarehouseAndShipping() {
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

        //Liskov
        shippingManager.setDispatcher(new PriorityFirstDispatcher());
        shippingManager.shipEverything();

        shippingManager.undoLastAction();

        warehouse.printWarehouse();
    }

    private void demoWarehouseFiltering() {
        // Filtering
        OrderExpression isCompleted = new OrderStatusExpression(OrderStatus.COMPLETED);
        OrderExpression isLarge = new OrderMinQuantityExpression(5);

        OrderExpression complexRule = new OrderAndExpression(isCompleted, isLarge);

        SystemLogger.INSTANCE.logInfo("INTERPRETER", "Filtering completed orders with quantity >= 5");
        allOrders.stream()
                .filter(complexRule::interpret)
                .forEach(o -> SystemLogger.INSTANCE.logInfo("[FILTERING]", "Match found: " + o.getId()));
    }

    private void demoWarehouseEquipmentAndMaintenance() {
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
            SystemLogger.INSTANCE.logInfo("MAINTENANCE", "Check needed: " + maintenanceIt.next().toString());
        }

        MaintenanceService serviceDept = new MaintenanceService();
        assetsManager.subscribeAll(serviceDept);

        forklift.use(5);

        MaintenanceProtocol forkliftService = new ForkliftMaintenance();
        MaintenanceProtocol scannerService = new ScannerMaintenance();

        scannerService.registerServiceDuration("SHORT", 20);
        forkliftService.executeService(forklift, "STANDARD");
        scannerService.executeService(scan, "SHORT");
    }

    private void demoFireAlarm() {
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

        ProductionOrder fireOrder = ProductionOrderFactory.createOrder(boardA, new OrderContext("FIRE-01", 1, specPcb));
        warehouse.storeOrder(fireOrder);

        SafetySystemMemento lastState = safetyHistory.undo();
        if (lastState != null) {
            warehouseSafetyCenter.restore(lastState);
            warehouseAdapter.getZoneA().setState(new OpenState());
        }

        warehouse.storeOrder(fireOrder);
    }

    private void demoReports() {
        // -- Reports --

        // not completed order example
        ProductionOrder orderD = ProductionOrderFactory.createOrder(boardA, new OrderContext("ORD-006", 5, specPcb));
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
    }

    private void demoOrderStatusMemento() {
        // Order Status Memento
        ProductionOrder orderD = allOrders.get(allOrders.size() - 1);
        orderD.setStatus(OrderStatus.COMPLETED);
        orderD.setStatus(OrderStatus.PACKED);
        orderD.setStatus(OrderStatus.SHIPPED);

        orderD.restoreStatus();
        orderD.restoreStatus();
        orderD.restoreStatus();
        orderD.restoreStatus();
    }

    private void demoOrderIteratorSetup() {
        SystemLogger.INSTANCE.logInfo("MAIN", "=== ORDER ITERATOR PATTERN TEST ===");

        ProductionOrder orderE = ProductionOrderFactory.createOrder(boardA, new OrderContext("ITER-01", 2, specPcb));
        ProductionOrder orderF = ProductionOrderFactory.createOrder(electronicBoardB, new OrderContext("ITER-02", 8, specElectronic));
        ProductionOrder orderG = ProductionOrderFactory.createOrder(boardA, new OrderContext("ITER-03", 5, specPcb));
        ProductionOrder orderH = ProductionOrderFactory.createOrder(electronicBoardB, new OrderContext("ITER-04", 9, specElectronic));
        ProductionOrder orderI = ProductionOrderFactory.createOrder(boardA, new OrderContext("ITER-05", 6, specPcb));
        ProductionOrder orderJ = ProductionOrderFactory.createOrder(electronicBoardB, new OrderContext("ITER-06", 7, specElectronic));

        orderE.setStatus(OrderStatus.COMPLETED);
        orderF.setStatus(OrderStatus.COMPLETED);

        OrderCollection orderCollection = ProductionQueue.getInstance();
        new QueueCommandInvoker().executeCommand(new ClearQueueCommand());

        orderCollection.addOrder(orderE);
        orderCollection.addOrder(orderF);
        orderCollection.addOrder(orderG);
        orderCollection.addOrder(orderH);
        orderCollection.addOrder(orderI);
        orderCollection.addOrder(orderJ);
    }

    private void demoOrderIteratorSearches() {
        OrderCollection orderCollection = ProductionQueue.getInstance();

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
    }

    private void demoIspTest() {
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
    }

    private void demoLspTest() {
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
    }

    public static void testPriceCalculation(StandardPricingStrategy strategy, ProductionOrder order) {
        double price = strategy.calculatePrice(order);
        SystemLogger.INSTANCE.logInfo("MAIN", "Calculated price for " + order.getId()
                + " using the base (or derived) class. Result: $" + price);
    }
}