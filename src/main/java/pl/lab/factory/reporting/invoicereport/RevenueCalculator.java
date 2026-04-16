package pl.lab.factory.reporting.invoicereport;

import pl.lab.factory.orders.invoice.OrderInvoice;
import java.util.List;


// Tydzień 7 - Stosowanie zasad pojedynczej odpowiedzialności i otwarte-zamknięte
// Zasada pojedynczej odpowiedzalności (SRP) 1
// Klasa RevenueCalculator jest odpowiedzialna tylko za obliczanie przychodu na podstawie listy faktur.
public class RevenueCalculator {

    public double calculate(List<OrderInvoice> invoices) {
        return invoices.stream()
                .mapToDouble(OrderInvoice::getAmount)
                .sum();
    }
}
// Koniec, Tydzień 7
// Zasada pojedynczej odpowiedzalności (SRP) 1