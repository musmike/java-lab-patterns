package pl.lab.factory.assemblyline.machinesubroutines;

import pl.lab.factory.assemblyline.Machine;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// dekorator 3 - abstrakcyjny dekorator maszyn

public class MachineDecorator<T extends Machine> implements Machine {
    protected T decorated;

    public MachineDecorator(T decorated) {
        this.decorated = decorated;
    }

    @Override
    public void perform(BaseBoard board) {
        decorated.perform(board);
    }
}
//koniec decorator 3
//koniec tydzien 3