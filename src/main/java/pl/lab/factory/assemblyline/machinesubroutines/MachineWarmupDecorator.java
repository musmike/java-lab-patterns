package pl.lab.factory.assemblyline.machinesubroutines;

import pl.lab.factory.assemblyline.Machine;
import pl.lab.factory.pcbboards.BaseBoard;

public class MachineWarmupDecorator<T extends Machine> extends MachineDecorator<T>{
    public MachineWarmupDecorator(T decorated) {
        super(decorated);
    }

    @Override
    public void perform(BaseBoard board) {
        System.out.println("[WARMUP] Warming the machine up before operation...\n.\n.\n.\nWarmed up!");
        super.perform(board);
    }
}
