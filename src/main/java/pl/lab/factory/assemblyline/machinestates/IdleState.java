package pl.lab.factory.assemblyline.machinestates;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.assemblyline.Machine;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec state 3 - konkretny stan maszyny
public class IdleState implements MachineState {

    @Override
    public void call(Machine machine) throws InterruptedException {
        SystemLogger.INSTANCE.log("", "Machine  idling. Resuming!");
    }

}
//state 3
//koniec tydzien 6