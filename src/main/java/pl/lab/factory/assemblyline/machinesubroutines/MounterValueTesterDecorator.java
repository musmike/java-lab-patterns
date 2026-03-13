package pl.lab.factory.assemblyline.machinesubroutines;

import pl.lab.factory.assemblyline.Mounter;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// konkretny dekorator 3
public class MounterValueTesterDecorator extends MachineDecorator<Mounter> {
    private double value;

    public MounterValueTesterDecorator(Mounter decorated, double value) {
        super(decorated);
        this.value = value;
    }


    @Override
    public void perform(BaseBoard board) {
        super.perform(board);
        if(decorated != null){
            System.out.println("Testing final value of " + value + " for mounted circuits.");
            double finalValue = board.getValue(value);
            System.out.println("Final value of board for " + value + ": " + finalValue);
        }
    }
}
//koniec decorator 3
// koniec tydzien 3