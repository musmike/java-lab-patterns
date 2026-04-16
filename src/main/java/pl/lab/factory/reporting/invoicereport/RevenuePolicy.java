package pl.lab.factory.reporting.invoicereport;

import pl.lab.factory.orders.invoice.OrderInvoice;

public interface RevenuePolicy {
    boolean isRevenue(OrderInvoice invoice);
}