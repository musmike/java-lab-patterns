package pl.lab.factory.orders.invoice.dispatch;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.invoice.OrderInvoice;
import pl.lab.factory.orders.pricing.CurrencyFormatter;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Template Method 1 (Concrete Class)
// Implementacja dla klientów hurtowych/B2B: generuje JSON i symuluje wysyłkę do zewnętrznego systemu poprzez API
public class B2bApiInvoiceDispatcher extends InvoiceDispatcher {

    @Override
    protected String renderDocument(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("DISPATCH-API", "Serializing invoice to JSON format...");
        return "{ \"orderId\": \"" + invoice.getOrder().getId()
                + "\", \"amount\": " + CurrencyFormatter.format(invoice.getAmount()) + ", \"currency\": \"USD\" }";
    }

    @Override
    protected void deliverToClient(String document, OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("DISPATCH-API",
                "POST /api/v1/invoices/receive Payload: " + document);
        SystemLogger.INSTANCE.logInfo("DISPATCH-API", "Response: 201 Created");
    }
}
// Koniec, Tydzień 6
// Wzorzec Template Method 1