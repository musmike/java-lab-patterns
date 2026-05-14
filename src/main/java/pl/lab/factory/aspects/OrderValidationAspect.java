package pl.lab.factory.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 11 - Programowanie aspektowe
// Aspekt 2 - Walidacja zamówienia przed zapisaniem do magazynu.
// Przechwytuje wywołania storeOrder() i sprawdza poprawność zamówienia
// bez modyfikowania klas magazynu
@Aspect
public class OrderValidationAspect {

    private static final String ASPECT_CONTEXT = OrderValidationAspect.class.getSimpleName();
    private static final int MIN_ORDER_QUANTITY = 1;

    // Rada przed (Before)
    // Przechwytuje storeOrder() we wszystkich klasach implementujących WarehouseService
    @Before("execution(* pl.lab.factory.warehouse.WarehouseService+.storeOrder(..)) && args(order)")
    public void validateOrderBeforeStorage(JoinPoint joinPoint, ProductionOrder order) {
        assertOrderIsNotNull(order);
        assertOrderHasValidId(order);
        assertOrderHasPositiveQuantity(order);

        SystemLogger.INSTANCE.logInfo(ASPECT_CONTEXT,
            "Order " + order.getId() + " passed validation before storage.");
    }

    private void assertOrderIsNotNull(ProductionOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Order passed to warehouse cannot be null.");
        }
    }

    private void assertOrderHasValidId(ProductionOrder order) {
        if (order.getId() == null || order.getId().isBlank()) {
            throw new IllegalArgumentException(
                "Order must have a non-empty identifier before storage.");
        }
    }

    private void assertOrderHasPositiveQuantity(ProductionOrder order) {
        if (order.getQuantity() < MIN_ORDER_QUANTITY) {
            throw new IllegalArgumentException(
                "Order " + order.getId() + " quantity must be at least " + MIN_ORDER_QUANTITY + ".");
        }
    }
}
// Koniec, Tydzień 11
// Aspekt 2
