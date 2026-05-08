package pl.lab.factory.orders.visitor;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 1 (Concrete Visitor)
// Oblicza podatek celny dla wysyłek do USA na podstawie typu produktu.
public class UsaCustomsVisitor extends TaxVisitor {

    private static final double STANDARD_TARIFF_RATE = 0.05;
    private static final double ELECTRONIC_TARIFF_RATE = 0.10;
    private static final double RESTRICTED_TECH_TARIFF_RATE = 0.25;

    @Override
    public void visit(BaseBoardProductionOrder order) {
        processCustomsTax(order, STANDARD_TARIFF_RATE);
    }

    @Override
    public void visit(BaseElectronicBoardProductionOrder order) {
        double taxRate = determineElectronicTaxRate(order);
        processCustomsTax(order, taxRate);
    }

    private double determineElectronicTaxRate(BaseElectronicBoardProductionOrder order) {
        if (order.getBaseElectronicBoard().getBoardType() == ElectronicType.HIGHLY_DEPENDANT) {
            SystemLogger.INSTANCE.logInfo("USA-CUSTOMS", "WARNING: Restricted tech. Applying "
                    + (RESTRICTED_TECH_TARIFF_RATE * 100) + "% tariff.");
            return RESTRICTED_TECH_TARIFF_RATE;
        }
        return ELECTRONIC_TARIFF_RATE;
    }

    private void processCustomsTax(ProductionOrder order, double taxRate) {
        double tax = calculateBaseTax(order, taxRate);
        totalTax += tax;
        logFinalTax("USA-CUSTOMS", order, (taxRate * 100) + "% tariff", tax);
    }

    public double getTotalCustomsTax() {
        return getTotalTax();
    }
}
// Koniec, Tydzień 6
// Wzorzec Visitor 1