package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.assemblyline.coordination.MachineMediator;
import pl.lab.factory.pcbboards.BaseBoard;

// tydzien 7 - stosowanie zasad SRP i OCP
// zasada OCP - specyfikacja otwartego formatu klasy dziedziczonej
// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec strategy 3 - konkretna strategia typu procesu
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
//strategy 3
//koniec tydzien 6
//koniec tydzien 7