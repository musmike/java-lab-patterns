package pl.lab.factory.orders.invoice;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.pricing.CurrencyFormatter;
import pl.lab.factory.orders.pricing.PricingStrategy;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 1 (Concrete State)
// Faktura opłacona. Generowanie paragonu działa, modyfikacje i anulowanie są zablokowane.
public class OrderInvoicePaidState implements OrderInvoiceState {

    private static final String RECEIPT_SEPARATOR = "====================================";

    @Override
    public void pay(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logError("INVOICE", MSG_ERR_ALREADY_PAID);
    }

    @Override
    public void applyDiscount(OrderInvoice invoice, double percentage) {
        SystemLogger.INSTANCE.logError("INVOICE", MSG_ERR_ALREADY_PAID);
    }

    @Override
    public void generateReceipt(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("RECEIPT", RECEIPT_SEPARATOR);
        SystemLogger.INSTANCE.logInfo("RECEIPT", "  OFFICIAL RECEIPT FOR: " + invoice.getOrder().getId());
        SystemLogger.INSTANCE.logInfo("RECEIPT", "  Items: " + invoice.getOrder().getQuantity());
        SystemLogger.INSTANCE.logInfo("RECEIPT", "  Total Paid: $" + CurrencyFormatter.format(invoice.getAmount()));
        SystemLogger.INSTANCE.logInfo("RECEIPT", RECEIPT_SEPARATOR);
    }

    @Override
    public void cancel(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logError("INVOICE", MSG_ERR_CANCELLED);
    }

    @Override
    public void recalculatePrice(OrderInvoice invoice, PricingStrategy strategy) {
        SystemLogger.INSTANCE.logError("INVOICE", MSG_ERR_ALREADY_PAID);
    }

    @Override
    public void confirm(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logError("INVOICE", MSG_ERR_ALREADY_PAID);
    }

    @Override
    public boolean isRevenueRecognized() {
        return true;
    }
}
// Koniec, Tydzień 6
// Wzorzec State 1