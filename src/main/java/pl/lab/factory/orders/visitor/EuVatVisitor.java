package pl.lab.factory.orders.visitor;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.pricing.CurrencyFormatter;

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
        processOrderVat(order, "BaseBoard", BASE_BOARD_UNIT_PRICE);
    }

    @Override
    public void visit(BaseElectronicBoardProductionOrder order) {
        processOrderVat(order, "ElectronicBoard", ELECTRONIC_BOARD_UNIT_PRICE);
    }

    private void processOrderVat(ProductionOrder order, String typeName, double unitPrice) {
        double tax = calculateBaseTax(order.getQuantity(), unitPrice);
        tax += calculateSmallOrderFee(order.getQuantity(), order.getId(), typeName);

        totalVat += tax;
        logFinalTax(order.getId(), typeName, tax);
    }

    private double calculateBaseTax(int quantity, double unitPrice) {
        double estimatedValue = quantity * unitPrice;
        return estimatedValue * VAT_RATE;
    }

    private double calculateSmallOrderFee(int quantity, String orderId, String typeName) {
        if (quantity < SMALL_ORDER_THRESHOLD) {
            SystemLogger.INSTANCE.logInfo("EU-VAT", "Order " + orderId
                    + " (" + typeName + ") -> Small order fee applied (+ $" + SMALL_ORDER_FEE + ").");
            return SMALL_ORDER_FEE;
        }
        return 0.0;
    }

    private void logFinalTax(String orderId, String typeName, double tax) {
        SystemLogger.INSTANCE.logInfo("EU-VAT", "Order " + orderId
                + " (" + typeName + ") -> " + (VAT_RATE * 100) + "% VAT. Tax: $" + CurrencyFormatter.format((tax)));
    }

    public double getTotalVat() {
        return totalVat;
    }
}
// Koniec, Tydzień 6
// Wzorzec Visitor 1