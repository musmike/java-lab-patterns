package pl.lab.factory.processes;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.mediator.CentralControlMediator;
import pl.lab.factory.mediator.ProductionDepartment;
import pl.lab.factory.mediator.ReportingDepartment;
import pl.lab.factory.mediator.WarehouseDepartment;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.ProductionQueue;
import pl.lab.factory.orders.command.*;
import pl.lab.factory.orders.observer.ClientEmailNotifier;
import pl.lab.factory.orders.observer.ErpSystemUpdater;
import pl.lab.factory.orders.observer.OrderObserver;
import pl.lab.factory.processes.context.FactoryContext;
import pl.lab.factory.reporting.BoardReporter;
import pl.lab.factory.reporting.CachingBoardReporterProxy;
import pl.lab.factory.reporting.LegacyAuditSystem;
import pl.lab.factory.reporting.LegacyAuditSystemAdapter;
import pl.lab.factory.scheduling.ProductionBatch;
import pl.lab.factory.scheduling.SingleOrderTask;
import pl.lab.factory.warehouse.LegacyWarehouseSystem;
import pl.lab.factory.warehouse.WarehouseAccessProxy;
import pl.lab.factory.warehouse.WarehouseAdapter;
import pl.lab.factory.warehouse.WarehouseService;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class FactoryOperationsProcess {

    private static final String APP_CONTEXT = FactoryOperationsProcess.class.getSimpleName();
    private static final String UNAPPROVED_ORDER_1 = "ORD-999";
    private static final String UNAPPROVED_ORDER_2 = "ORD-888";
    private static final String CLIENT_EMAIL = "contact@acmecorp.com";
    private static final String BATCH_PLAN_NAME = "Weekly plan";
    private static final int QUEUE_SLEEP_SECONDS = 1;

    private final FactoryContext context;

    public FactoryOperationsProcess(FactoryContext context) {
        this.context = context;
    }

    public void execute() throws InterruptedException {
        validateContext();
        setupCentralControlMediator();
        testOrderObservers();
        testOrderQueue();
    }

    private void validateContext() {
        if (context.getPcbProcessor() == null || context.getPcbOrder() == null) {
            throw new IllegalStateException("Required processors and orders must be present.");
        }
    }

    private void setupCentralControlMediator() {
        WarehouseService proxyWarehouse = createWarehouseProxy();
        BoardReporter boardReporter = createBoardReporter();
        wireMediatorWithDepartments(proxyWarehouse, boardReporter);
    }

    private WarehouseService createWarehouseProxy() {
        Set<String> unapprovedOrders = Set.of(UNAPPROVED_ORDER_1, UNAPPROVED_ORDER_2);
        LegacyWarehouseSystem legacyWarehouse = new LegacyWarehouseSystem();
        WarehouseAdapter warehouseAdapter = new WarehouseAdapter(legacyWarehouse);
        context.setWarehouseAdapter(warehouseAdapter);
        return new WarehouseAccessProxy(warehouseAdapter, unapprovedOrders);
    }

    private BoardReporter createBoardReporter() {
        return new CachingBoardReporterProxy(
                new LegacyAuditSystemAdapter(new LegacyAuditSystem())
        );
    }

    private void wireMediatorWithDepartments(WarehouseService proxyWarehouse, BoardReporter boardReporter) {
        CentralControlMediator mediator = new CentralControlMediator();

        ProductionDepartment productionDepartment = new ProductionDepartment(mediator, context.getPcbProcessor(), context.getElectronicBoardProcessor());
        WarehouseDepartment warehouseDepartment = new WarehouseDepartment(mediator, proxyWarehouse);
        ReportingDepartment reportingDepartment = new ReportingDepartment(mediator, boardReporter);

        mediator.setProductionDepartment(productionDepartment);
        mediator.setWarehouseDepartment(warehouseDepartment);
        mediator.setReportingDepartment(reportingDepartment);

        context.setWarehouse(proxyWarehouse);
        context.setProductionDepartment(productionDepartment);
    }

    private void testOrderObservers() {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== ORDER OBSERVER 1 PATTERN TEST ===");
        OrderObserver emailNotifier = new ClientEmailNotifier(CLIENT_EMAIL);
        OrderObserver globalErpSynchronizer = new ErpSystemUpdater();

        context.getPcbOrder().addObserver(emailNotifier);
        context.getPcbOrder().addObserver(globalErpSynchronizer);
    }

    private void testOrderQueue() throws InterruptedException {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== COMMAND PATTERN TEST (UNDO/REDO FOR QUEUE) ===");

        ProductionQueue productionQueue = ProductionQueue.getInstance();
        QueueCommandInvoker queueCommandInvoker = new QueueCommandInvoker();

        executeQueueUndoRedo(productionQueue, queueCommandInvoker);
        populateQueueWithAllOrders(productionQueue, queueCommandInvoker);

        TimeUnit.SECONDS.sleep(QUEUE_SLEEP_SECONDS);
        executeProductionBatch(productionQueue);
    }

    private void executeQueueUndoRedo(ProductionQueue productionQueue, QueueCommandInvoker queueCommandInvoker) {
        demonstrateAddAndRemove(productionQueue, queueCommandInvoker);
        demonstrateClearAndUndo(productionQueue, queueCommandInvoker);
    }

    private void demonstrateAddAndRemove(ProductionQueue productionQueue, QueueCommandInvoker queueCommandInvoker) {
        queueCommandInvoker.executeCommand(new AddOrderCommand(context.getPcbOrder()));
        queueCommandInvoker.executeCommand(new AddOrderCommand(context.getStandardElectronicOrder()));
        assertQueueState(productionQueue);

        queueCommandInvoker.executeCommand(new RemoveOrderCommand(context.getPcbOrder()));
        assertQueueState(productionQueue);

        queueCommandInvoker.undo();
        assertQueueState(productionQueue);
    }

    private void demonstrateClearAndUndo(ProductionQueue productionQueue, QueueCommandInvoker queueCommandInvoker) {
        OrderQueueCommand clearAllCommand = new ClearQueueCommand();

        queueCommandInvoker.executeCommand(clearAllCommand);
        assertQueueState(productionQueue);

        queueCommandInvoker.undo();
        assertQueueState(productionQueue);

        queueCommandInvoker.executeCommand(clearAllCommand);
        assertQueueState(productionQueue);
    }
    
    private void assertQueueState(ProductionQueue productionQueue) {
        productionQueue.printQueue();
    }

    private void populateQueueWithAllOrders(ProductionQueue productionQueue, QueueCommandInvoker queueCommandInvoker) {
        queueCommandInvoker.executeCommand(new AddMultipleOrdersCommand(context.getAllOrders()));
        assertQueueState(productionQueue); 
    }

    private void executeProductionBatch(ProductionQueue productionQueue) throws InterruptedException {
        ProductionBatch productionBatch = createBatchFromQueue(productionQueue);
        
        System.out.println(productionBatch.getDescription(""));
        productionBatch.execute();
    }

    private ProductionBatch createBatchFromQueue(ProductionQueue productionQueue) {
        ProductionBatch productionBatch = new ProductionBatch(BATCH_PLAN_NAME);
        
        while (!productionQueue.isEmpty()) {
            ProductionOrder order = productionQueue.nextOrder();
            productionBatch.add(new SingleOrderTask(order, context.getProductionDepartment()));
        }
        
        return productionBatch;
    }
}