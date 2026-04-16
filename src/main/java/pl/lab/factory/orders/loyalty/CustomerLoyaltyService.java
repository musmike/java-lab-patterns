package pl.lab.factory.orders.loyalty;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;
import java.util.HashMap;
import java.util.Map;

// Tydzień 7 - Stosowanie zasad pojedynczej odpowiedzialności i otwarte-zamknięte
// Zasada otwarte - zamknięte (OCP) 1
// Klasa CustomerLoyaltyService jest otwarta na rozszerzenia (możemy dodawać nowe strategie i dane), 
// ale zamknięta na modyfikacje (nie musimy zmieniać istniejącego kodu, aby dodać nowe funkcjonalności).
public class CustomerLoyaltyService {

    private final Map<String, Integer> tierBasePoints = new HashMap<>();

    public CustomerLoyaltyService() {
        tierBasePoints.put("BRONZE", 10);
        tierBasePoints.put("SILVER", 50);
        tierBasePoints.put("GOLD", 150);
    }

    public void registerTier(String tierName, int basePoints) {
        tierBasePoints.put(tierName, basePoints);
    }

    // --- PODEJŚCIE 1: JAWNE DOMKNIĘCIE PRZEZ ABSTRAKCJĘ ---
    public int calculateByStrategy(ProductionOrder order, LoyaltyBonusStrategy strategy) {
        int points = strategy.calculateBonus(order);

        SystemLogger.INSTANCE.logInfo("LOYALTY-SERVICE",
                "Points calculated via " + strategy.getClass().getSimpleName() + ": " + points);
        return points;
    }

    // --- PODEJŚCIE 2: JAWNE DOMKNIĘCIE PRZEZ STEROWANIE DANYMI ---
    public int calculateByTier(ProductionOrder order, String tierName) {
        Integer base = tierBasePoints.get(tierName);

        if (base == null) {
            SystemLogger.INSTANCE.logError("LOYALTY-SERVICE", "Unknown tier: " + tierName);
            return 0;
        }

        int total = base + (order.getQuantity() > 100 ? 20 : 0);

        SystemLogger.INSTANCE.logInfo("LOYALTY-SERVICE",
                "Data-driven points for tier " + tierName + ": " + total);
        return total;
    }
}
// Koniec, Tydzień 7
// Zasada otwarte - zamknięte (OCP) 1