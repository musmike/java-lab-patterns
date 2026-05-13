package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.assemblyline.assemblingstrategies.PrintingStrategy;
import pl.lab.factory.pcbboards.BaseBoard;

//Tydzien 8 - Podstawienie Liskov
//klasa którą można podstawić pod klasę dziedziczoną w dowolnym miejscu
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
//LSP 3
//koniec tydzien 8