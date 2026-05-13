package pl.lab.factory.assemblyline.machinesubroutines;

import pl.lab.factory.assemblyline.Machine;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 3
// Abstrakcyjny dekorator maszyn
public class MachineDecorator<T extends Machine> extends Machine {
    protected T decorated;

    public MachineDecorator(T decorated) {
        this.decorated = decorated;
    }

    @Override
    public void perform(BaseBoard baseBoard) {
        decorated.perform(baseBoard);
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 3