package pl.lab.factory.reporting.invoicereport;

import pl.lab.factory.orders.invoice.OrderInvoice;

import java.util.List;

public class RevenueService {
    private final RevenueFilter revenueFilter;
    private final RevenueCalculator revenueCalculator;

    public RevenueService(RevenueFilter revenueFilter, RevenueCalculator revenueCalculator) {
        this.revenueFilter = revenueFilter;
        this.revenueCalculator = revenueCalculator;
    }

    public double calculateRevenue(List<OrderInvoice> invoices) {
        List<OrderInvoice> filteredInvoices = revenueFilter.filter(invoices);
        return revenueCalculator.calculate(filteredInvoices);
    }
}