package pl.lab.factory.assemblyline.assemblingstrategiers.safetyproxies;

import pl.lab.factory.assemblyline.assemblingstrategiers.PrintingStrategy;
import pl.lab.factory.pcbboards.BaseBoard;

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
