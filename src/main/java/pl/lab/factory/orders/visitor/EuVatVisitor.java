package pl.lab.factory.orders.visitor;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 1 (Concrete Visitor)
// Oblicza podatek VAT dla Unii Europejskiej oraz dodatkowe opłaty dla małych zamówień.
public class EuVatVisitor implements OrderVisitor {

    private static final double BASE_BOARD_UNIT_PRICE = 50.0;
    private static final double ELECTRONIC_BOARD_UNIT_PRICE = 150.0;
    private static final double VAT_RATE = 0.23;
    private static final int SMALL_ORDER_THRESHOLD = 10;
    private static final double SMALL_ORDER_FEE = 15.0;

    private double totalVat = 0.0;

    @Override
    public void visit(BaseBoardProductionOrder order) {
        calculateVat(order.getId(), "BaseBoard", order.getQuantity(), BASE_BOARD_UNIT_PRICE);
    }

    @Override
    public void visit(BaseElectronicBoardProductionOrder order) {
        calculateVat(order.getId(), "ElectronicBoard", order.getQuantity(), ELECTRONIC_BOARD_UNIT_PRICE);
    }

    private void calculateVat(String orderId, String type, int quantity, double unitPrice) {
        double estimatedValue = quantity * unitPrice;
        double tax = estimatedValue * VAT_RATE;

        if (quantity < SMALL_ORDER_THRESHOLD) {
            tax += SMALL_ORDER_FEE;
            SystemLogger.INSTANCE.logInfo("EU-VAT", "Order " + orderId
                    + " (" + type + ") -> Small order fee applied (+ $" + SMALL_ORDER_FEE + ").");
        }

        totalVat += tax;
        SystemLogger.INSTANCE.logInfo("EU-VAT", "Order " + orderId
                + " (" + type + ") -> " + (VAT_RATE * 100) + "% VAT. Tax: $" + String.format("%.2f", tax));
    }

    public double getTotalVat() {
        return totalVat;
    }
}
// Koniec, Tydzień 6
// Wzorzec Visitor 1