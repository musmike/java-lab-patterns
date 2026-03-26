package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.assemblyline.Machine;
import pl.lab.factory.assemblyline.Mounter;
import pl.lab.factory.assemblyline.coordination.MachineMediator;
import pl.lab.factory.assemblyline.machinesubroutines.MachineAdjusterDecorator;
import pl.lab.factory.assemblyline.machinesubroutines.MachineWarmupDecorator;
import pl.lab.factory.assemblyline.machinesubroutines.MounterValueTesterDecorator;
import pl.lab.factory.pcbboards.BaseBoard;

public class AutoMount implements MountingStrategy {
    private final MachineMediator mediator;

    public AutoMount(MachineMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void execute(BaseBoard board) throws InterruptedException {
        mediator.requestMount(board, this);
    }
}
