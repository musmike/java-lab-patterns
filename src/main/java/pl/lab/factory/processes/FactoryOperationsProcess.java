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
        Set<String> unapproved = Set.of(UNAPPROVED_ORDER_1, UNAPPROVED_ORDER_2);
        LegacyWarehouseSystem legacyWarehouse = new LegacyWarehouseSystem();
        WarehouseAdapter adapter = new WarehouseAdapter(legacyWarehouse);
        context.setWarehouseAdapter(adapter);
        return new WarehouseAccessProxy(adapter, unapproved);
    }

    private BoardReporter createBoardReporter() {
        return new CachingBoardReporterProxy(
                new LegacyAuditSystemAdapter(new LegacyAuditSystem())
        );
    }

    private void wireMediatorWithDepartments(WarehouseService proxyWarehouse, BoardReporter boardReporter) {
        CentralControlMediator mediator = new CentralControlMediator();

        ProductionDepartment productionDept = new ProductionDepartment(mediator, context.getPcbProcessor(), context.getElectronicBoardProcessor());
        WarehouseDepartment warehouseDept = new WarehouseDepartment(mediator, proxyWarehouse);
        ReportingDepartment reportingDept = new ReportingDepartment(mediator, boardReporter);

        mediator.setProductionDepartment(productionDept);
        mediator.setWarehouseDepartment(warehouseDept);
        mediator.setReportingDepartment(reportingDept);

        context.setWarehouse(proxyWarehouse);
        context.setProductionDepartment(productionDept);
    }

    private void testOrderObservers() {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== ORDER OBSERVER 1 PATTERN TEST ===");
        OrderObserver emailNotifier = new ClientEmailNotifier(CLIENT_EMAIL);
        OrderObserver globalErpSync = new ErpSystemUpdater();

        context.getPcbOrder().addObserver(emailNotifier);
        context.getPcbOrder().addObserver(globalErpSync);
    }

    private void testOrderQueue() throws InterruptedException {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== COMMAND PATTERN TEST (UNDO/REDO FOR QUEUE) ===");

        ProductionQueue queue = ProductionQueue.getInstance();
        QueueCommandInvoker invoker = new QueueCommandInvoker();

        executeQueueUndoRedo(queue, invoker);
        populateQueueWithAllOrders(queue, invoker);

        TimeUnit.SECONDS.sleep(QUEUE_SLEEP_SECONDS);
        executeWeeklyPlan(queue);
    }

    private void executeQueueUndoRedo(ProductionQueue queue, QueueCommandInvoker invoker) {
        demonstrateAddAndRemove(queue, invoker);
        demonstrateClearAndUndo(queue, invoker);
    }

    private void demonstrateAddAndRemove(ProductionQueue queue, QueueCommandInvoker invoker) {
        invoker.executeCommand(new AddOrderCommand(context.getPcbOrder()));
        invoker.executeCommand(new AddOrderCommand(context.getStandardElectronicOrder()));
        queue.printQueue();

        invoker.executeCommand(new RemoveOrderCommand(context.getPcbOrder()));
        queue.printQueue();

        invoker.undo();
        queue.printQueue();
    }

    private void demonstrateClearAndUndo(ProductionQueue queue, QueueCommandInvoker invoker) {
        OrderQueueCommand clearAll = new ClearQueueCommand();

        invoker.executeCommand(clearAll);
        queue.printQueue();

        invoker.undo();
        queue.printQueue();

        invoker.executeCommand(clearAll);
        queue.printQueue();
    }

    private void populateQueueWithAllOrders(ProductionQueue queue, QueueCommandInvoker invoker) {
        invoker.executeCommand(new AddMultipleOrdersCommand(context.getAllOrders()));
        queue.printQueue(); // State: allOrders
    }

    private void executeWeeklyPlan(ProductionQueue queue) throws InterruptedException {
        ProductionBatch weeklyPlan = new ProductionBatch(BATCH_PLAN_NAME);

        while (!queue.isEmpty()) {
            ProductionOrder order = queue.nextOrder();
            weeklyPlan.add(new SingleOrderTask(order, context.getProductionDepartment()));
        }

        System.out.println(weeklyPlan.getDescription(""));
        weeklyPlan.execute();
    }
}