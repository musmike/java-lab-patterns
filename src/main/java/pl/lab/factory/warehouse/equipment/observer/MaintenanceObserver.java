package pl.lab.factory.warehouse.equipment.observer;

import pl.lab.factory.warehouse.equipment.WarehouseEquipment;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Observer 2 (Interface Observer)
public interface MaintenanceObserver {
    void onMaintenanceRequired(WarehouseEquipment equipment, String reason);
}
// Koniec, Tydzień 6
// Wzorzec Observer 2