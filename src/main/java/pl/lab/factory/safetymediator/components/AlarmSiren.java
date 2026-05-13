package pl.lab.factory.safetymediator.components;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.safetymediator.SafetyMediator;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 2 (Konkretny komponent)
public class AlarmSiren extends SafetyDevice{
    private boolean isActive = false;
    public AlarmSiren(SafetyMediator safetyMediator){
        super(safetyMediator);
    }

    public void turnOn(){
        this.isActive = true;
        SystemLogger.INSTANCE.logInfo("SIREN", "Audio-visual alarm ACTIVATED.");
    }

    public void turnOff() {
        this.isActive = false;
        SystemLogger.INSTANCE.logInfo("SIREN", "Siren SILENCED.");
    }

    public boolean isActive() {
        return isActive;
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 2