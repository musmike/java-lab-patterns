package pl.lab.factory.orders.loyalty;

import pl.lab.factory.orders.ProductionOrder;

public class QualityBonusStrategy implements LoyaltyBonusStrategy {
    private static final String HIGH_QUALITY_CLASS_MARKER = "3";
    private static final int HIGH_QUALITY_BONUS_POINTS = 50;

    @Override
    public int calculateBonus(ProductionOrder order) {
        return order.getSpecification().getQualityStandard().contains(HIGH_QUALITY_CLASS_MARKER)
                ? HIGH_QUALITY_BONUS_POINTS : 0;
    }
}
