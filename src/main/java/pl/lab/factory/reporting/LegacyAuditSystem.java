package pl.lab.factory.reporting;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 1 (Adaptee — klasa zewnętrzna/legacy)
// Istniejący system audytu z interfejsem niezgodnym z kontraktem.
// Nie można go modyfikować. Przyjmuje płaskie parametry zamiast obiektów domenowych.

// Tydzień 7 - Stosowanie zasad pojedynczej odpowiedzialności i otwarte-zamknięte
// Zasada pojedynczej odpowiedzalności (SRP) 5
// Klasa LegacyAuditSystem jest odpowiedzialna tylko za logowanie informacji o zamówieniu
public class LegacyAuditSystem {
    public void logProduction(AuditData data) {
        System.out.printf("[LEGACY AUDIT] orderId=%-10s type=%-40s qty=%d status=%s%n",
                data.orderId(), data.boardType(), data.quantity(), data.statusCode());
    }

}
// Koniec, Tydzień 3
// Wzorzec Adapter 1

// Koniec, Tydzień 7
// Zasada pojedynczej odpowiedzalności (SRP) 5
