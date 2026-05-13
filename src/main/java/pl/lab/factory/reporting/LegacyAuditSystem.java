package pl.lab.factory.reporting;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 1 (Adaptee — klasa zewnętrzna/legacy)
// Istniejący system audytu z interfejsem niezgodnym z kontraktem.
// Nie można go modyfikować. Przyjmuje płaskie parametry zamiast obiektów domenowych.

// Tydzień 7 - Stosowanie zasad pojedynczej odpowiedzialności i otwarte-zamknięte
// Zasada pojedynczej odpowiedzalności (SRP) 5
// Klasa LegacyAuditSystem jest odpowiedzialna tylko za logowanie informacji o zamówieniu
public class LegacyAuditSystem {
    private static final int ORDER_ID_WIDTH = 10;
    private static final int ORDER_TYPE_WIDTH = 40;
    private static final String LOG_FORMAT = "[LEGACY AUDIT] orderId=%-" + ORDER_ID_WIDTH + "s type=%-" + ORDER_TYPE_WIDTH + "s qty=%d status=%s%n";

    public void logProduction(AuditData data) {
        System.out.printf(LOG_FORMAT, data.getOrderId(), data.getBoardType(), data.getQuantity(), data.getStatusCode());
    }
}
// Koniec, Tydzień 3
// Wzorzec Adapter 1

// Koniec, Tydzień 7
// Zasada pojedynczej odpowiedzalności (SRP) 5
