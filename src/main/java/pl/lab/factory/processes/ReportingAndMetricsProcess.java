package pl.lab.factory.processes;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderContext;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.ProductionOrderFactory;
import pl.lab.factory.orders.ProductionQueue;
import pl.lab.factory.orders.command.ClearQueueCommand;
import pl.lab.factory.orders.command.QueueCommandInvoker;
import pl.lab.factory.orders.iterator.OrderCollection;
import pl.lab.factory.orders.iterator.OrderIterator;
import pl.lab.factory.processes.context.FactoryContext;
import pl.lab.factory.reporting.CompletedOnlyProductionReport;
import pl.lab.factory.reporting.ConsoleReportFormatter;
import pl.lab.factory.reporting.ProductionReport;
import pl.lab.factory.reporting.SummaryProductionReport;
import pl.lab.factory.reporting.interpreter.*;
import pl.lab.factory.reporting.reportingfacade.ReportFormat;
import pl.lab.factory.reporting.reportingfacade.ReportType;
import pl.lab.factory.reporting.reportingfacade.ReportingFacade;
import pl.lab.factory.reporting.reportingsystem.ReportDispatcher;
import pl.lab.factory.reporting.reportingsystem.ReportFormatter;
import pl.lab.factory.reporting.reportingsystem.ReportGenerator;
import pl.lab.factory.reporting.reportingsystem.SimpleReportDispatcher;
import pl.lab.factory.reporting.reportingsystem.SimpleReportFormatter;
import pl.lab.factory.reporting.reportingsystem.SimpleReportGenerator;

public class ReportingAndMetricsProcess {

    private static final String APP_CONTEXT = ReportingAndMetricsProcess.class.getSimpleName();
    private static final String NOT_COMPLETED_ORDER_ID = "ORD-006";
    private static final int NOT_COMPLETED_ORDER_QUANTITY = 5;
    private static final int QUANTITY_LESS_THAN_VAL = 6;
    private static final String BASE_BOARD_TYPE = "BaseBoardProductionOrder";

    private static final String ITERATION_1_ID = "ITER-01"; private static final int ITERATION_1_QUANTITY = 2;
    private static final String ITERATION_2_ID = "ITER-02"; private static final int ITERATION_2_QUANTITY = 8;
    private static final String ITERATION_3_ID = "ITER-03"; private static final int ITERATION_3_QUANTITY = 5;
    private static final String ITERATION_4_ID = "ITER-04"; private static final int ITERATION_4_QUANTITY = 9;
    private static final String ITERATION_5_ID = "ITER-05"; private static final int ITERATION_5_QUANTITY = 6;
    private static final String ITERATION_6_ID = "ITER-06"; private static final int ITERATION_6_QUANTITY = 7;

    private final FactoryContext context;

    public ReportingAndMetricsProcess(FactoryContext context) {
        this.context = context;
    }

    public void execute() {
        validateContext();
        testReports();
        testOrderStatusMemento();
        testOrderIteratorSetup();
        testOrderIteratorSearches();
        testIspReporting();
        testLspReportingClasses();
    }

    private void validateContext() {
        if (context.getAllOrders() == null || context.getAllOrders().isEmpty()) {
            throw new IllegalStateException("All orders must be initialized prior to Reporting.");
        }
    }

    private void testReports() {
        ProductionOrder notCompletedOrder = ProductionOrderFactory.createOrder(
                context.getStandardPcbBoard(), new OrderContext(NOT_COMPLETED_ORDER_ID, 
                    NOT_COMPLETED_ORDER_QUANTITY, context.getSpecPcb()));
        context.getAllOrders().add(notCompletedOrder);

        runReportingFacade();
        runReportingInterpreter();
    }

    private void runReportingFacade() {
        ReportingFacade.generate(ReportType.SUMMARY, ReportFormat.CONSOLE, context.getAllOrders());
        ReportingFacade.generate(ReportType.SUMMARY, ReportFormat.CSV, context.getAllOrders());
        ReportingFacade.generate(ReportType.COMPLETED_ONLY, ReportFormat.CONSOLE, context.getAllOrders());
        ReportingFacade.generate(ReportType.COMPLETED_ONLY, ReportFormat.CSV, context.getAllOrders());
    }

    private void runReportingInterpreter() {
        AbstractExpression completedOrdersQuery = new StatusExpression(OrderStatus.COMPLETED);
        AbstractExpression pendingSmallQuantityOrdersQuery = new AndExpression(
                new StatusExpression(OrderStatus.PENDING),
                new QuantityExpression(QuantityOperator.LESS_THAN, QUANTITY_LESS_THAN_VAL)
        );
        AbstractExpression boardTypeOrCompletedOrdersQuery = new OrExpression(
                new TypeExpression(BASE_BOARD_TYPE),
                new StatusExpression(OrderStatus.COMPLETED)
        );

        ReportingFacade.generateDynamic(completedOrdersQuery, ReportFormat.CONSOLE, context.getAllOrders());
        ReportingFacade.generateDynamic(pendingSmallQuantityOrdersQuery, ReportFormat.CONSOLE, context.getAllOrders());
        ReportingFacade.generateDynamic(boardTypeOrCompletedOrdersQuery, ReportFormat.CSV, context.getAllOrders());
    }

