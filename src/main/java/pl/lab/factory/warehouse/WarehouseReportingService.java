package pl.lab.factory.warehouse;

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada segregacji interfejsów (ISP) 1 (Interfejs segregowany)
public interface WarehouseReportingService {
    void printWarehouse();
    void printLegacyWarehouse();
}
// Koniec, Tydzień 8
// Zasada segregacji interfejsów (ISP) 1