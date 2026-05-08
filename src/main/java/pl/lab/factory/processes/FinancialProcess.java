package pl.lab.factory.processes;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderContext;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.ProductionOrderFactory;
import pl.lab.factory.orders.invoice.OrderInvoice;
import pl.lab.factory.orders.invoice.dispatch.B2bApiInvoiceDispatcher;
import pl.lab.factory.orders.invoice.dispatch.EmailInvoiceDispatcher;
import pl.lab.factory.orders.invoice.dispatch.InvoiceDispatcher;
import pl.lab.factory.orders.loyalty.CustomerLoyaltyService;
import pl.lab.factory.orders.loyalty.TierConfig;
import pl.lab.factory.orders.loyalty.VolumeBonusStrategy;
import pl.lab.factory.orders.pricing.*;
import pl.lab.factory.reporting.invoicereport.PaidInvoiceRevenuePolicy;
import pl.lab.factory.reporting.invoicereport.RevenueCalculator;
import pl.lab.factory.reporting.invoicereport.RevenueFilter;
import pl.lab.factory.reporting.invoicereport.RevenuePolicy;
import pl.lab.factory.reporting.invoicereport.RevenueService;
import pl.lab.factory.orders.visitor.EuVatVisitor;
import pl.lab.factory.orders.visitor.UsaCustomsVisitor;
import pl.lab.factory.processes.context.FactoryContext;

import java.util.List;

public class FinancialProcess {

    private static final String APP_CONTEXT = FinancialProcess.class.getName();

    private static final String ORDER_Z_ID = "ORD-Z";
    private static final int ORDER_Z_QTY = 150;
    private static final double VIP_DISCOUNT_PERCENT = 10.0;

    private static final String TIER_GOLD = "GOLD";
    private static final String TIER_PLATINUM = "PLATINUM";
    private static final int PLATINUM_BASE_PTS = 500;
    private static final int PLATINUM_THRESH = 5;
    private static final int PLATINUM_BONUS = 1000;

    private final FactoryContext context;

    public FinancialProcess(FactoryContext context) {
        this.context = context;
    }

    public void execute() {
        validateContext();
        testInvoiceLifecycle();
        testRevenueCalculation();
        testCustomerLoyaltyPoints();
        testInvoiceDispatch();
        testTaxAndCustomsCalculator();
        testLspPricingStrategies();
    }

    private void validateContext() {
        if (context.getOrderA() == null || context.getOrderB() == null || context.getOrderC() == null) {
            throw new IllegalStateException("Orders must be initialized prior to Financial Process.");
        }
    }

    private void testInvoiceLifecycle() {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== STATE 1 AND STRATEGY 1 PATTERNS TEST (INVOICE LIFECYCLE) ===");

        // -- Order Invoice --
        OrderInvoice invoiceA = initializeDraftInvoice();
        verifyDraftRestrictions(invoiceA);
        applyDiscountsAndConfirm(invoiceA);
        verifyConfirmedRestrictions(invoiceA);
        payAndGenerateReceipt(invoiceA);
        cancelAdditionalInvoice();

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== END OF STATE 1 AND STRATEGY 1 PATTERNS TEST ===");
    }

