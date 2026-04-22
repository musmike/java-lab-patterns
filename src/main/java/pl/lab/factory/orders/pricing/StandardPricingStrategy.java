package pl.lab.factory.orders.pricing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 1 (Concrete Strategy)
// Podstawowy algorytm: cena jednostkowa (zależna od typu płytki) x ilość.

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada podstawienia Liskov (LSP) 1
// Konkretna klasa StandardPricingStrategy, która jest bazowa bezpośrednio
// dla strategii cenowych takich jak RushOrderPricingStrategy czy HolidayPricingStrategy

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada odwracania zależności (DIP) - wersja z interfejsem 1 (Klasa pochodna)
// StandardPricingStrategy jest klasą pochodną, która implementuje abstrakcyjną metodę z interfejsu PricingStrategy
public class StandardPricingStrategy implements PricingStrategy {

    private static final double BASE_BOARD_UNIT_PRICE = 50.0;
    private static final double ELECTRONIC_BOARD_UNIT_PRICE = 150.0;

    @Override
    public double calculatePrice(ProductionOrder order) {
        double unitPrice = (order instanceof BaseElectronicBoardProductionOrder)
                ? ELECTRONIC_BOARD_UNIT_PRICE
                : BASE_BOARD_UNIT_PRICE;

        double finalPrice = unitPrice * order.getQuantity();

        SystemLogger.INSTANCE.logInfo("PRICING", "Standard pricing for " + order.getId() + ": "
                + order.getQuantity() + " units * $" + unitPrice + " = $" + String.format("%.2f", finalPrice));

        return finalPrice;
    }
}
// Koniec, Tydzień 6
// Wzorzec Strategy 1

// Koniec, Tydzień 8
// Zasada podstawienia Liskov (LSP) 1

// Koniec, Tydzień 8
// Zasada odwracania zależności (DIP) - wersja z interfejsem 1