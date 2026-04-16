package pl.lab.factory.orders.invoice;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.pricing.PricingStrategy;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 1 (Concrete State)
// Faktura anulowana. Żadne akcje nie są już dozwolone.
public class OrderInvoiceCancelledState implements OrderInvoiceState {

    @Override
    public void pay(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logError("INVOICE", "Action denied. Invoice is CANCELLED.");
    }

    @Override
    public void applyDiscount(OrderInvoice invoice, double percentage) {
        SystemLogger.INSTANCE.logError("INVOICE", "Action denied. Invoice is CANCELLED.");
    }

    @Override
    public void generateReceipt(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logError("INVOICE", "Action denied. Invoice is CANCELLED.");
    }

    @Override
    public void cancel(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logError("INVOICE", "Invoice is already CANCELLED.");
    }

    @Override
    public void recalculatePrice(OrderInvoice invoice, PricingStrategy strategy) {
        SystemLogger.INSTANCE.logError("INVOICE", "Action denied. Invoice is CANCELLED.");
    }

    @Override
    public void confirm(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logError("INVOICE", "Action denied. Invoice is CANCELLED.");
    }

    @Override
    public boolean isRevenueRecognized() {
        return false;
    }
}
// Koniec, Tydzień 6
// Wzorzec State 1