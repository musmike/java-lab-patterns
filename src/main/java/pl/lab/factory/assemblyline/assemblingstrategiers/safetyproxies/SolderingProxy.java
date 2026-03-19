package pl.lab.factory.assemblyline.assemblingstrategiers.safetyproxies;

import pl.lab.factory.assemblyline.assemblingstrategiers.SolderingStrategy;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Proxy 2 - 1 z 2 proxy sprawdzajacych poprawnosc dzialania
public class SolderingProxy implements SolderingStrategy {
    private SolderingStrategy strategy;

    public SolderingProxy(SolderingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void execute(BaseBoard board) throws InterruptedException {
        if(!board.is_circuitsPrinted() || !board.is_componentsMounted()){
            System.out.println("[MACHINE SAFETY ERROR] - Soldering cannot commence; board not ready for soldering!");
            throw new InterruptedException("Board not printed and mounted, while attempting soldering.");
        }

        strategy.execute(board);
    }
}
// proxy 2
//koniec tydzien 4