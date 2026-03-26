package pl.lab.factory.safetymediator.components;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.safetymediator.SafetyMediator;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 2 (Konkretny komponent)
public class AlarmSiren extends SafetyDevice{
    public AlarmSiren(SafetyMediator m){
        super(m);
    }

    public void turnOn(){
        SystemLogger.INSTANCE.logInfo("SIREN", "Audio-visual alarm ACTIVATED.");
    }

    public void turnOff() {
        SystemLogger.INSTANCE.logInfo("SIREN", "Siren SILENCED.");
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 2