package pl.lab.factory.assemblyline;

import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
// Wzorzec Singleton 1 (Lazy Initialization)
// Singleton tworzy instancję leniwie przy pierwszym wywołaniu getInstance().
// W tej wersji nie ma synchronizacji, więc nie jest bezpieczny dla wielu wątków.
public class Solderer {
    private static Solderer instance;

    private Solderer(){}

    public static Solderer getInstance(){
        if (instance == null) {
            instance = new Solderer();
        }
        return instance;
    }

    public void solderBoard(BaseBoard board){
        board.solder();
    }
}
// Koniec, Tydzień 2
// Wzorzec Singleton 1
