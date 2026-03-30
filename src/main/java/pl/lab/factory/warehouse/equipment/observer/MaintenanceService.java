package pl.lab.factory.warehouse.equipment.observer;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.warehouse.equipment.WarehouseEquipment;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Observer 2 (Concrete Observer)
public class MaintenanceService implements MaintenanceObserver{
    @Override
    public void onMaintenanceRequired(WarehouseEquipment equipment, String reason){
        SystemLogger.INSTANCE.logInfo("SERVICE-DEPT",
                "ATTENTION! Maintenance ticket created for: " + equipment.getName() +
                        " [" + equipment.getId() + "]. Reason: " + reason);
    }
}
// Koniec, Tydzień 6
// Wzorzec Observer 2