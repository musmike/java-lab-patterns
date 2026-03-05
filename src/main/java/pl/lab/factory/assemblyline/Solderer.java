package pl.lab.factory.assemblyline;

import pl.lab.factory.pcbboards.BaseBoard;

public class Solderer {
    //lazy init
    private static Solderer instance;
    private Solderer(){}
    public static Solderer getInstance(){
        if(instance == null){
            instance = new Solderer();
        }
        return instance;
    }

    public void solderBoard(BaseBoard board){
        board.solder();
    }
}
