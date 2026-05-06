package pl.lab.factory.orders.invoice;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.pricing.CurrencyFormatter;
import pl.lab.factory.orders.pricing.PricingStrategy;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 1 (Concrete State)
// Stan początkowy. W tym stanie można dowolnie zmieniać strategię i przeliczać cenę.
// Nie można tu jednak opłacić faktury ani wygenerować paragonu.
public class OrderInvoiceDraftState implements OrderInvoiceState {

    private static final double PERCENTAGE_DIVISOR = 100.0;

    @Override
    public void pay(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logError("INVOICE", MSG_ERR_DRAFT);
    }

    @Override
    public void applyDiscount(OrderInvoice invoice, double percentage) {
        double currentAmount = invoice.getAmount();
        double deduction = currentAmount * (percentage / PERCENTAGE_DIVISOR);
        invoice.setAmount(currentAmount - deduction);
        SystemLogger.INSTANCE.logInfo("INVOICE",
                "Discount of " + percentage + "% applied to draft. New amount: $" + CurrencyFormatter.format(invoice.getAmount()));
    }

    @Override
    public void generateReceipt(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logError("INVOICE", MSG_ERR_DRAFT);
    }

    @Override
    public void cancel(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("INVOICE", "Draft invoice has been discarded.");
        invoice.setState(new OrderInvoiceCancelledState());
    }

    @Override
    public void recalculatePrice(OrderInvoice invoice, PricingStrategy strategy) {
        double newAmount = strategy.calculatePrice(invoice.getOrder());
        invoice.setAmount(newAmount);
        SystemLogger.INSTANCE.logInfo("INVOICE", "Draft invoice recalculated using "
                + strategy.getClass().getSimpleName() + ". New amount: $" + CurrencyFormatter.format(newAmount));
    }

    @Override
    public void confirm(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("INVOICE", "Invoice for order "
                + invoice.getOrder().getId() + " has been confirmed and is ready for payment.");
        invoice.setState(new OrderInvoiceUnpaidState());
    }

    @Override
    public boolean isRevenueRecognized() {
        return false;
    }
}
// Koniec, Tydzień 6
// Wzorzec State 1