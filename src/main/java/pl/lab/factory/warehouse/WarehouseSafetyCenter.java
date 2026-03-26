package pl.lab.factory.warehouse;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.safetymediator.SafetyMediator;
import pl.lab.factory.safetymediator.components.*;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 2 (Konkretny Mediator)
public class WarehouseSafetyCenter implements SafetyMediator {
    private SmokeDetector detector;
    private FireGate gate;
    private SprinklerSystem sprinkler;
    private AlarmSiren siren;

    public void setComponents(SmokeDetector d, FireGate g, SprinklerSystem s, AlarmSiren as){
        this.detector = d;
        this.gate = g;
        this.sprinkler = s;
        this.siren = as;
    }

    @Override
    public void notify(SafetyDevice sender, String event){
        if(sender == detector && event.equals("FIRE_ALARM")){
            SystemLogger.INSTANCE.logInfo("WAREHOUSE SAFETY", "Initiating fire protocol!");
            siren.turnOn();
            gate.close();
        }

        if(sender == gate && event.equals("GATE_CLOSED")){
            SystemLogger.INSTANCE.logInfo("WAREHOUSE SAFETY", "Zone isolated. Starting water suppression.");
            sprinkler.startPumps();
        }
    }

    @Override
    public void resetSystem(){
        SystemLogger.INSTANCE.logInfo("WAREHOUSE SAFETY", "Manual System Reset initiated. Restoring normal operations...");
        siren.turnOff();
        sprinkler.stopPumps();
        gate.open();

    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 2