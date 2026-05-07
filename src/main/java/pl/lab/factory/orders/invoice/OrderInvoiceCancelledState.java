package pl.lab.factory.orders.invoice;

import pl.lab.factory.orders.pricing.PricingStrategy;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 1 (Concrete State)
// Faktura anulowana. Żadne akcje nie są już dozwolone.
public class OrderInvoiceCancelledState implements OrderInvoiceState {

    @Override
    public void pay(OrderInvoice invoice) {
        throw new IllegalStateException(MSG_ERR_CANCELLED);
    }

    @Override
    public void applyDiscount(OrderInvoice invoice, double percentage) {
        throw new IllegalStateException(MSG_ERR_CANCELLED);
    }

    @Override
    public void generateReceipt(OrderInvoice invoice) {
        throw new IllegalStateException(MSG_ERR_CANCELLED);
    }

    @Override
    public void cancel(OrderInvoice invoice) {
        throw new IllegalStateException(MSG_ERR_CANCELLED);
    }

    @Override
    public void recalculatePrice(OrderInvoice invoice, PricingStrategy strategy) {
        throw new IllegalStateException(MSG_ERR_CANCELLED);
    }

    @Override
    public void confirm(OrderInvoice invoice) {
        throw new IllegalStateException(MSG_ERR_CANCELLED);
    }

    @Override
    public boolean isRevenueRecognized() {
        return false;
    }
}
// Koniec, Tydzień 6
// Wzorzec State 1