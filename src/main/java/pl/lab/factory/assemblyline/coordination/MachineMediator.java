package pl.lab.factory.assemblyline.coordination;

import pl.lab.factory.assemblyline.assemblingstrategies.MountingStrategy;
import pl.lab.factory.assemblyline.assemblingstrategies.PrintingStrategy;
import pl.lab.factory.assemblyline.assemblingstrategies.SolderingStrategy;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 3 (Mediator – interfejs)
public interface MachineMediator {
    void requestPrint(BaseBoard baseBoard, PrintingStrategy strategy);
    void requestMount(BaseBoard baseBoard, MountingStrategy strategy);
    void requestSolder(BaseBoard baseBoard, SolderingStrategy strategy);
}

//mediator 3
//koniec tydzien 5