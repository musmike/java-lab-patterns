package pl.lab.factory.assemblyline.machinesubroutines;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.assemblyline.Machine;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 3
// Konkretny dekorator
public class MachineWarmupDecorator<T extends Machine> extends MachineDecorator<T> {
    public MachineWarmupDecorator(T decorated) {
        super(decorated);
    }

    @Override
    public void perform(BaseBoard baseBoard) {
        SystemLogger.INSTANCE.log("[WARMUP]", "Warming the machine up before operation...\n.\n.\n.\nWarmed up!");
        super.perform(baseBoard);
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 3