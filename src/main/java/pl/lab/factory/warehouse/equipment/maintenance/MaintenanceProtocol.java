package pl.lab.factory.warehouse.equipment.maintenance;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.warehouse.equipment.WarehouseEquipment;

import java.util.HashMap;
import java.util.Map;
// Tydzień 7 - Stosowanie zasad pojedynczej odpowiedzialności i otwarte-zamknięteZadanie
// Otwarte - zamknięte (abstrakcja, sterowanie danymi)

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Template 2 (Abstract Class)
public abstract class MaintenanceProtocol {

    private static final int STANDARD_SERVICE_DURATION_MINUTES = 60;
    private static final int EXTENDED_SERVICE_DURATION_MINUTES = 120;
    private static final int DEFAULT_SERVICE_DURATION_MINUTES  = 30;

    private final Map<String, Integer> serviceDurations = new HashMap<>();

    public MaintenanceProtocol(){
        serviceDurations.put("STANDARD", STANDARD_SERVICE_DURATION_MINUTES);
        serviceDurations.put("EXTENDED", EXTENDED_SERVICE_DURATION_MINUTES);
    }

    public void registerServiceDuration(String type, int minutes) {
        serviceDurations.put(type, minutes);
    }

    public final void executeService(WarehouseEquipment equipment, String serviceType) {
        prepareForService(equipment, serviceType);
        performServiceSafely(equipment);
    }

    private void prepareForService(WarehouseEquipment equipment, String serviceType) {
        int duration = serviceDurations.getOrDefault(serviceType, DEFAULT_SERVICE_DURATION_MINUTES);
        SystemLogger.INSTANCE.logInfo("MAINTENANCE",
                "Starting maintenance for: " + equipment.getName() + " (Est. time: " + duration + "min)");
        
        equipment.setOperational(false);
    }

    private void performServiceSafely(WarehouseEquipment equipment) {
        try {
            executeServiceSteps(equipment);
        } catch (Exception e) {
            SystemLogger.INSTANCE.logError("SERVICE", "Critical failure during service of " + equipment.getId());
        } finally {
            finalizeService(equipment);
        }
    }

    private void executeServiceSteps(WarehouseEquipment equipment) {
        performDiagnostics(equipment);
        applyTechnicalService(equipment);
        equipment.resetMaintenance();
        
        SystemLogger.INSTANCE.logInfo("SERVICE", equipment.getName() + " " + equipment.getId() + " stats reset and verified.");
    }

    private void finalizeService(WarehouseEquipment equipment) {
        equipment.setOperational(true);
        SystemLogger.INSTANCE.logInfo("SERVICE", equipment.getName() + " " + equipment.getId() + " has been returned.");
    }

    protected abstract void performDiagnostics(WarehouseEquipment e);
    protected abstract void applyTechnicalService(WarehouseEquipment e);
}
// Koniec, Tydzień 6
// Wzorzec Observer 2

// Koniec, Tydzień 7
// Otwarte - zamknięte (abstrakcja)