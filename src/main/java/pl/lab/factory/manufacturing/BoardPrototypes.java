package pl.lab.factory.manufacturing;

import pl.lab.factory.electronicboards.BaseElectronicBoard;

import java.util.HashMap;
import java.util.Map;

public class BoardPrototypes {
    private Map<String, BaseElectronicBoard> prototypes = new HashMap<>();

    public void registerPrototype(String name, BaseElectronicBoard board){
        prototypes.put(name, board);
    }
    public BaseElectronicBoard createFromPrototype(String name){
        return prototypes.get(name).clone();
    }
}
