package pl.lab.factory.orders.loyalty;

import pl.lab.factory.orders.ProductionOrder;

public class QualityBonusStrategy implements LoyaltyBonusStrategy {
    private static final String IPC_CLASS_3_STANDARD_SUFFIX = "3";
    private static final int HIGH_QUALITY_BONUS_POINTS = 50;

    @Override
    public int calculateBonus(ProductionOrder order) {
        return order.getSpecification().getQualityStandard().contains(IPC_CLASS_3_STANDARD_SUFFIX)
                ? HIGH_QUALITY_BONUS_POINTS : 0;
    }
}
