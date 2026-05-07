package pl.lab.factory.assemblyline;

import pl.lab.factory.assemblyline.assemblingsteps.*;
import pl.lab.factory.assemblyline.assemblingstrategies.AutoMount;
import pl.lab.factory.assemblyline.assemblingstrategies.LaserPrint;
import pl.lab.factory.assemblyline.assemblingstrategies.WaveSolder;
import pl.lab.factory.assemblyline.assemblingstrategies.safetyproxies.PrintingProxy;
import pl.lab.factory.assemblyline.assemblingstrategies.safetyproxies.SolderingProxy;
import pl.lab.factory.assemblyline.coordination.FactoryMachineMediator;
import pl.lab.factory.assemblyline.coordination.MachineMediator;
import pl.lab.factory.pcbboards.BaseBoard;

import java.util.List;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 1 (Konkretny komponent)
// Bazowa implementacja produkcji płytki — drukuje ścieżki i lutuje.
// Opakowywana przez dekoratory w celu rozszerzenia zachowania bez dziedziczenia.
public class SimpleBoardProducer implements BoardProducer  {
    @Override
    public void produce(BaseBoard board) throws InterruptedException {
        MachineMediator mediator = new FactoryMachineMediator();
        AssemblyStepManufacturingFacade assembler = new AssemblyStepManufacturingFacade(List.of(
                new MountStep(new AutoMount(mediator)),
                new PrintStep(new PrintingProxy(new LaserPrint(mediator))),
                new SolderStep(new SolderingProxy(new WaveSolder(mediator)))
        ));
        assembler.manufacture(board);
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 1