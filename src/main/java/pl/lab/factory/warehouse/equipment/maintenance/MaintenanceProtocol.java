package pl.lab.factory.warehouse.equipment.maintenance;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.warehouse.equipment.WarehouseEquipment;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Template 2 (Abstract Class)
public abstract class MaintenanceProtocol {
    public final void executeService(WarehouseEquipment equipment){
        SystemLogger.INSTANCE.logInfo("MAINTENANCE", "Starting maintenance for: " + equipment.getName() + " " + equipment.getId());

        equipment.setOperational(false);

        try{
            performDiagnostics(equipment);
            applyTechnicalService(equipment);

            equipment.resetMaintenance();

            SystemLogger.INSTANCE.logInfo("SERVICE", equipment.getName() + " " + equipment.getId() + " stats reset and verified.");
        }catch (Exception e){
            SystemLogger.INSTANCE.logError("SERVICE", "Critical failure during service of " + equipment.getId());
        }finally {
            equipment.setOperational(true);
            SystemLogger.INSTANCE.logInfo("SERVICE", equipment.getName() + " " + equipment.getId() + " has been returned.");
        }

    }

    protected abstract void performDiagnostics(WarehouseEquipment e);
    protected abstract void applyTechnicalService(WarehouseEquipment e);
}
// Koniec, Tydzień 6
// Wzorzec Observer 2