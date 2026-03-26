package pl.lab.factory.reporting.interpreter;

import pl.lab.factory.orders.ProductionOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Interpreter 1 (Kontekst)
// Przechowuje listę zamówień oraz aktywny predykat filtrowania
// Wyrażenia ustawiają filtr i wywołują search().
public class ReportContext {
    private final List<ProductionOrder> allOrders;

    private Predicate<ProductionOrder> filter = order -> true;

    public ReportContext(List<ProductionOrder> allOrders) {
        this.allOrders = new ArrayList<>(allOrders);
    }

    public void setFilter(Predicate<ProductionOrder> filter) {
        this.filter = filter;
    }

    public List<ProductionOrder> search() {
        List<ProductionOrder> result = allOrders.stream()
                .filter(filter)
                .toList();

        clear();
        return result;
    }

    private void clear() {
        this.filter = order -> true;
    }
}
// Koniec, Tydzień 5
// Wzorzec Interpreter 1