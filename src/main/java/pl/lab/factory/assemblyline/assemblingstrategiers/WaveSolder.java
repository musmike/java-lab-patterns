package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.assemblyline.Solderer;
import pl.lab.factory.pcbboards.BaseBoard;

public class WaveSolder implements SolderingStrategy{
    @Override
    public void execute(BaseBoard board) throws InterruptedException {
        System.out.println("Using reflow soldering method!");
        Solderer.getInstance().setReflowMode(false);
        Solderer.getInstance().perform(board);
    }
}
