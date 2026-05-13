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

    private static final TierConfig BRONZE_TIER_CONFIG = new TierConfig(10, 100, 20);
    private static final TierConfig SILVER_TIER_CONFIG = new TierConfig(50, 100, 20);
    private static final TierConfig GOLD_TIER_CONFIG = new TierConfig(150, 50, 50);

    public static final String TIER_BRONZE = "BRONZE";
    public static final String TIER_SILVER = "SILVER";
    public static final String TIER_GOLD = "GOLD";

    private final Map<String, TierConfig> tiers = new HashMap<>();

    public CustomerLoyaltyService() {
        tiers.put(TIER_BRONZE, BRONZE_TIER_CONFIG);
        tiers.put(TIER_SILVER, SILVER_TIER_CONFIG);
        tiers.put(TIER_GOLD, GOLD_TIER_CONFIG);
    }

    public void registerTier(String tierName, TierConfig config) {
        tiers.put(tierName, config);
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
        TierConfig config = tiers.get(tierName);

        if (config == null) {
            throw new IllegalArgumentException("Unknown tier: " + tierName);
        }

        int total = config.basePoints();

        if (order.getQuantity() > config.bulkThreshold()) {
            total += config.bulkBonus();
        }

        SystemLogger.INSTANCE.logInfo("LOYALTY-SERVICE",
                "Data-driven points for tier " + tierName + ": " + total);
        return total;
    }
}
// Koniec, Tydzień 7
// Zasada otwarte - zamknięte (OCP) 1