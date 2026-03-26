package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.assemblyline.Mounter;
import pl.lab.factory.assemblyline.coordination.MachineMediator;
import pl.lab.factory.pcbboards.BaseBoard;

import java.util.concurrent.TimeUnit;

public class ManualMount implements MountingStrategy {
    private final MachineMediator mediator;

    public ManualMount(MachineMediator mediator) {
        this.mediator = mediator;
    }
    @Override
    public void execute(BaseBoard board) throws InterruptedException {
        mediator.requestMount(board, this);
    }
}
