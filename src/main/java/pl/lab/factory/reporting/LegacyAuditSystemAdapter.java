package pl.lab.factory.reporting;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 1 (Konkretny Adapter)
// Implementuje interfejs docelowy BoardReporter, opakowując LegacyAuditSystem. Tłumaczy obiekt ProductionOrder na płaskie
// parametry wymagane przez legacy API — klient (Main) wywołuje report(order) i nie wie, że pod spodem działa stary system.
public class LegacyAuditSystemAdapter implements BoardReporter {
    private final LegacyAuditSystem legacyAuditSystem;

    public LegacyAuditSystemAdapter(LegacyAuditSystem legacyAuditSystem) {
        this.legacyAuditSystem = legacyAuditSystem;
    }

    @Override
    public void report(ProductionOrder order) {
        AuditData data = new AuditData(
                order.getId(),
                order.getClass().getSimpleName(),
                order.getQuantity(),
                order.getStatus().name()
        );
        legacyAuditSystem.logProduction(data);
    }
}
// Koniec, Tydzień 3
// Wzorzec Adapter 1