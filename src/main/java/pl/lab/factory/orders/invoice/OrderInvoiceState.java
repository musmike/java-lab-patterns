package pl.lab.factory.orders.invoice;

import pl.lab.factory.orders.pricing.PricingStrategy;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 1 (Interface State)
// Definiuje wszystkie operacje, które zachowują się różnie w zależności od stanu faktury.
public interface OrderInvoiceState {
    String MSG_ERR_CANCELLED = "Action denied. Invoice is CANCELLED.";
    String MSG_ERR_AWAITS_PAYMENT = "Action denied. Invoice has already been confirmed and awaits payment.";
    String MSG_ERR_ALREADY_PAID = "Action denied. Invoice is already PAID.";
    String MSG_ERR_DRAFT = "Action denied. Invoice is still a DRAFT.";

    void pay(OrderInvoice invoice);
    void applyDiscount(OrderInvoice invoice, double percentage);
    void generateReceipt(OrderInvoice invoice);
    void cancel(OrderInvoice invoice);
    void recalculatePrice(OrderInvoice invoice, PricingStrategy strategy);
    void confirm(OrderInvoice invoice);
    boolean isRevenueRecognized();
}
// Koniec, Tydzień 6
// Wzorzec State 1