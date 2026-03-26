package pl.lab.factory.safetymediator.components;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.safetymediator.SafetyMediator;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 2 (Konkretny komponent)
public class FireGate extends SafetyDevice{
    public FireGate(SafetyMediator m){
        super(m);
    }

    public void close(){
        SystemLogger.INSTANCE.logInfo("GATE", "Closing heavy fire gate to block oxygen...");
        mediator.notify(this, "GATE_CLOSED");
    }

    public void open(){
        SystemLogger.INSTANCE.logInfo("GATE", "Opening gate. Access restored.");
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 2