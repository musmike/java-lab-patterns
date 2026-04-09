package pl.lab.factory.assemblyline.machinestates;

import pl.lab.factory.assemblyline.Machine;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec state 3 - interfejs stanu maszyny
public interface MachineState {
    void call(Machine machine) throws InterruptedException;
}
//state 3
//koniec tydzien 6