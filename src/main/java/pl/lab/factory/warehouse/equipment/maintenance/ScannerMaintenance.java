package pl.lab.factory.warehouse.equipment.maintenance;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.warehouse.equipment.WarehouseEquipment;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Template 2 (Concrete Class)
public class ScannerMaintenance extends MaintenanceProtocol{
    @Override
    protected void performDiagnostics(WarehouseEquipment warehouseEquipment){
        SystemLogger.INSTANCE.logInfo("SERVICE", "[Checking Optics] Cleaning laser lens on " + warehouseEquipment.getName());
        SystemLogger.INSTANCE.logInfo("SERVICE", "[Checking Battery] Testing Li-ion cycle health.");
    }

    @Override
    protected void applyTechnicalService(WarehouseEquipment warehouseEquipment){
        SystemLogger.INSTANCE.logInfo("SERVICE", "[Action] Scanner " + warehouseEquipment.getId() + ": Firmware flashing... New version installed." );
        SystemLogger.INSTANCE.logInfo("SERVICE", "[Action] Calibrating scan engine and updating firmware.");
    }
}
// Koniec, Tydzień 6
// Wzorzec Observer 2