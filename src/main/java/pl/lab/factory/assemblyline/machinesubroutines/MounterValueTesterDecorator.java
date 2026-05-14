package pl.lab.factory.assemblyline.machinesubroutines;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.assemblyline.Mounter;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 3
// Konkretny dekorator
public class MounterValueTesterDecorator extends MachineDecorator<Mounter> {
    private double value;

    public MounterValueTesterDecorator(Mounter decorated, double value) {
        super(decorated);
        this.value = value;
    }

    @Override
    public void perform(BaseBoard baseBoard) {
        super.perform(baseBoard);
        if(decorated != null) {
            SystemLogger.INSTANCE.log("", "Testing final value of " + value + " for mounted circuits.");
            double finalValue = baseBoard.getValue(value);
            SystemLogger.INSTANCE.log("", "Final value of board for " + value + ": " + finalValue);
        }
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 3