package pl.lab.factory.assemblyline.machinesubroutines;

import pl.lab.factory.assemblyline.Machine;
import pl.lab.factory.pcbboards.BaseBoard;

public class MachineAdjusterDecorator<T extends Machine> extends MachineDecorator<T> {
    public MachineAdjusterDecorator(T decorated) {
        super(decorated);
    }

    @Override
    public void perform(BaseBoard board) {
        System.out.println("Scanning for board parameters...");
        System.out.println("-=- Board length: " + board.getLength() +"\n-=- Board width: "+board.getWidth()
                +"\n-=- Board color: " + board.getBoardColor() + "\n-=- Board layers: " + board.getLayers());
        System.out.println("Machine adjusted to parameters!");
        super.perform(board);

    }
}
