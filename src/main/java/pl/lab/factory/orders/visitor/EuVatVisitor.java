package pl.lab.factory.orders.visitor;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 1 (Concrete Visitor)
// Oblicza podatek VAT dla Unii Europejskiej oraz dodatkowe opłaty dla małych zamówień.
public class EuVatVisitor implements OrderVisitor {

    private double totalVat = 0.0;

    @Override
    public void visit(BaseBoardProductionOrder order) {
        calculateVat(order.getId(), "BaseBoard", order.getQuantity(), 50.0);
    }

    @Override
    public void visit(BaseElectronicBoardProductionOrder order) {
        calculateVat(order.getId(), "ElectronicBoard", order.getQuantity(), 150.0);
    }

    private void calculateVat(String orderId, String type, int quantity, double unitPrice) {
        double estimatedValue = quantity * unitPrice;
        double tax = estimatedValue * 0.23;

        if (quantity < 10) {
            tax += 15.0;
            SystemLogger.INSTANCE.logInfo("EU-VAT", "Order " + orderId
                    + " (" + type + ") -> Small order fee applied (+ $15.00).");
        }

        totalVat += tax;
        SystemLogger.INSTANCE.logInfo("EU-VAT", "Order " + orderId
                + " (" + type + ") -> 23% VAT. Tax: $" + String.format("%.2f", tax));
    }

    public double getTotalVat() {
        return totalVat;
    }
}
// Koniec, Tydzień 6
// Wzorzec Visitor 1