package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.assemblyline.CircuitPrinter;
import pl.lab.factory.assemblyline.coordination.MachineMediator;
import pl.lab.factory.pcbboards.BaseBoard;

public class LaserPrint implements PrintingStrategy{
    private final MachineMediator mediator;

    public LaserPrint(MachineMediator mediator) {
        this.mediator = mediator;
    }
    @Override
    public void execute(BaseBoard board) throws InterruptedException {
        mediator.requestPrint(board, this);
    }
}
