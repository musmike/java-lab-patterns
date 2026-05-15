package pl.lab.factory.processes;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.functional.OrderCostCalculator;
import pl.lab.factory.functional.OrderDescriptionFormatter;
import pl.lab.factory.functional.OrderStatusChecker;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.processes.context.FactoryContext;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionalProgrammingProcess {

    private static final String PROCESS_CONTEXT = FunctionalProgrammingProcess.class.getSimpleName();
    private static final double BASE_BOARD_UNIT_PRICE = 12.50;
    private static final double ELECTRONIC_BOARD_UNIT_PRICE = 45.00;
    private static final int LARGE_ORDER_THRESHOLD = 5;
    private static final String LABEL_LARGE = "LARGE";
    private static final String LABEL_STANDARD = "STANDARD";

    private final FactoryContext context;

    public FunctionalProgrammingProcess(FactoryContext context) {
        this.context = context;
    }

    public void execute() {
        validateContext();
        demonstrateFunctionalInterfaces();
        demonstrateStreamProcessing();
        demonstratePredicateAndFunction();
    }

    private void validateContext() {
        if (context.getAllOrders() == null || context.getAllOrders().isEmpty()) {
            throw new IllegalStateException("Order list must not be empty before functional processing.");
        }
    }

    private void demonstrateFunctionalInterfaces() {
        SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "=== Demo: Functional interfaces ===");
        demonstrateOrderCostCalculator();
        demonstrateOrderDescriptionFormatter();
        demonstrateOrderStatusChecker();
    }

    // Tydzień 10 - Programowanie funkcyjne
    // Interfejs funkcyjny 1 - oblicza całkowity koszt zamówienia produkcyjnego
    private void demonstrateOrderCostCalculator() {
        OrderCostCalculator costCalculator =
            order -> order.getQuantity() * resolveUnitPrice(order);

        context.getAllOrders().forEach(order -> {
            double cost = costCalculator.calculate(order);
            SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT,
                "Order cost " + order.getId() + ": $" + formatAmount(cost));
        });
    }
    // Koniec, Tydzień 10
    // Interfejs funkcyjny 1



    // Tydzień 10 - Programowanie funkcyjne
    // Interfejs funkcyjny 2 - formatuje czytelny opis tekstowy zamówienia produkcyjnego
    private void demonstrateOrderDescriptionFormatter() {
        OrderDescriptionFormatter formatter = order ->
            String.format("[%s] %s | qty=%d | status=%s",
                    order.getClass().getSimpleName(),
                    order.getId(),
                    order.getQuantity(),
                    order.getStatus());

        context.getAllOrders().stream()
            .map(formatter::format)
            .forEach(description ->
                    SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, description));
    }
    // Koniec, Tydzień 10
    // Interfejs funkcyjny 2



    // Tydzień 10 - Programowanie funkcyjne
    // Interfejs funkcyjny 3 - sprawdza, czy zamówienie produkcyjne spełnia podany warunek statusu
    private void demonstrateOrderStatusChecker() {
        OrderStatusChecker completionChecker =
            order -> order.getStatus() == OrderStatus.COMPLETED;

        long completedCount = context.getAllOrders().stream()
            .filter(completionChecker::check)
            .count();

        SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "Completed orders: " + completedCount);
    }
    // Koniec, Tydzień 10
    // Interfejs funkcyjny 3


    private void demonstrateStreamProcessing() {
        SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "=== Demo: Streams (3 collections) ===");
        validateOrderListNotEmpty();
        processOrderCollection();
        processOrderIdentifierCollection();
        processOrderCostCollection();
    }

    private void validateOrderListNotEmpty() {
        if (context.getAllOrders().isEmpty()) {
            throw new IllegalStateException("Cannot process empty order list in stream processing.");
        }
    }

    // Tydzień 10 - Programowanie funkcyjne
    // Kolekcja 1 - wyświetla zamówienia o statusie COMPLETED, posortowane po ID
    // i zlicza ich ilość
    private void processOrderCollection() {
        SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "-- Collection #1: List<ProductionOrder> --");

        List<ProductionOrder> completedOrders = context.getAllOrders().stream()
            .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
            .sorted(Comparator.comparing(ProductionOrder::getId))
            .collect(Collectors.toList());

        completedOrders.forEach(order ->
            SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "Completed: " + order.getId()));

        SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "Total completed: " + completedOrders.size());
    }
    // Koniec, Tydzień 10
    // Kolekcja 1


    // Tydzień 10 - Programowanie funkcyjne
    // Kolekcja 2 - wyświetla unikalne identyfikatory zamówień, posortowane alfabetycznie i połączone w jeden string
    private void processOrderIdentifierCollection() {
        SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "-- Collection #2: List<String> (identifiers) --");

        List<String> sortedIdentifiers = context.getAllOrders().stream()
            .map(ProductionOrder::getId)
            .distinct()
            .sorted()
            .collect(Collectors.toList());

        String joinedIdentifiers = String.join(", ", sortedIdentifiers);
        SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "All IDs: " + joinedIdentifiers);
    }
    // Koniec, Tydzień 10
    // Kolekcja 2


    // Tydzień 10 - Programowanie funkcyjne
    // Kolekcja 3 - oblicza koszty zamówień, sumuje je i znajduje najdroższe zamówienie
    private void processOrderCostCollection() {
        SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "-- Collection #3: List<Double> (costs) --");

        List<Double> orderCosts = context.getAllOrders().stream()
            .map(order -> order.getQuantity() * resolveUnitPrice(order))
            .collect(Collectors.toList());

        double totalCost = orderCosts.stream().mapToDouble(Double::doubleValue).sum();
        OptionalDouble maxCost = orderCosts.stream().mapToDouble(Double::doubleValue).max();

        SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "Total cost: $" + formatAmount(totalCost));
        maxCost.ifPresent(max ->
            SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "Most expensive order: $" + formatAmount(max)));
    }
    // Koniec, Tydzień 10
    // Kolekcja 3


    private void demonstratePredicateAndFunction() {
        SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "=== Demo: Predicate and Function ===");
        demonstratePredicate();
        demonstrateFunction();
    }

    // Tydzień 10 - Programowanie funkcyjne
    // Predicate - złożony predykat do filtrowania zamówień
    private void demonstratePredicate() {
        Predicate<ProductionOrder> isLargeOrder =
            order -> order.getQuantity() > LARGE_ORDER_THRESHOLD;
        Predicate<ProductionOrder> isCompleted =
            order -> order.getStatus() == OrderStatus.COMPLETED;

        // AND
        List<ProductionOrder> largeCompletedOrders = context.getAllOrders().stream()
            .filter(isLargeOrder.and(isCompleted))
            .collect(Collectors.toList());

        SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT,
            "Large and completed orders: " + largeCompletedOrders.size());

        // NOT
        long pendingSmallCount = context.getAllOrders().stream()
            .filter(isLargeOrder.negate().and(isCompleted.negate()))
            .count();
        SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT, "Small and not completed: " + pendingSmallCount);
    }
    // Koniec, Tydzień 10
    // Predicate


    // Tydzień 10 - Programowanie funkcyjne
    // Function - złożona funkcja do mapowania zamówienia na etykietę rozmiaru
    private void demonstrateFunction() {
        Function<ProductionOrder, Integer> toQuantity = order -> {
            if (order == null) throw new IllegalArgumentException("Order cannot be null.");
            return order.getQuantity();
        };

        Function<Integer, String> toSizeLabel =
            quantity -> quantity > LARGE_ORDER_THRESHOLD ? LABEL_LARGE : LABEL_STANDARD;

        Function<ProductionOrder, String> toOrderSizeLabel = toQuantity.andThen(toSizeLabel);

        context.getAllOrders().forEach(order ->
            SystemLogger.INSTANCE.logInfo(PROCESS_CONTEXT,
                order.getId() + " -> " + toOrderSizeLabel.apply(order)));
    }
    // Koniec, Tydzień 10
    // Function

    private double resolveUnitPrice(ProductionOrder order) {
        return order instanceof BaseBoardProductionOrder
            ? BASE_BOARD_UNIT_PRICE
            : ELECTRONIC_BOARD_UNIT_PRICE;
    }

    private String formatAmount(double amount) {
        return String.format("%.2f", amount);
    }
}
