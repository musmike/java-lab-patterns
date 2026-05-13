package pl.lab.factory.warehouse.packing;

import java.util.HashMap;
import java.util.Map;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Flyweight 3
public class PackageFactory {

    public static final String TYPE_CARDBOARD = "cardboard";
    public static final String TYPE_PLASTIC = "plastic";

    private static final Map<String, PackageImplementor> flyweights = new HashMap<>();

    public static PackageImplementor getPackage(String type) {
        if (!flyweights.containsKey(type)) {
            switch (type.toLowerCase()) {
                case TYPE_CARDBOARD:
                    flyweights.put(type, new CardboardBoxPacking());
                    break;
                case TYPE_PLASTIC:
                    flyweights.put(type, new PlasticBoxPacking());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown package type: " + type);
            }
        }
        return flyweights.get(type);
    }
}
// Koniec, Tydzień 4
// Wzorzec Flyweight 3