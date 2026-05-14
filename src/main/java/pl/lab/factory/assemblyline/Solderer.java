package pl.lab.factory.assemblyline;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
// Wzorzec Singleton 1 (Lazy Initialization)
// Singleton tworzy instancję leniwie przy pierwszym wywołaniu getInstance().
// W tej wersji nie ma synchronizacji, więc nie jest bezpieczny dla wielu wątków.
public class Solderer extends Machine{
    private static Solderer instance;
    private boolean reflowMode = false;

    private Solderer(){}

    public void setReflowMode(boolean set){reflowMode = set;}
    public static Solderer getInstance(){
        if (instance == null) {
            instance = new Solderer();
        }
        return instance;
    }

    private void solderBoard(BaseBoard baseBoard){
        baseBoard.solder();
    }

    @Override
    public void perform(BaseBoard baseBoard) {
        super.perform(baseBoard);
        SystemLogger.INSTANCE.log("[SOLDERER]", "Soldering board!");
        SystemLogger.INSTANCE.log("[SOLDERER]", "Using " + (reflowMode ? "reflow " : "wave ") + "mode");
        solderBoard(baseBoard);
    }
}
// Koniec, Tydzień 2
// Wzorzec Singleton 1
