package pl.lab.factory.orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lab.factory.pcbboards.StandardBoard;

import static org.junit.jupiter.api.Assertions.*;

// Tydzień 13 - Stosowanie testów
// Testy jednostkowe (klasa 3) - ProductionQueue
class ProductionQueueTest {

    private static final String DUMMY_ORDER_ID_1 = "Q-ORD-01";
    private static final String DUMMY_ORDER_ID_2 = "Q-ORD-02";
    private static final int DEFAULT_QUANTITY = 10;
    private static final int EMPTY_SIZE = 0;
    private static final int SINGLE_ITEM_SIZE = 1;

    private ProductionQueue productionQueue;

    @BeforeEach
    void setUp() {
        productionQueue = ProductionQueue.getInstance();
        productionQueue.clear();
    }

    @Test
    // Sprawdza, czy ProductionQueue działa jako singleton i zawsze zwraca tę samą instancję.
    void getInstanceReturnsSameSingletonInstance() {
        ProductionQueue secondInstance = ProductionQueue.getInstance();

        assertSame(productionQueue, secondInstance);
    }

    @Test
    // Weryfikuje, czy dodanie zamówienia zwiększa rozmiar kolejki oraz czy nextOrder zwraca dodany element.
    void addOrderIncreasesQueueSizeAndNextOrderRetrievesIt() {
        ProductionOrder order = createDummyOrder(DUMMY_ORDER_ID_1);

        productionQueue.addOrder(order);

        assertEquals(SINGLE_ITEM_SIZE, productionQueue.size());
        assertEquals(order, productionQueue.nextOrder());
    }

    @Test
    // Sprawdza, czy metoda clear poprawnie usuwa wszystkie elementy z kolejki.
    void clearEmptiesAllOrdersFromQueue() {
        productionQueue.addOrder(createDummyOrder(DUMMY_ORDER_ID_1));
        productionQueue.addOrder(createDummyOrder(DUMMY_ORDER_ID_2));

        productionQueue.clear();

        assertEquals(EMPTY_SIZE, productionQueue.size());
        assertTrue(productionQueue.isEmpty());
    }

    @Test
    // Weryfikuje, czy usunięcie konkretnego zamówienia działa poprawnie i nie wpływa na pozostałe elementy.
    void removeOrderDeletesSpecificOrderFromQueue() {
        ProductionOrder orderToKeep = createDummyOrder(DUMMY_ORDER_ID_1);
        ProductionOrder orderToRemove = createDummyOrder(DUMMY_ORDER_ID_2);
        productionQueue.addOrder(orderToKeep);
        productionQueue.addOrder(orderToRemove);

        productionQueue.removeOrder(orderToRemove);

        assertEquals(SINGLE_ITEM_SIZE, productionQueue.size());
        assertEquals(orderToKeep, productionQueue.nextOrder());
    }

    @Test
    // Sprawdza walidację wejścia — dodanie null powinno skutkować rzuceniem wyjątku.
    void addOrderThrowsExceptionWhenOrderIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            productionQueue.addOrder(null);
        });
    }

    private ProductionOrder createDummyOrder(String orderId) {
        return new BaseBoardProductionOrder.Builder(orderId, new StandardBoard())
                .setQuantity(DEFAULT_QUANTITY)
                .build();
    }
}
// Koniec, Tydzień 13
// Testy jednostkowe (klasa 3)