package pl.lab.factory.assemblyline.coordination;

import pl.lab.factory.assemblyline.assemblingstrategies.MountingStrategy;
import pl.lab.factory.assemblyline.assemblingstrategies.PrintingStrategy;
import pl.lab.factory.assemblyline.assemblingstrategies.SolderingStrategy;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 3 (Mediator – interfejs)
public interface MachineMediator {
    void requestPrint(BaseBoard board, PrintingStrategy strategy);
    void requestMount(BaseBoard board, MountingStrategy strategy);
    void requestSolder(BaseBoard board, SolderingStrategy strategy);
}

//mediator 3
//koniec tydzien 5