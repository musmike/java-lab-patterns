package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.assemblyline.assemblingstrategiers.AssemblingStrategy;
import pl.lab.factory.assemblyline.assemblingstrategiers.SolderingStrategy;
import pl.lab.factory.pcbboards.BaseBoard;

public class SolderStep extends AssemblyStep{
    public SolderStep(SolderingStrategy strategy) {
        super(strategy);
    }

    @Override
    public void perform(BaseBoard board) throws InterruptedException {
        System.out.println("Soldering components...");
        strategy.execute(board);
    }
}
