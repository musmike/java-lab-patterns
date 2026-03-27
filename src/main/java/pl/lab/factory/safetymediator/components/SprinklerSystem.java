package pl.lab.factory.safetymediator.components;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.safetymediator.SafetyMediator;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 2 (Konkretny komponent)
public class SprinklerSystem extends SafetyDevice{
    private boolean isActive = false;
    public SprinklerSystem(SafetyMediator m){
        super(m);
    }

    public void startPumps(){
        this.isActive = true;
        SystemLogger.INSTANCE.logInfo("SPRINKLER", "Water pumps STARTED. Suppression active.");
    }

    public void stopPumps() {
        this.isActive = false;
        SystemLogger.INSTANCE.logInfo("SPRINKLER", "Water pumps STOPPED.");
    }

    public boolean isActive() {
        return isActive;
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 2