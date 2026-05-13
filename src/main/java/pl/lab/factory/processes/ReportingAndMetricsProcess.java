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

import java.util.List;

public class ReportingAndMetricsProcess {

    private static final String APP_CONTEXT = ReportingAndMetricsProcess.class.getSimpleName();
    private static final String NOT_COMPLETED_ORDER_ID = "ORD-006";
    private static final int NOT_COMPLETED_ORDER_QUANTITY = 5;
    private static final int SEARCH_QUANTITY_THRESHOLD = 6;
    private static final String BASE_BOARD_TYPE = "BaseBoardProductionOrder";

    private static class IteratorTestOrder {
        private final String id;
        private final int qty;
        private final OrderStatus status;
        private final boolean isElectronic;

        private IteratorTestOrder(Builder builder) {
            this.id = builder.id;
            this.qty = builder.qty;
            this.status = builder.status;
            this.isElectronic = builder.isElectronic;
        }

        public String getId() { return id; }
        public int getQty() { return qty; }
        public OrderStatus getStatus() { return status; }
        public boolean isElectronic() { return isElectronic; }

        public static class Builder {
            private String id;
            private int qty;
            private OrderStatus status;
            private boolean isElectronic;

            public Builder id(String id) { this.id = id; return this; }
            public Builder qty(int qty) { this.qty = qty; return this; }
            public Builder status(OrderStatus status) { this.status = status; return this; }
            public Builder isElectronic(boolean isElectronic) { this.isElectronic = isElectronic; return this; }

            public IteratorTestOrder build() { return new IteratorTestOrder(this); }
        }
    }

    private static final List<IteratorTestOrder> ITERATOR_TEST_ORDERS = List.of(
            new IteratorTestOrder.Builder().id("ITER-01").qty(2).status(OrderStatus.COMPLETED).isElectronic(false).build(),
            new IteratorTestOrder.Builder().id("ITER-02").qty(8).status(OrderStatus.COMPLETED).isElectronic(true).build(),
            new IteratorTestOrder.Builder().id("ITER-03").qty(5).status(OrderStatus.PENDING).isElectronic(false).build(),
            new IteratorTestOrder.Builder().id("ITER-04").qty(9).status(OrderStatus.PENDING).isElectronic(true).build(),
            new IteratorTestOrder.Builder().id("ITER-05").qty(6).status(OrderStatus.PENDING).isElectronic(false).build(),
            new IteratorTestOrder.Builder().id("ITER-06").qty(7).status(OrderStatus.PENDING).isElectronic(true).build()
    );

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
        createOrderNotCompletedOrder();
        runReportingFacade();
        runReportingInterpreter();
    }

    private void createOrderNotCompletedOrder() {
        ProductionOrder notCompletedOrder = ProductionOrderFactory.createOrder(
                context.getStandardPcbBoard(), new OrderContext(NOT_COMPLETED_ORDER_ID, 
                    NOT_COMPLETED_ORDER_QUANTITY, context.getSpecPcb()));
        context.getAllOrders().add(notCompletedOrder);
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
                new QuantityExpression(QuantityOperator.LESS_THAN, SEARCH_QUANTITY_THRESHOLD)
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
        for (IteratorTestOrder testOrder : ITERATOR_TEST_ORDERS) {
            ProductionOrder order = buildOrderForIterator(testOrder);
            order.setStatus(testOrder.getStatus());
            collection.addOrder(order);
        }
    }

    private ProductionOrder buildOrderForIterator(IteratorTestOrder testOrder) {
        if (testOrder.isElectronic()) {
            return ProductionOrderFactory.createOrder(context.getHotfixElectronicBoard(),
                    new OrderContext(testOrder.getId(), testOrder.getQty(), context.getSpecElectronic()));
        } else {
            return ProductionOrderFactory.createOrder(context.getStandardPcbBoard(),
                    new OrderContext(testOrder.getId(), testOrder.getQty(), context.getSpecPcb()));
        }
    }

    private void testOrderIteratorSearches() {
        OrderCollection orderCollection = ProductionQueue.getInstance();

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "--- Searching for all orders with status: COMPLETED ---");
        printIteratorResults(orderCollection.statusIterator(OrderStatus.COMPLETED));

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "--- Searching for all orders with status: PENDING ---");
        printIteratorResults(orderCollection.statusIterator(OrderStatus.PENDING));

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "--- Searching for PENDING orders with quantity > " + SEARCH_QUANTITY_THRESHOLD + " ---");
        OrderIterator pendingOrdersIterator = orderCollection.statusIterator(OrderStatus.PENDING);
        while (pendingOrdersIterator.hasNextByQuantity(QuantityOperator.GREATER_THAN, SEARCH_QUANTITY_THRESHOLD)) {
            SystemLogger.INSTANCE.logInfo("ITERATOR-RESULT", pendingOrdersIterator.nextByQuantity().toString());
        }
    }

    private void printIteratorResults(OrderIterator iterator) {
        while (iterator.hasNext()) {
            SystemLogger.INSTANCE.logInfo("ITERATOR-RESULT", iterator.next().toString());
        }
    }

    private void testIspReporting() {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "--- ISP 1 TEST ---");

        ReportGenerator reportGenerator = new SimpleReportGenerator();
        ReportFormatter reportFormatter = new SimpleReportFormatter();
        ReportDispatcher reportDispatcher = new SimpleReportDispatcher();

        reportGenerator.generateReport(context.getPcbOrder());

        String csvData = reportFormatter.formatAsCsv(context.getPcbOrder());
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "Formatted data: " + csvData);

        reportDispatcher.archiveReport(context.getPcbOrder());
        reportDispatcher.sendReportByEmail(context.getPcbOrder());

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