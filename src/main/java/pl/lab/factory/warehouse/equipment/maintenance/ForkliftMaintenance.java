package pl.lab.factory.warehouse.equipment.maintenance;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.warehouse.equipment.WarehouseEquipment;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Template 2 (Concrete Class)
public class ForkliftMaintenance extends MaintenanceProtocol {
    @Override
    protected void performDiagnostics(WarehouseEquipment warehouseEquipment){
        SystemLogger.INSTANCE.logInfo("SERVICE", "[Checking Hydraulic] Testing lift cylinders on " + warehouseEquipment.getName() + " " + warehouseEquipment.getId());
        SystemLogger.INSTANCE.logInfo("SERVICE", "[Checking Brakes] Verifying brake pad thickness.");
    }

    @Override
    protected void applyTechnicalService(WarehouseEquipment warehouseEquipment){
        SystemLogger.INSTANCE.logInfo("SERVICE", "[Action] Forklift " + warehouseEquipment.getId() + ": Tank refilled to 100%.");
        SystemLogger.INSTANCE.logInfo("SERVICE", "[Action] Changing oil and refilling fuel tank.");
    }
}
// Koniec, Tydzień 6
// Wzorzec Observer 2