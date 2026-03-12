package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.assemblyline.assemblingstrategiers.AssemblingStrategy;
import pl.lab.factory.assemblyline.assemblingstrategiers.MountingStrategy;
import pl.lab.factory.pcbboards.BaseBoard;

public class MountStep extends AssemblyStep{
    public MountStep(MountingStrategy strategy) {
        super(strategy);
    }

    @Override
    public void perform(BaseBoard board) throws InterruptedException {
        System.out.println("Mounting components...");
        strategy.execute(board);
    }
}
