package pl.lab.factory.warehouse;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.safetymediator.SafetyMediator;
import pl.lab.factory.safetymediator.SafetySystemMemento;
import pl.lab.factory.safetymediator.components.*;

import java.util.HashMap;
import java.util.Map;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 2 (Konkretny Mediator)
public class WarehouseSafetyCenter implements SafetyMediator {
    private SmokeDetector detector;
    private FireGate gate;
    private SprinklerSystem sprinkler;
    private AlarmSiren siren;

    public void setComponents(SmokeDetector detector, FireGate gate, SprinklerSystem sprinkler, AlarmSiren siren){
        this.detector = detector;
        this.gate = gate;
        this.sprinkler = sprinkler;
        this.siren = siren;
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

    // Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
    // Wzorzec Memento 2 (Originator)
    public SafetySystemMemento save(){
        SystemLogger.INSTANCE.logInfo("MEMENTO", "Taking a safety snapshot of the warehouse...");
        Map<String, String> states = new HashMap<>();

        states.put("GATE", gate.isClosed() ? "CLOSED" : "OPEN");
        states.put("SIREN", siren.isActive() ? "ON" : "OFF");
        states.put("SPRINKLER", sprinkler.isActive() ? "ON" : "OFF");

        return new SafetySystemMemento(states);
    }

    public void restore(SafetySystemMemento memento){
        if (memento == null) return;
        SystemLogger.INSTANCE.logInfo("MEMENTO", "Restoring warehouse configuration...");

        Map<String, String> states = memento.getSavedStates();

        if ("OFF".equals(states.get("SIREN"))) siren.turnOff(); else siren.turnOn();
        if ("OFF".equals(states.get("SPRINKLER"))) sprinkler.stopPumps(); else sprinkler.startPumps();
        if ("OPEN".equals(states.get("GATE"))) gate.open(); else gate.close();
    }
    // Koniec, Tydzień 5
    // Wzorzec Memento 2
}
// Koniec, Tydzień 5
// Wzorzec Mediator 2