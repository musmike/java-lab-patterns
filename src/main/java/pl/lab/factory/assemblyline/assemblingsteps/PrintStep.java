package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.assemblyline.assemblingstrategiers.AssemblingStrategy;
import pl.lab.factory.assemblyline.assemblingstrategiers.PrintingStrategy;
import pl.lab.factory.pcbboards.BaseBoard;

public class PrintStep extends AssemblyStep{
    public PrintStep(PrintingStrategy strategy) {
        super(strategy);
    }

    @Override
    public void perform(BaseBoard board) throws InterruptedException {
        System.out.println("Printing circuit...");
        strategy.execute(board);
    }
}
