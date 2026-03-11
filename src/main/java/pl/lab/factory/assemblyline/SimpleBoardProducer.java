package pl.lab.factory.assemblyline;

import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 1 (Konkretny komponent)
// Bazowa implementacja produkcji płytki — drukuje ścieżki i lutuje.
// Opakowywana przez dekoratory w celu rozszerzenia zachowania bez dziedziczenia.
public class SimpleBoardProducer implements BoardProducer  {
    @Override
    public void produce(BaseBoard board) {
        CircuitPrinter.getInstance().printCircuits(board);

        // mozna mount component

        Solderer.getInstance().solderBoard(board);
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 1