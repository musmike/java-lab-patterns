package pl.lab.factory.orders.invoice;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 1 (Context)
// Faktura powiązana z konkretnym zamówieniem produkcyjnym.
// Deleguje swoje zachowanie (płatności, rabaty, wydruki) do aktualnego obiektu stanu.
public class OrderInvoice {
    private final ProductionOrder order;
    private double amount;
    private OrderInvoiceState state;

    public OrderInvoice(ProductionOrder order, double baseAmount) {
        this.order = order;
        this.amount = baseAmount;
        this.state = new OrderInvoiceUnpaidState(); // Domyślny stan
    }

    public ProductionOrder getOrder() {
        return order;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Metoda pozwalająca stanom na zmianę głównego stanu faktury
    public void setState(OrderInvoiceState state) {
        this.state = state;
    }

    // Delegacja akcji do aktualnego stanu
    public void pay() {
        state.pay(this);
    }

    public void applyDiscount(double percentage) {
        state.applyDiscount(this, percentage);
    }

    public void generateReceipt() {
        state.generateReceipt(this);
    }

    public void cancel() {
        state.cancel(this);
    }
}
// Koniec, Tydzień 6
// Wzorzec State 1