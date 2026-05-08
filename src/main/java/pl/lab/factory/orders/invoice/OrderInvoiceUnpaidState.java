package pl.lab.factory.orders.invoice;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.pricing.PricingStrategy;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 1 (Concrete State)
// Faktura czeka na opłacenie. Nie można modyfikować ceny ani wydać paragonu.
public class OrderInvoiceUnpaidState implements OrderInvoiceState {

    @Override
    public void pay(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("INVOICE", "Payment received for order "
                + invoice.getOrder().getId() + ".");
        invoice.setState(new OrderInvoicePaidState());
    }

    @Override
    public void applyDiscount(OrderInvoice invoice, double percentage) {
        throw new IllegalStateException(MSG_ERR_AWAITS_PAYMENT);
    }

    @Override
    public void generateReceipt(OrderInvoice invoice) {
        throw new IllegalStateException("Cannot generate receipt. Invoice for "
                + invoice.getOrder().getId() + " is UNPAID.");
    }

    @Override
    public void cancel(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("INVOICE", "Invoice cancelled successfully.");
        invoice.setState(new OrderInvoiceCancelledState());
    }

    @Override
    public void recalculatePrice(OrderInvoice invoice, PricingStrategy strategy) {
        throw new IllegalStateException(MSG_ERR_AWAITS_PAYMENT);
    }

    @Override
    public void confirm(OrderInvoice invoice) {
        throw new IllegalStateException("Invoice is already confirmed.");
    }

    @Override
    public boolean isRevenueRecognized() {
        return false;
    }
}
// Koniec, Tydzień 6
// Wzorzec State 1
