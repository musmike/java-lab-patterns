package pl.lab.factory.orders.invoice;

import pl.lab.factory.SystemLogger;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 1 (Concrete State)
// Faktura opłacona. Generowanie paragonu działa, modyfikacje i anulowanie są zablokowane.
public class OrderInvoicePaidState implements OrderInvoiceState {

    @Override
    public void pay(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logError("INVOICE", "Invoice is already PAID. Cannot pay again.");
    }

    @Override
    public void applyDiscount(OrderInvoice invoice, double percentage) {
        SystemLogger.INSTANCE.logError("INVOICE", "Cannot apply discount. Invoice is already PAID.");
    }

    @Override
    public void generateReceipt(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("RECEIPT", "====================================");
        SystemLogger.INSTANCE.logInfo("RECEIPT", "  OFFICIAL RECEIPT FOR: " + invoice.getOrder().getId());
        SystemLogger.INSTANCE.logInfo("RECEIPT", "  Items: " + invoice.getOrder().getQuantity());
        SystemLogger.INSTANCE.logInfo("RECEIPT", "  Total Paid: $" + String.format("%.2f", invoice.getAmount()));
        SystemLogger.INSTANCE.logInfo("RECEIPT", "====================================");
    }

    @Override
    public void cancel(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logError("INVOICE", "Cannot cancel a PAID invoice. Please initiate a formal refund process.");
    }
}
// Koniec, Tydzień 6
// Wzorzec State 1