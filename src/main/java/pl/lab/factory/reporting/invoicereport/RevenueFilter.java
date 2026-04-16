package pl.lab.factory.reporting.invoicereport;

import pl.lab.factory.orders.invoice.OrderInvoice;
import java.util.List;

public class RevenueFilter {
    private final RevenuePolicy policy;

    public RevenueFilter(RevenuePolicy policy) {
        this.policy = policy;
    }

    public List<OrderInvoice> filter(List<OrderInvoice> invoices) {
        return invoices.stream()
                .filter(policy::isRevenue)
                .toList();
    }
}