    private void testOrderStatusMemento() {
        ProductionOrder targetOrder = context.getAllOrders().get(context.getAllOrders().size() - 1);

        targetOrder.setStatus(OrderStatus.COMPLETED);
        targetOrder.setStatus(OrderStatus.PACKED);
        targetOrder.setStatus(OrderStatus.SHIPPED);

        targetOrder.restoreStatus();
        targetOrder.restoreStatus();
        targetOrder.restoreStatus();
        targetOrder.restoreStatus();
    }

    private void testOrderIteratorSetup() {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== ORDER ITERATOR PATTERN TEST ===");

        OrderCollection orderCollection = ProductionQueue.getInstance();
        new QueueCommandInvoker().executeCommand(new ClearQueueCommand());

        populateIteratorQueue(orderCollection);
    }

    private void populateIteratorQueue(OrderCollection collection) {
        ProductionOrder completedPcbOrder = ProductionOrderFactory.createOrder(context.getStandardPcbBoard(),
                new OrderContext(ITERATION_1_ID, ITERATION_1_QUANTITY, context.getSpecPcb()));
        ProductionOrder completedElectronicOrder = ProductionOrderFactory.createOrder(context.getHotfixElectronicBoard(),
                new OrderContext(ITERATION_2_ID, ITERATION_2_QUANTITY, context.getSpecElectronic()));
        ProductionOrder pendingPcbOrderSmall = ProductionOrderFactory.createOrder(context.getStandardPcbBoard(),
                new OrderContext(ITERATION_3_ID, ITERATION_3_QUANTITY, context.getSpecPcb()));
        ProductionOrder pendingElectronicOrderLarge = ProductionOrderFactory.createOrder(context.getHotfixElectronicBoard(),
                new OrderContext(ITERATION_4_ID, ITERATION_4_QUANTITY, context.getSpecElectronic()));
        ProductionOrder pendingPcbOrderMedium = ProductionOrderFactory.createOrder(context.getStandardPcbBoard(),
                new OrderContext(ITERATION_5_ID, ITERATION_5_QUANTITY, context.getSpecPcb()));
        ProductionOrder pendingElectronicOrderMid = ProductionOrderFactory.createOrder(context.getHotfixElectronicBoard(),
                new OrderContext(ITERATION_6_ID, ITERATION_6_QUANTITY, context.getSpecElectronic()));

        completedPcbOrder.setStatus(OrderStatus.COMPLETED);
        completedElectronicOrder.setStatus(OrderStatus.COMPLETED);

        collection.addOrder(completedPcbOrder); collection.addOrder(completedElectronicOrder); collection.addOrder(pendingPcbOrderSmall);
        collection.addOrder(pendingElectronicOrderLarge); collection.addOrder(pendingPcbOrderMedium); collection.addOrder(pendingElectronicOrderMid);
    }

    private void testOrderIteratorSearches() {
        OrderCollection orderCollection = ProductionQueue.getInstance();

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "--- Searching for all orders with status: COMPLETED ---");
        OrderIterator completedOrdersIterator = orderCollection.statusIterator(OrderStatus.COMPLETED);
        while (completedOrdersIterator.hasNext()) {
            SystemLogger.INSTANCE.logInfo("ITERATOR-RESULT", completedOrdersIterator.next().toString());
        }

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "--- Searching for all orders with status: PENDING ---");
        OrderIterator pendingOrdersIterator = orderCollection.statusIterator(OrderStatus.PENDING);
        while (pendingOrdersIterator.hasNext()) {
            SystemLogger.INSTANCE.logInfo("ITERATOR-RESULT", pendingOrdersIterator.next().toString());
        }

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "--- Searching for PENDING orders with quantity > 6 ---");
        pendingOrdersIterator.reset();
        while (pendingOrdersIterator.hasNextByQuantity(QuantityOperator.GREATER_THAN, 6)) {
            SystemLogger.INSTANCE.logInfo("ITERATOR-RESULT", pendingOrdersIterator.nextByQuantity().toString());
        }
    }

    private void testIspReporting() {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "--- ISP 1 TEST ---");

        ReportGenerator generator = new SimpleReportGenerator();
        ReportFormatter formatter = new SimpleReportFormatter();
        ReportDispatcher dispatcher = new SimpleReportDispatcher();

        generator.generateReport(context.getPcbOrder());

        String csvData = formatter.formatAsCsv(context.getPcbOrder());
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "Formatted data: " + csvData);

        dispatcher.archiveReport(context.getPcbOrder());
        dispatcher.sendReportByEmail(context.getPcbOrder());

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "--- ISP 1 TEST END ---");
    }

    private void testLspReportingClasses() {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "--- LSP TEST START ---");

        // Klasy pochodne z typem klasy pochodnej
        CompletedOnlyProductionReport reportCompletedDerived = new CompletedOnlyProductionReport(new ConsoleReportFormatter());
        SummaryProductionReport reportAllDerived = new SummaryProductionReport(new ConsoleReportFormatter());
        reportCompletedDerived.generate(context.getAllOrders());
        reportAllDerived.generate(context.getAllOrders());

        // Klasy pochodne z typem klasy bazowej (abstrakcyjnej)
        ProductionReport reportCompletedBase = new CompletedOnlyProductionReport(new ConsoleReportFormatter());
        ProductionReport reportAllBase = new SummaryProductionReport(new ConsoleReportFormatter());
        reportCompletedBase.generate(context.getAllOrders());
        reportAllBase.generate(context.getAllOrders());

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "--- LSP TEST END ---");
    }
}