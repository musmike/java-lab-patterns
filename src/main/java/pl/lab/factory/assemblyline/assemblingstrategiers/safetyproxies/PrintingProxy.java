package pl.lab.factory.assemblyline.assemblingstrategiers.safetyproxies;

import pl.lab.factory.assemblyline.assemblingstrategiers.PrintingStrategy;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Proxy 2 - 2 z 2 proxy sprawdzajacych poprawnosc dzialania
public class PrintingProxy implements PrintingStrategy {

    private PrintingStrategy strategy;

    public PrintingProxy(PrintingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void execute(BaseBoard board) throws InterruptedException {
        if(!board.is_componentsMounted()){
            System.out.println("[MACHINE SAFETY ERROR] - Printing cannot commence; components not mounted!");
            throw new InterruptedException("Board not mounted, while attempting printing.");
        }
        strategy.execute(board);
    }
}
//proxy 2
//koniec tydzien 4