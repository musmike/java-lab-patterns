package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.assemblyline.CircuitPrinter;
import pl.lab.factory.pcbboards.BaseBoard;

public class LaserPrint implements PrintingStrategy{
    @Override
    public void execute(BaseBoard board) throws InterruptedException {
        System.out.println("Printing the circuit using laser!");
        CircuitPrinter.getInstance().perform(board);
    }
}
