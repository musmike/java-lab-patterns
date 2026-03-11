package pl.lab.factory.reporting;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 1 (Adaptee — klasa zewnętrzna/legacy)
// Istniejący system audytu z interfejsem niezgodnym z kontraktem.
// Nie można go modyfikować. Przyjmuje płaskie parametry zamiast obiektów domenowych.
public class LegacyAuditSystem {
    public void logProduction(String orderId, String boardType, int quantity, String statusCode) {
        System.out.printf("[LEGACY AUDIT] orderId=%-10s type=%-40s qty=%d status=%s%n",
                orderId, boardType, quantity, statusCode);
    }
}
// Koniec, Tydzień 3
// Wzorzec Adapter 1