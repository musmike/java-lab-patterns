package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.assemblyline.assemblingstrategies.SolderingStrategy;
import pl.lab.factory.pcbboards.BaseBoard;
//Tydzien 8 - Podstawienie Liskov
//klasa którą można podstawić pod klasę dziedziczoną w dowolnym miejscu
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
//LSP 3
//koniec tydzien 8