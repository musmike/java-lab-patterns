package pl.lab.factory.orders;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.SystemLogger;
import pl.lab.factory.manufacturing.ManufacturableProduct;
import pl.lab.factory.pcbboards.BaseBoard;
import java.util.Objects;

// Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
// Wzorzec Factory 2 (Simple Factory)
// Simple Factory – ukrywa logikę tworzenia różnych typów zleceń produkcyjnych.
// Metoda createOrder() sprawdza typ produktu i tworzy odpowiedni obiekt ProductionOrder.
public class ProductionOrderFactory {

    public static ProductionOrder createOrder(ManufacturableProduct product, OrderContext context) {
        Objects.requireNonNull(product, "product cannot be null");
        SystemLogger.INSTANCE.logInfo("ORDERS", "Creating order: " + context.id());

        if (product instanceof BaseElectronicBoard electronicBoard) {
            return new BaseElectronicBoardProductionOrder.Builder(context.id(), electronicBoard)
                    .setQuantity(context.quantity())
                    .setSpecification(context.orderSpecification())
                    .build();
        } else if (product instanceof BaseBoard baseBoard) {
            return new BaseBoardProductionOrder.Builder(context.id(), baseBoard)
                    .setQuantity(context.quantity())
                    .setSpecification(context.orderSpecification())
                    .build();
        }

        throw new IllegalArgumentException("Unknown product type: " + product.getClass().getSimpleName());
    }
}
// Koniec, Tydzień 2
// Wzorzec Factory 2