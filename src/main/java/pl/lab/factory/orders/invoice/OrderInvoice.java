package pl.lab.factory.orders.invoice;

import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.pricing.PricingStrategy;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 1 (Context)
// Faktura powiązana z konkretnym zamówieniem produkcyjnym.
// Deleguje swoje zachowanie (płatności, rabaty, wydruki) do aktualnego obiektu stanu.

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 1 (Context)
// Faktura jest również kontekstem dla strategii wyceny. Przechowuje referencję
// do obiektu strategii i używa go do obliczenia swojej wartości zamówienia.

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada odwracania zależności (DIP) - wersja z interfejsem 1 (Moduł wysokopoziomowy)
// Moduł wysokopoziomowy (OrderInvoice) zależy od abstrakcji (interfejsu PricingStrategy),
// a nie od konkretnej implementacji.
public class OrderInvoice {
    private final ProductionOrder order;
    private double amount;
    private OrderInvoiceState state;

    public OrderInvoice(ProductionOrder order, PricingStrategy pricingStrategy) {
        this.order = order;
        this.state = new OrderInvoiceDraftState(); // Domyślny stan
        this.pricingStrategy = pricingStrategy;
        this.amount = pricingStrategy.calculatePrice(order);
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



    // Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
    // Wzorzec Strategy 1

    // Pole na strategię, którą można zmieniać
    private PricingStrategy pricingStrategy;

    // Setter do zmiany strategii w locie
    public void setPricingStrategy(PricingStrategy newStrategy) {
        this.pricingStrategy = newStrategy;
    }

    // Metoda wykonująca strategię
    // Deleguje wykonanie aktualnej strategii do obiektu stanu,
    // aby upewnić się, że przeliczenie jest dozwolone tylko w odpowiednim momencie (w stanie Draft).
    public void recalculatePrice() {
        state.recalculatePrice(this, pricingStrategy);
    }

    // Koniec, Tydzień 6
    // Wzorzec Strategy 1




    // Metoda pozwalająca stanom na zmianę głównego stanu faktury
    public void setState(OrderInvoiceState state) {
        this.state = state;
    }

    public OrderInvoiceState getState() {
        return state;
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

    public void confirm() {
        state.confirm(this);
    }




}
// Koniec, Tydzień 6
// Wzorzec State 1

// Koniec, Tydzień 6
// Wzorzec Strategy 1

// Koniec, Tydzień 8
// Zasada odwracania zależności (DIP) - wersja z interfejsem 1
