package pl.lab.factory.reporting;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 1 (Interfejs docelowy — Target)
// Kontrakt, którego oczekuje system do raportowania ukończonych zleceń.
// Kod klienta (Main) zna tylko ten interfejs — nie wie nic o LegacyAuditSystem.
public interface BoardReporter {
    void report(ProductionOrder order);
}
// Koniec, Tydzień 3
// Wzorzec Adapter 1