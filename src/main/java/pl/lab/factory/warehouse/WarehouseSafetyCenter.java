package pl.lab.factory.warehouse;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.safetymediator.SafetyEvent;
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

    private static final String KEY_GATE = "GATE";
    private static final String KEY_SIREN = "SIREN";
    private static final String KEY_SPRINKLER = "SPRINKLER";
    private static final String STATE_CLOSED = "CLOSED";
    private static final String STATE_OPEN = "OPEN";
    private static final String STATE_ON = "ON";
    private static final String STATE_OFF = "OFF";

    public void setDetector(SmokeDetector detector) {
        this.detector = detector;
    }

    public void setGate(FireGate gate) {
        this.gate = gate;
    }

    public void setSprinkler(SprinklerSystem sprinkler) {
        this.sprinkler = sprinkler;
    }

    public void setSiren(AlarmSiren siren) {
        this.siren = siren;
    }

    @Override
    public void notify(SafetyDevice sender, SafetyEvent event){
        if(sender == detector && event == SafetyEvent.FIRE_ALARM){
            SystemLogger.INSTANCE.logInfo("WAREHOUSE SAFETY", "Initiating fire protocol!");
            siren.turnOn();
            gate.close();
        }

        if(sender == gate && event == SafetyEvent.GATE_CLOSED){
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

        states.put(KEY_GATE, gate.isClosed() ? STATE_CLOSED : STATE_OPEN);
        states.put(KEY_SIREN, siren.isActive() ? STATE_ON : STATE_OFF);
        states.put(KEY_SPRINKLER, sprinkler.isActive() ? STATE_ON : STATE_OFF);

        return new SafetySystemMemento(states);
    }

    public void restore(SafetySystemMemento memento){
        if (memento == null) return;
        SystemLogger.INSTANCE.logInfo("MEMENTO", "Restoring warehouse configuration...");

        Map<String, String> states = memento.getSavedStates();

        if (STATE_OFF.equals(states.get(KEY_SIREN))) siren.turnOff(); else siren.turnOn();
        if (STATE_OFF.equals(states.get(KEY_SPRINKLER))) sprinkler.stopPumps(); else sprinkler.startPumps();
        if (STATE_OPEN.equals(states.get(KEY_GATE))) gate.open(); else gate.close();
    }
    // Koniec, Tydzień 5
    // Wzorzec Memento 2
}
// Koniec, Tydzień 5
// Wzorzec Mediator 2