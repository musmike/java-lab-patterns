package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.assemblyline.assemblingstrategies.MountingStrategy;
import pl.lab.factory.pcbboards.BaseBoard;
//Tydzien 8 - Podstawienie Liskov
//klasa którą można podstawić pod klasę dziedziczoną w dowolnym miejscu
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
//LSP 3
//koniec tydzien 8