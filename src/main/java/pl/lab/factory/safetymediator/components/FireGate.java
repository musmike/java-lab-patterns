package pl.lab.factory.safetymediator.components;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.safetymediator.SafetyMediator;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 2 (Konkretny komponent)
public class FireGate extends SafetyDevice{
    private boolean isClosed = false;

    public FireGate(SafetyMediator m){
        super(m);
    }

    public void close(){
        this.isClosed = true;
        SystemLogger.INSTANCE.logInfo("GATE", "Closing heavy fire gate to block oxygen...");
        mediator.notify(this, "GATE_CLOSED");
    }

    public void open(){
        this.isClosed = false;
        SystemLogger.INSTANCE.logInfo("GATE", "Opening gate. Access restored.");
    }

    public boolean isClosed() {
        return isClosed;
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 2