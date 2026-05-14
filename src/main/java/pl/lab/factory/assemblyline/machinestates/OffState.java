package pl.lab.factory.assemblyline.machinestates;

import java.util.concurrent.TimeUnit;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.assemblyline.Machine;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec state 3 - konkretny stan maszyny
public class OffState implements MachineState{
    private final short STARTUP_DELAY_SECONDS = 3;


    @Override
    public void call(Machine machine) throws InterruptedException {
        SystemLogger.INSTANCE.log("", "Machine idle... Starting up!");
        TimeUnit.SECONDS.sleep(STARTUP_DELAY_SECONDS);
    }

}
//state 3
//koniec tydzien 6