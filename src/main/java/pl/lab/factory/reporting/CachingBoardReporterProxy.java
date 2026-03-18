package pl.lab.factory.reporting;

import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;
import java.util.HashMap;
import java.util.Map;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Proxy 1 (Caching Proxy)
// Proxy buforujące dla BoardReporter. Przechowuje ostatni zaraportowany status każdego zamówienia. Ponowne wywołanie
// report() dla tego samego zlecenia pomija LegacyAuditSystem jeśli status się nie zmienił od ostatniego raportu.
public class CachingBoardReporterProxy implements BoardReporter {

    private final BoardReporter boardReporter;
    private final Map<String, OrderStatus> cache = new HashMap<>();

    public CachingBoardReporterProxy(BoardReporter boardReporter) {
        this.boardReporter = boardReporter;
    }

    @Override
    public void report(ProductionOrder order) {
        String orderId = order.getId();
        OrderStatus currentStatus = order.getStatus();
        OrderStatus cachedStatus = cache.get(orderId);

        if (cachedStatus != null && cachedStatus == currentStatus) { return; }

        boardReporter.report(order);
        cache.put(orderId, currentStatus);
    }
}
// Koniec, Tydzień 4
// Wzorzec Proxy 1