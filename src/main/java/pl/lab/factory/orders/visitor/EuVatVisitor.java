package pl.lab.factory.orders.visitor;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 1 (Concrete Visitor)
// Oblicza podatek VAT dla Unii Europejskiej oraz dodatkowe opłaty dla małych zamówień.
public class EuVatVisitor extends TaxVisitor {

    private static final double VAT_RATE = 0.23;
    private static final int SMALL_ORDER_THRESHOLD = 10;
    private static final double SMALL_ORDER_FEE = 15.0;

    @Override
    public void visit(BaseBoardProductionOrder order) {
        processOrderVat(order);
    }

    @Override
    public void visit(BaseElectronicBoardProductionOrder order) {
        processOrderVat(order);
    }

    private void processOrderVat(ProductionOrder order) {
        double tax = calculateBaseTax(order, VAT_RATE);
        tax += calculateSmallOrderFee(order);

        totalTax += tax;
        logFinalTax("EU-VAT", order, (VAT_RATE * 100) + "% VAT", tax);
    }

    private double calculateSmallOrderFee(ProductionOrder order) {
        if (order.getQuantity() < SMALL_ORDER_THRESHOLD) {
            SystemLogger.INSTANCE.logInfo("EU-VAT", "Order " + order.getId()
                    + " (" + getTypeName(order) + ") -> Small order fee applied (+ $" + SMALL_ORDER_FEE + ").");
            return SMALL_ORDER_FEE;
        }
        return 0.0;
    }

    public double getTotalVat() {
        return getTotalTax();
    }
}
// Koniec, Tydzień 6
// Wzorzec Visitor 1
