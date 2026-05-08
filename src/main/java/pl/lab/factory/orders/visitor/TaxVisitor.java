package pl.lab.factory.orders.visitor;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.pricing.CurrencyFormatter;
import pl.lab.factory.orders.pricing.StandardPricingStrategy;

public abstract class TaxVisitor implements OrderVisitor {

    protected double totalTax = 0.0;

    public double getTotalTax() {
        return totalTax;
    }

    protected String getTypeName(ProductionOrder order) {
        return (order instanceof BaseElectronicBoardProductionOrder) ? "ElectronicBoard" : "BaseBoard";
    }

    protected double getUnitPrice(ProductionOrder order) {
        return (order instanceof BaseElectronicBoardProductionOrder)
                ? StandardPricingStrategy.ELECTRONIC_BOARD_UNIT_PRICE
                : StandardPricingStrategy.BASE_BOARD_UNIT_PRICE;
    }

    protected double calculateBaseTax(ProductionOrder order, double taxRate) {
        double estimatedValue = order.getQuantity() * getUnitPrice(order);
        return estimatedValue * taxRate;
    }

    protected void logFinalTax(String region, ProductionOrder order, TaxCalculationResult taxResult) {
        SystemLogger.INSTANCE.logInfo(region, "Order " + order.getId()
                + " (" + getTypeName(order) + ") -> " + taxResult.description() + ". Tax: $" + CurrencyFormatter.format(taxResult.amount()));
    }
}