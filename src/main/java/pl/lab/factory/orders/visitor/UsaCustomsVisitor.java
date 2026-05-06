package pl.lab.factory.orders.visitor;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.pricing.CurrencyFormatter;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 1 (Concrete Visitor)
// Oblicza podatek celny dla wysyłek do USA na podstawie typu produktu.
public class UsaCustomsVisitor implements OrderVisitor {

    private static final double BASE_BOARD_UNIT_PRICE = 50.0;
    private static final double ELECTRONIC_BOARD_UNIT_PRICE = 150.0;
    private static final double STANDARD_TARIFF_RATE = 0.05;
    private static final double ELECTRONIC_TARIFF_RATE = 0.10;
    private static final double RESTRICTED_TECH_TARIFF_RATE = 0.25;

    private double totalCustomsTax = 0.0;

    @Override
    public void visit(BaseBoardProductionOrder order) {
        processCustomsTax(order, "BaseBoard", BASE_BOARD_UNIT_PRICE, STANDARD_TARIFF_RATE);
    }

    @Override
    public void visit(BaseElectronicBoardProductionOrder order) {
        double taxRate = determineElectronicTaxRate(order);
        processCustomsTax(order, "ElectronicBoard", ELECTRONIC_BOARD_UNIT_PRICE, taxRate);
    }

    private double determineElectronicTaxRate(BaseElectronicBoardProductionOrder order) {
        if (order.getBaseElectronicBoard().getBoardType() == ElectronicType.HIGHLY_DEPENDANT) {
            SystemLogger.INSTANCE.logInfo("USA-CUSTOMS", "WARNING: Order " + order.getId()
                    + " contains restricted tech. Applying " + (RESTRICTED_TECH_TARIFF_RATE * 100) + "% tariff.");
            return RESTRICTED_TECH_TARIFF_RATE;
        }
        return ELECTRONIC_TARIFF_RATE;
    }

    private void processCustomsTax(ProductionOrder order, String typeName, double unitPrice, double taxRate) {
        double tax = calculateTax(order.getQuantity(), unitPrice, taxRate);
        totalCustomsTax += tax;
        logFinalTax(order.getId(), typeName, taxRate, tax);
    }

    private double calculateTax(int quantity, double unitPrice, double taxRate) {
        double estimatedValue = quantity * unitPrice;
        return estimatedValue * taxRate;
    }

    private void logFinalTax(String orderId, String typeName, double taxRate, double tax) {
        SystemLogger.INSTANCE.logInfo("USA-CUSTOMS", "Order " + orderId
                + " (" + typeName + ") -> " + (taxRate * 100) + "% tariff. Tax: $" + CurrencyFormatter.format(tax));
    }

    public double getTotalCustomsTax() {
        return totalCustomsTax;
    }
}
// Koniec, Tydzień 6
// Wzorzec Visitor 1