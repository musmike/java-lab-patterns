package pl.lab.factory.assemblyline.machinesubroutines;

import pl.lab.factory.assemblyline.Machine;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 3
// Konkretny dekorator
public class MachineAdjusterDecorator<T extends Machine> extends MachineDecorator<T> {
    public MachineAdjusterDecorator(T decorated) {
        super(decorated);
    }

    @Override
    public void perform(BaseBoard baseBoard) {
        System.out.println("Scanning for board parameters...");
        System.out.println("-=- Board length: " + baseBoard.getLength() + "\n-=- Board width: " + baseBoard.getWidth()
                + "\n-=- Board color: " + baseBoard.getBoardColor() + "\n-=- Board layers: " + baseBoard.getLayers());
        System.out.println("Machine readjusted to parameters!");
        super.perform(baseBoard);
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 3