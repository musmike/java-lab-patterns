package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.assemblyline.Mounter;
import pl.lab.factory.pcbboards.BaseBoard;

import java.util.concurrent.TimeUnit;

public class ManualMount implements MountingStrategy {
    @Override
    public void execute(BaseBoard board) throws InterruptedException {
        board.mountComponents();
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Manually mounting components!");
    }
}
