package pl.lab.factory.warehouse.shipping;

import java.util.HashMap;
import java.util.Map;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Flyweight 3
public class PackageFactory {
    private static final Map<String, PackageImplementor> flyweights = new HashMap<>();

    public static PackageImplementor getPackage(String type){
        if(!flyweights.containsKey(type)){
            switch (type.toLowerCase()){
                case "cardboard":
                    flyweights.put(type, new CardboardBoxPacking());
                    break;
                case "plastic":
                    flyweights.put(type, new PlacticBoxPacking());
                    break;
            }
        }
        return flyweights.get(type);
    }
}
// Koniec, Tydzień 4
// Wzorzec Flyweight 3