package pl.lab.factory.assemblyline;

import pl.lab.factory.assemblyline.assemblingsteps.*;
import pl.lab.factory.assemblyline.assemblingstrategiers.AutoMount;
import pl.lab.factory.assemblyline.assemblingstrategiers.LaserPrint;
import pl.lab.factory.assemblyline.assemblingstrategiers.WaveSolder;
import pl.lab.factory.pcbboards.BaseBoard;

import java.util.ArrayList;
import java.util.List;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 1 (Konkretny komponent)
// Bazowa implementacja produkcji płytki — drukuje ścieżki i lutuje.
// Opakowywana przez dekoratory w celu rozszerzenia zachowania bez dziedziczenia.
public class SimpleBoardProducer implements BoardProducer  {
    @Override
    public void produce(BaseBoard board) throws InterruptedException {

        AssemblyStepFacade assembler = new AssemblyStepFacade(List.of(
                new MountStep(new AutoMount()),
                new PrintStep(new LaserPrint()),
                new SolderStep(new WaveSolder())
        ));
        assembler.manufacture(board);
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 1