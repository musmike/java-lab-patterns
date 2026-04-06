package pl.lab.factory.orders.invoice;

import pl.lab.factory.SystemLogger;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 1 (Concrete State)
// Stan domyślny. Można dodać rabat, można opłacić, nie można wydać paragonu.
public class OrderInvoiceUnpaidState implements OrderInvoiceState {

    @Override
    public void pay(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("INVOICE", "Payment received for order " + invoice.getOrder().getId() + ".");
        invoice.setState(new OrderInvoicePaidState()); // Zmiana stanu na opłaconą
    }

    @Override
    public void applyDiscount(OrderInvoice invoice, double percentage) {
        double currentAmount = invoice.getAmount();
        double deduction = currentAmount * (percentage / 100.0);
        invoice.setAmount(currentAmount - deduction);
        SystemLogger.INSTANCE.logInfo("INVOICE", "Discount of " + percentage + "% applied. New amount: " + invoice.getAmount());
    }

    @Override
    public void generateReceipt(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logError("INVOICE", "Cannot generate receipt. Invoice for " + invoice.getOrder().getId() + " is UNPAID.");
    }

    @Override
    public void cancel(OrderInvoice invoice) {
        SystemLogger.INSTANCE.logInfo("INVOICE", "Invoice cancelled successfully.");
        invoice.setState(new OrderInvoiceCancelledState());
    }
}
// Koniec, Tydzień 6
// Wzorzec State 1
