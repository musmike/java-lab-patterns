package pl.lab.factory.assemblyline.machinestates;

import java.util.concurrent.TimeUnit;

import pl.lab.factory.assemblyline.Machine;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec state 3 - konkretny stan maszyny
public class OffState implements MachineState{
    private final short DEFAULT_TIMEOUT = 3;


    @Override
    public void call(Machine machine) throws InterruptedException {
        System.out.println("Machine idle... Starting up!");
        TimeUnit.SECONDS.sleep(DEFAULT_TIMEOUT);
    }

}
//state 3
//koniec tydzien 6