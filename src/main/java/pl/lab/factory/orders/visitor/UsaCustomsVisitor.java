package pl.lab.factory.orders.visitor;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 1 (Concrete Visitor)
// Oblicza podatek celny dla wysyłek do USA na podstawie typu produktu.
public class UsaCustomsVisitor implements OrderVisitor {

    private double totalCustomsTax = 0.0;

    @Override
    public void visit(BaseBoardProductionOrder order) {
        double estimatedValue = order.getQuantity() * 50.0;
        double tax = estimatedValue * 0.05;
        totalCustomsTax += tax;

        SystemLogger.INSTANCE.logInfo("USA-CUSTOMS", "Order " + order.getId()
                + " (BaseBoard) -> 5% tariff. Tax: $" + String.format("%.2f", tax));
    }

    @Override
    public void visit(BaseElectronicBoardProductionOrder order) {
        double estimatedValue = order.getQuantity() * 150.0;
        double taxRate = 0.10;

        if (order.getBaseElectronicBoard().getBoardType() == ElectronicType.HIGHLY_DEPENDANT) {
            taxRate = 0.25;
            SystemLogger.INSTANCE.logInfo("USA-CUSTOMS", "WARNING: Order " + order.getId()
                    + " contains restricted tech. Applying 25% tariff.");
        }

        double tax = estimatedValue * taxRate;
        totalCustomsTax += tax;

        SystemLogger.INSTANCE.logInfo("USA-CUSTOMS", "Order " + order.getId()
                + " (ElectronicBoard) -> " + (taxRate*100) + "% tariff. Tax: $" + String.format("%.2f", tax));
    }

    public double getTotalCustomsTax() {
        return totalCustomsTax;
    }
}
// Koniec, Tydzień 6
// Wzorzec Visitor 1