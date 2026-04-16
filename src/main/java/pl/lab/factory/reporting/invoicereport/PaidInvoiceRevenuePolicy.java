package pl.lab.factory.reporting.invoicereport;

import pl.lab.factory.orders.invoice.OrderInvoice;

public class PaidInvoiceRevenuePolicy implements RevenuePolicy {
    @Override
    public boolean isRevenue(OrderInvoice invoice) {
        return invoice.getState().isRevenueRecognized();
    }}
