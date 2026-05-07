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
import pl.lab.factory.orders.pricing.HolidayPricingStrategy;
import pl.lab.factory.orders.pricing.RushOrderPricingStrategy;
import pl.lab.factory.orders.pricing.StandardPricingStrategy;
import pl.lab.factory.processes.context.FactoryContext;
import pl.lab.factory.reporting.CompletedOnlyProductionReport;
import pl.lab.factory.reporting.ConsoleReportFormatter;
import pl.lab.factory.reporting.ProductionReport;
import pl.lab.factory.reporting.SummaryProductionReport;
import pl.lab.factory.reporting.interpreter.AbstractExpression;
import pl.lab.factory.reporting.interpreter.AndExpression;
import pl.lab.factory.reporting.interpreter.OrExpression;
import pl.lab.factory.reporting.interpreter.QuantityExpression;
import pl.lab.factory.reporting.interpreter.StatusExpression;
import pl.lab.factory.reporting.interpreter.TypeExpression;
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

    private static final String APP_CONTEXT = ReportingAndMetricsProcess.class.getName();
    private static final String ORDER_6_ID = "ORD-006";
    private static final int ORDER_6_QTY = 5;

    private static final String QTY_LESS_OP = "<";
    private static final int QTY_LESS_VAL = 6;
    private static final String BASE_BOARD_TYPE = "BaseBoardProductionOrder";

    private static final String ITER_1_ID = "ITER-01"; private static final int ITER_1_QTY = 2;
    private static final String ITER_2_ID = "ITER-02"; private static final int ITER_2_QTY = 8;
    private static final String ITER_3_ID = "ITER-03"; private static final int ITER_3_QTY = 5;
    private static final String ITER_4_ID = "ITER-04"; private static final int ITER_4_QTY = 9;
    private static final String ITER_5_ID = "ITER-05"; private static final int ITER_5_QTY = 6;
    private static final String ITER_6_ID = "ITER-06"; private static final int ITER_6_QTY = 7;

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
        // -- Reports --
        // not completed order example
        ProductionOrder orderD = ProductionOrderFactory.createOrder(
                context.getBoardA(), new OrderContext(ORDER_6_ID, ORDER_6_QTY, context.getSpecPcb()));
        context.getAllOrders().add(orderD);

        runReportingFacade();
        runReportingInterpreter();
    }

    private void runReportingFacade() {
        // Reporting Facade
        ReportingFacade.generate(ReportType.SUMMARY, ReportFormat.CONSOLE, context.getAllOrders());
        ReportingFacade.generate(ReportType.SUMMARY, ReportFormat.CSV, context.getAllOrders());
        ReportingFacade.generate(ReportType.COMPLETED_ONLY, ReportFormat.CONSOLE, context.getAllOrders());
        ReportingFacade.generate(ReportType.COMPLETED_ONLY, ReportFormat.CSV, context.getAllOrders());
    }

    private void runReportingInterpreter() {
        // Reporting Interpreter
        AbstractExpression query1 = new StatusExpression(OrderStatus.COMPLETED);
        AbstractExpression query2 = new AndExpression(
                new StatusExpression(OrderStatus.PENDING),
                new QuantityExpression(QTY_LESS_OP, QTY_LESS_VAL)
        );
        AbstractExpression query3 = new OrExpression(
                new TypeExpression(BASE_BOARD_TYPE),
                new StatusExpression(OrderStatus.COMPLETED)
        );

        ReportingFacade.generateDynamic(query1, ReportFormat.CONSOLE, context.getAllOrders());
        ReportingFacade.generateDynamic(query2, ReportFormat.CONSOLE, context.getAllOrders());
        ReportingFacade.generateDynamic(query3, ReportFormat.CSV, context.getAllOrders());
    }

    private void testOrderStatusMemento() {
        // Order Status Memento
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
        ProductionOrder orderE = ProductionOrderFactory.createOrder(context.getBoardA(), new OrderContext(ITER_1_ID, ITER_1_QTY, context.getSpecPcb()));
        ProductionOrder orderF = ProductionOrderFactory.createOrder(context.getElectronicBoardB(), new OrderContext(ITER_2_ID, ITER_2_QTY, context.getSpecElectronic()));
        ProductionOrder orderG = ProductionOrderFactory.createOrder(context.getBoardA(), new OrderContext(ITER_3_ID, ITER_3_QTY, context.getSpecPcb()));
        ProductionOrder orderH = ProductionOrderFactory.createOrder(context.getElectronicBoardB(), new OrderContext(ITER_4_ID, ITER_4_QTY, context.getSpecElectronic()));
        ProductionOrder orderI = ProductionOrderFactory.createOrder(context.getBoardA(), new OrderContext(ITER_5_ID, ITER_5_QTY, context.getSpecPcb()));
        ProductionOrder orderJ = ProductionOrderFactory.createOrder(context.getElectronicBoardB(), new OrderContext(ITER_6_ID, ITER_6_QTY, context.getSpecElectronic()));

        orderE.setStatus(OrderStatus.COMPLETED);
        orderF.setStatus(OrderStatus.COMPLETED);

        collection.addOrder(orderE); collection.addOrder(orderF); collection.addOrder(orderG);
        collection.addOrder(orderH); collection.addOrder(orderI); collection.addOrder(orderJ);
    }

    private void testOrderIteratorSearches() {
        OrderCollection orderCollection = ProductionQueue.getInstance();

        System.out.println("\n--- Searching for all orders with status: COMPLETED ---");
        OrderIterator completedOrdersIterator = orderCollection.statusIterator(OrderStatus.COMPLETED);
        while (completedOrdersIterator.hasNext()) {
            System.out.println(completedOrdersIterator.next().toString());
        }

        System.out.println("\n--- Searching for all orders with status: PENDING ---");
        OrderIterator pendingOrdersIterator = orderCollection.statusIterator(OrderStatus.PENDING);
        while (pendingOrdersIterator.hasNext()) {
            System.out.println(pendingOrdersIterator.next().toString());
        }

        System.out.println("\n--- Searching for PENDING orders with quantity > 6 ---");
        pendingOrdersIterator.reset();
        while (pendingOrdersIterator.hasNextByQuantity(">", 6)) {
            System.out.println(pendingOrdersIterator.nextByQuantity().toString());
        }
    }

    private void testIspReporting() {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "--- ISP 1 TEST ---");

        ReportGenerator generator = new SimpleReportGenerator();
        ReportFormatter formatter = new SimpleReportFormatter();
        ReportDispatcher dispatcher = new SimpleReportDispatcher();

        generator.generateReport(context.getOrderA());

        String csvData = formatter.formatAsCsv(context.getOrderA());
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "Formatted data: " + csvData);

        dispatcher.archiveReport(context.getOrderA());
        dispatcher.sendReportByEmail(context.getOrderA());

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