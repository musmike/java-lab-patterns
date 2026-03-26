package pl.lab.factory.safetymediator.components;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.safetymediator.SafetyMediator;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 2 (Konkretny komponent)
public class SprinklerSystem extends SafetyDevice{
    public SprinklerSystem(SafetyMediator m){
        super(m);
    }

    public void startPumps(){
        SystemLogger.INSTANCE.logInfo("SPRINKLER", "Water pumps STARTED. Suppression active.");
    }

    public void stopPumps() {
        SystemLogger.INSTANCE.logInfo("SPRINKLER", "Water pumps STOPPED.");
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 2