package pl.lab.factory.orders.visitor;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;

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
        double estimatedValue = order.getQuantity() * BASE_BOARD_UNIT_PRICE;
        double tax = estimatedValue * STANDARD_TARIFF_RATE;
        totalCustomsTax += tax;

        SystemLogger.INSTANCE.logInfo("USA-CUSTOMS", "Order " + order.getId()
                + " (BaseBoard) -> " + (STANDARD_TARIFF_RATE * 100) + "% tariff. Tax: $" + String.format("%.2f", tax));
    }

    @Override
    public void visit(BaseElectronicBoardProductionOrder order) {
        double estimatedValue = order.getQuantity() * ELECTRONIC_BOARD_UNIT_PRICE;
        double taxRate = ELECTRONIC_TARIFF_RATE;

        if (order.getBaseElectronicBoard().getBoardType() == ElectronicType.HIGHLY_DEPENDANT) {
            taxRate = RESTRICTED_TECH_TARIFF_RATE;
            SystemLogger.INSTANCE.logInfo("USA-CUSTOMS", "WARNING: Order " + order.getId()
                    + " contains restricted tech. Applying " + (RESTRICTED_TECH_TARIFF_RATE * 100) + "% tariff.");
        }

        double tax = estimatedValue * taxRate;
        totalCustomsTax += tax;

        SystemLogger.INSTANCE.logInfo("USA-CUSTOMS", "Order " + order.getId()
                + " (ElectronicBoard) -> " + (taxRate * 100) + "% tariff. Tax: $" + String.format("%.2f", tax));
    }

    public double getTotalCustomsTax() {
        return totalCustomsTax;
    }
}
// Koniec, Tydzień 6
// Wzorzec Visitor 1