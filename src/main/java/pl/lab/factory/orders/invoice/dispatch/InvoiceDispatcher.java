package pl.lab.factory.orders.invoice.dispatch;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.invoice.OrderInvoice;
import pl.lab.factory.orders.invoice.OrderInvoiceCancelledState;
import pl.lab.factory.orders.invoice.OrderInvoiceDraftState;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Template Method 1 (Abstract Class)
// Definiuje szkielet algorytmu przetwarzania i wysyłki faktury.
public abstract class InvoiceDispatcher {

    public final void dispatch(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("DISPATCH", "--- Starting invoice dispatch process for "
                + invoice.getOrder().getId() + " ---");

        if (!verifyInvoiceState(invoice)) {
            SystemLogger.INSTANCE.logError("DISPATCH", "Dispatch aborted. Invoice cannot be a DRAFT or CANCELLED.");
            return;
        }

        String renderedDocument = renderDocument(invoice);
        deliverToClient(renderedDocument, invoice);

        logDispatchActivity(invoice);

        SystemLogger.INSTANCE.logInfo("DISPATCH", "--- Dispatch process completed successfully ---");
    }

    // --- Metody wspólne ---

    private boolean verifyInvoiceState(OrderInvoice invoice) {
        return !(invoice.getState() instanceof OrderInvoiceDraftState)
                && !(invoice.getState() instanceof OrderInvoiceCancelledState);
    }

    private void logDispatchActivity(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("DISPATCH", "Activity logged: Invoice for amount $"
                + String.format("%.2f", invoice.getAmount()) + " has been processed and dispatched.");
    }

    // --- Metody abstrakcyjne ---

    protected abstract String renderDocument(OrderInvoice invoice);

    protected abstract void deliverToClient(String document, OrderInvoice invoice);
}
// Koniec, Tydzień 6
// Wzorzec Template Method 1