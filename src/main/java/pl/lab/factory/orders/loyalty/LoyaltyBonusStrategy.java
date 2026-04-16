package pl.lab.factory.orders.loyalty;

import pl.lab.factory.orders.ProductionOrder;

public interface LoyaltyBonusStrategy {
    int calculateBonus(ProductionOrder order);
}