package pl.lab.factory.reporting.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lab.factory.orders.ProductionOrder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// Tydzień 13 - Stosowanie testów
// Testy jednostkowe (klasa 4) - QuantityExpression
class QuantityExpressionTest {

    private static final int LOW_QUANTITY = 5;
    private static final int EXACT_QUANTITY = 10;
    private static final int HIGH_QUANTITY = 15;
    private static final int THRESHOLD = 10;

    private static final int EXPECTED_SINGLE_MATCH = 1;
    private static final int EXPECTED_EMPTY_RESULT = 0;

    private ProductionOrder lowQuantityOrderMock;
    private ProductionOrder exactQuantityOrderMock;
    private ProductionOrder highQuantityOrderMock;

    @BeforeEach
    void setUp() {
        lowQuantityOrderMock = mockOrderWithQuantity(LOW_QUANTITY);
        exactQuantityOrderMock = mockOrderWithQuantity(EXACT_QUANTITY);
        highQuantityOrderMock = mockOrderWithQuantity(HIGH_QUANTITY);
    }

    @Test
    // Filtr zwraca tylko zamówienia, których ilość jest większa od podanego progu.
    void interpretReturnsOrdersWithQuantityGreaterThanThreshold() {
        QuantityExpression expression = new QuantityExpression(QuantityOperator.GREATER_THAN, THRESHOLD);
        ReportContext context = createPopulatedContext();

        List<ProductionOrder> filteredOrders = expression.interpret(context);

        assertEquals(EXPECTED_SINGLE_MATCH, filteredOrders.size());
        assertTrue(filteredOrders.contains(highQuantityOrderMock));
    }

    @Test
    // Filtr zwraca tylko zamówienia o ilości mniejszej niż próg.
    void interpretReturnsOrdersWithQuantityLessThanThreshold() {
        QuantityExpression expression = new QuantityExpression(QuantityOperator.LESS_THAN, THRESHOLD);
        ReportContext context = createPopulatedContext();

        List<ProductionOrder> filteredOrders = expression.interpret(context);

        assertEquals(EXPECTED_SINGLE_MATCH, filteredOrders.size());
        assertTrue(filteredOrders.contains(lowQuantityOrderMock));
    }

    @Test
    // Filtr zwraca zamówienia, których ilość jest dokładnie równa progowi.
    void interpretReturnsOrdersWithQuantityEqualToThreshold() {
        QuantityExpression expression = new QuantityExpression(QuantityOperator.EQUAL, THRESHOLD);
        ReportContext context = createPopulatedContext();

        List<ProductionOrder> filteredOrders = expression.interpret(context);

        assertEquals(EXPECTED_SINGLE_MATCH, filteredOrders.size());
        assertTrue(filteredOrders.contains(exactQuantityOrderMock));
    }

    @Test
    // Weryfikuje, że brak dopasowań w warunku filtrującym zwraca pustą listę.
    void interpretReturnsEmptyListWhenNoOrdersMatchCondition() {
        QuantityExpression expression = new QuantityExpression(QuantityOperator.EQUAL, HIGH_QUANTITY);
        ReportContext context = createContextWithOnly(lowQuantityOrderMock);

        List<ProductionOrder> filteredOrders = expression.interpret(context);

        assertEquals(EXPECTED_EMPTY_RESULT, filteredOrders.size());
    }

    @Test
    // Sprawdza, że pusty kontekst wejściowy skutkuje zwróceniem pustej listy wyników.
    void interpretReturnsEmptyListWhenContextIsEmpty() {
        QuantityExpression expression = new QuantityExpression(QuantityOperator.GREATER_THAN, LOW_QUANTITY);
        ReportContext emptyContext = new ReportContext(Collections.emptyList());

        List<ProductionOrder> filteredOrders = expression.interpret(emptyContext);

        assertEquals(EXPECTED_EMPTY_RESULT, filteredOrders.size());
    }

    private ProductionOrder mockOrderWithQuantity(int quantity) {
        ProductionOrder orderMock = mock(ProductionOrder.class);
        when(orderMock.getQuantity()).thenReturn(quantity);
        return orderMock;
    }

    private ReportContext createPopulatedContext() {
        List<ProductionOrder> allOrders = List.of(
                lowQuantityOrderMock,
                exactQuantityOrderMock,
                highQuantityOrderMock
        );
        return new ReportContext(allOrders);
    }

    private ReportContext createContextWithOnly(ProductionOrder order) {
        return new ReportContext(List.of(order));
    }
}
// Koniec, Tydzień 13
// Testy jednostkowe (klasa 4)