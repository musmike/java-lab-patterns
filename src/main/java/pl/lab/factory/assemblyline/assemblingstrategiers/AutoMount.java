package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.assemblyline.Mounter;
import pl.lab.factory.pcbboards.BaseBoard;

import java.util.concurrent.TimeUnit;

public class AutoMount implements MountingStrategy {
    @Override
    public void execute(BaseBoard board) throws InterruptedException {
        Mounter.getInstance().perform(board);
        System.out.println("Automatically mounting components!");
    }
}