    private OrderInvoice initializeDraftInvoice() {
        ProductionOrder orderZ = ProductionOrderFactory.createOrder(
                context.getBoardA(), new OrderContext(ORDER_Z_ID, ORDER_Z_QTY, context.getSpecPcb()));
        OrderInvoice invoice = new OrderInvoice(orderZ, new StandardPricingStrategy());
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "Initial draft amount: $" + CurrencyFormatter.format(invoice.getAmount()));
        context.setInvoiceA(invoice);
        return invoice;
    }

    private void attemptInvoiceAction(Runnable action) {
        try {
            action.run();
        } catch (IllegalStateException e) {
            SystemLogger.INSTANCE.logError("INVOICE", e.getMessage());
    }
}

    private void verifyDraftRestrictions(OrderInvoice invoice) {
        // Can't pay and generate receipt before confirming
        attemptInvoiceAction(invoice::pay);
        attemptInvoiceAction(invoice::generateReceipt);
    }

    private void applyDiscountsAndConfirm(OrderInvoice invoice) {
        invoice.setPricingStrategy(new VipPricingStrategy());
        invoice.recalculatePrice();
        invoice.applyDiscount(VIP_DISCOUNT_PERCENT);
        invoice.confirm();
    }

    private void verifyConfirmedRestrictions(OrderInvoice invoice) {
        // Can't change price or apply discount after confirmation
        attemptInvoiceAction(() -> { invoice.setPricingStrategy(new BulkPricingStrategy()); invoice.recalculatePrice(); });
        attemptInvoiceAction(() -> invoice.applyDiscount(VIP_DISCOUNT_PERCENT));
        // Can't generate receipt before payment
        attemptInvoiceAction(invoice::generateReceipt);
    }

    private void payAndGenerateReceipt(OrderInvoice invoice) {
        invoice.pay();
        // Can't cancel after payment
        attemptInvoiceAction(invoice::cancel);
        invoice.generateReceipt();
    }

    private void cancelAdditionalInvoice() {
        OrderInvoice invoiceB = new OrderInvoice(context.getOrderB(), new StandardPricingStrategy());
        invoiceB.cancel();
        context.setInvoiceB(invoiceB);
    }

    private void testRevenueCalculation() {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== SRP 1 TEST (REVENUE CALCULATION) ===");

        OrderInvoice invoiceC = createPaidInvoice(context.getOrderC());
        OrderInvoice invoiceD = createPaidInvoice(context.getOrderBaseClone());

        RevenuePolicy policy = new PaidInvoiceRevenuePolicy();
        RevenueFilter filter = new RevenueFilter(policy);
        RevenueCalculator calculator = new RevenueCalculator();

        RevenueService revenueService = new RevenueService(filter, calculator);

        List<OrderInvoice> allInvoices = List.of(context.getInvoiceA(), context.getInvoiceB(), invoiceC, invoiceD);
        double totalRevenue = revenueService.calculateRevenue(allInvoices);

        SystemLogger.INSTANCE.logInfo("REVENUE-REPORT", "Calculated Total Revenue: $" + CurrencyFormatter.format(totalRevenue));
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== END OF SRP 1 TEST ===");
    }

    private OrderInvoice createPaidInvoice(ProductionOrder order) {
        OrderInvoice invoice = new OrderInvoice(order, new StandardPricingStrategy());
        invoice.recalculatePrice();
        invoice.confirm();
        invoice.pay();
        return invoice;
    }

    private void testCustomerLoyaltyPoints() {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== OCP 1 TEST (CUSTOMER LOYALTY POINTS) ===");

        CustomerLoyaltyService loyaltyService = new CustomerLoyaltyService();
        loyaltyService.calculateByStrategy(context.getOrderA(), new VolumeBonusStrategy());
        loyaltyService.calculateByTier(context.getOrderB(), TIER_GOLD);

        loyaltyService.registerTier(TIER_PLATINUM, new TierConfig(PLATINUM_BASE_PTS, PLATINUM_THRESH, PLATINUM_BONUS));
        loyaltyService.calculateByTier(context.getOrderC(), TIER_PLATINUM);

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== END OF OCP 1 TEST ===");
    }

    private void testInvoiceDispatch() {
        // -- Invoice Dispatch --
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== TEMPLATE METHOD 1 PATTERN TEST (INVOICE DISPATCH) ===");

        InvoiceDispatcher emailDispatcher = new EmailInvoiceDispatcher();
        InvoiceDispatcher b2bApiDispatcher = new B2bApiInvoiceDispatcher();

        b2bApiDispatcher.dispatch(context.getInvoiceA());
        emailDispatcher.dispatch(context.getInvoiceA());
        emailDispatcher.dispatch(context.getInvoiceB());

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== END OF TEMPLATE METHOD 1 PATTERN TEST ===");
    }

    private void testTaxAndCustomsCalculator() {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== VISITOR 1 PATTERN TEST (TAX AND CUSTOMS CALCULATION) ===");

        UsaCustomsVisitor usaVisitor = new UsaCustomsVisitor();
        EuVatVisitor euVisitor = new EuVatVisitor();

        context.getOrderA().accept(usaVisitor);
        context.getOrderA().accept(euVisitor);
        context.getOrderB().accept(usaVisitor);
        context.getOrderB().accept(euVisitor);

        SystemLogger.INSTANCE.logInfo("TAX-SUMMARY", "Total USA Customs expected: $" + CurrencyFormatter.format(usaVisitor.getTotalCustomsTax()));
        SystemLogger.INSTANCE.logInfo("TAX-SUMMARY", "Total EU VAT expected: $" + CurrencyFormatter.format(euVisitor.getTotalVat()));

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "=== END OF VISITOR 1 PATTERN TEST ===");
    }

    private void testLspPricingStrategies() {
        StandardPricingStrategy standardPricing = new StandardPricingStrategy();
        HolidayPricingStrategy holidayPricing = new HolidayPricingStrategy();
        RushOrderPricingStrategy rushPricingDerived = new RushOrderPricingStrategy();
        StandardPricingStrategy rushPricingBaseType = new RushOrderPricingStrategy();

        testPriceCalculation(standardPricing, context.getOrderA());
        testPriceCalculation(holidayPricing, context.getOrderA());
        testPriceCalculation(rushPricingDerived, context.getOrderA());
        testPriceCalculation(rushPricingBaseType, context.getOrderA());
    }

    private void testPriceCalculation(StandardPricingStrategy strategy, ProductionOrder order) {
        double price = strategy.calculatePrice(order);
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "Calculated price for " + order.getId()
                + " using the base (or derived) class. Result: $" + price);
    }
}