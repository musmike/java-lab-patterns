package pl.lab.factory.orders.invoice.dispatch;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.invoice.OrderInvoice;
import pl.lab.factory.orders.pricing.CurrencyFormatter;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Template Method 1 (Concrete Class)
// Implementacja dla klientów detalicznych: generuje PDF i wysyła e-mailem.
public class EmailInvoiceDispatcher extends InvoiceDispatcher {

    @Override
    protected String renderDocument(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("DISPATCH-EMAIL", "Rendering PDF document...");
        return "Attachment: Invoice_PDF_" + invoice.getOrder().getId()
                + ".pdf (Total: $" + CurrencyFormatter.format(invoice.getAmount()) + ")";
    }

    @Override
    protected void deliverToClient(String document, OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("DISPATCH-EMAIL",
                "Sending Email to customer with payload: [" + document + "]");
    }
}
// Koniec, Tydzień 6
// Wzorzec Template Method 1