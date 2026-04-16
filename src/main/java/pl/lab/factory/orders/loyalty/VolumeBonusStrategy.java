package pl.lab.factory.orders.loyalty;

import pl.lab.factory.orders.ProductionOrder;

public class VolumeBonusStrategy implements LoyaltyBonusStrategy {
    @Override
    public int calculateBonus(ProductionOrder order) {
        return order.getQuantity() * 45;
    }
}
