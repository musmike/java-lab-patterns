package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.assemblyline.Machine;
import pl.lab.factory.assemblyline.Mounter;
import pl.lab.factory.assemblyline.machinesubroutines.MachineAdjusterDecorator;
import pl.lab.factory.assemblyline.machinesubroutines.MachineWarmupDecorator;
import pl.lab.factory.assemblyline.machinesubroutines.MounterValueTesterDecorator;
import pl.lab.factory.pcbboards.BaseBoard;

public class AutoMount implements MountingStrategy {
    @Override
    public void execute(BaseBoard board) throws InterruptedException {
        Mounter mounter = Mounter.getInstance();
        Machine decoratedMounter = new MachineWarmupDecorator<>(new MachineAdjusterDecorator<>(new MounterValueTesterDecorator(mounter, 1)));
        decoratedMounter.perform(board);
        System.out.println("Automatically mounting components!");
    }
}
