package pl.lab.factory.safetymediator.components;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.safetymediator.SafetyMediator;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 2 (Konkretny komponent)
public class SmokeDetector extends SafetyDevice{
    public SmokeDetector(SafetyMediator m){
        super(m);
    }

    public void detectFire(){
        SystemLogger.INSTANCE.logInfo("SENSOR", "Fire detected!");
        mediator.notify(this, "FIRE_ALARM");
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 2