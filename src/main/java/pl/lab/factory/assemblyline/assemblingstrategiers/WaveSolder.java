package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.assemblyline.Solderer;
import pl.lab.factory.assemblyline.coordination.MachineMediator;
import pl.lab.factory.pcbboards.BaseBoard;

public class WaveSolder implements SolderingStrategy{
    private final MachineMediator mediator;

    public WaveSolder(MachineMediator mediator) {
        this.mediator = mediator;
    }
    @Override
    public void execute(BaseBoard board) throws InterruptedException {
        mediator.requestSolder(board, this);
    }
}
