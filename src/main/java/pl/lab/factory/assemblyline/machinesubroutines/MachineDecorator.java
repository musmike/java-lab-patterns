package pl.lab.factory.assemblyline.machinesubroutines;

import pl.lab.factory.assemblyline.Machine;
import pl.lab.factory.pcbboards.BaseBoard;

// tydzien 3 - dekorator 3 - abstrakcyjny dekorator maszyn

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
//koniec tydzien 3