package pl.lab.factory.orders;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.SystemLogger;
import pl.lab.factory.manufacturing.ManufacturableProduct;
import pl.lab.factory.pcbboards.BaseBoard;

import java.util.Objects;

// Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
// Wzorzec Factory (Simple Factory)
//
//
public class ProductionOrderFactory {

    public static ProductionOrder createOrder(String id, ManufacturableProduct product, int quantity) {
        Objects.requireNonNull(product, "product cannot be null");

        SystemLogger.INSTANCE.logInfo("ZAMÓWIENIA", "Tworzenie zlecenia: " + id);

        if (product instanceof BaseElectronicBoard electronicBoard) {
            return new BaseElectronicBoardProductionOrder.Builder(id, electronicBoard)
                    .setQuantity(quantity)
                    .build();
        } else if (product instanceof BaseBoard baseBoard) {
            return new BaseBoardProductionOrder.Builder(id, baseBoard)
                    .setQuantity(quantity)
                    .build();
        }

        throw new IllegalArgumentException("Nieznany typ produktu: " + product.getClass().getSimpleName());
    }
}

// Koniec, Tydzień 2
// Wzorzec Factory (Simple Factory)