package pl.lab.factory.orders.invoice;

import pl.lab.factory.orders.pricing.PricingStrategy;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 1 (Interface State)
// Definiuje wszystkie operacje, które zachowują się różnie w zależności od stanu faktury.
public interface OrderInvoiceState {
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