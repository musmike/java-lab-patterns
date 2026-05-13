package pl.lab.factory.assemblyline.assemblingstrategies;

import pl.lab.factory.assemblyline.coordination.MachineMediator;
import pl.lab.factory.pcbboards.BaseBoard;

// tydzien 7 - stosowanie zasad SRP i OCP
// zasada OCP - specyfikacja otwartego formatu klasy dziedziczonej
// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec strategy 3 - konkretna strategia typu procesu
public class ManualMount implements MountingStrategy {
    private final MachineMediator machineMediator;

    public ManualMount(MachineMediator machineMediator) {
        this.machineMediator = machineMediator;
    }
    @Override
    public void execute(BaseBoard baseBoard) throws InterruptedException {
        machineMediator.requestMount(baseBoard, this);
    }
}
//strategy 3
//koniec tydzien 6
//koniec tydzien 7