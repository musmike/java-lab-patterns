package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.assemblyline.coordination.MachineMediator;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec strategy 3 - konkretna strategia typu procesu
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
//strategy 3
//koniec tydzien 6