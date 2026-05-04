package pl.lab.factory.orders.loyalty;

import pl.lab.factory.orders.ProductionOrder;

public class VolumeBonusStrategy implements LoyaltyBonusStrategy {
    private static final int POINTS_PER_UNIT = 45;

    @Override
    public int calculateBonus(ProductionOrder order) {
        return order.getQuantity() * POINTS_PER_UNIT;
    }
}
