package pl.lab.factory.orders.loyalty;

import pl.lab.factory.orders.ProductionOrder;

public class QualityBonusStrategy implements LoyaltyBonusStrategy {
    @Override
    public int calculateBonus(ProductionOrder order) {
        return order.getSpecification().getQualityStandard().contains("3") ? 50 : 0;
    }
}
