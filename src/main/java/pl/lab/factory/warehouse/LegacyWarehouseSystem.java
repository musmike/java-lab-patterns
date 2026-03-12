package pl.lab.factory.warehouse;

import java.util.HashMap;
import java.util.Map;
import pl.lab.factory.SystemLogger;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 2 (Adaptee — klasa zewnętrzna/legacy)
public class LegacyWarehouseSystem {
    private final Map<String, Integer> storage = new HashMap<>();

    public void storeItem(String itemName, int quantity){
        storage.put(itemName, storage.getOrDefault(itemName, 0) + quantity);

        SystemLogger.INSTANCE.logInfo(
                "[LEGACY WAREHOUSE]",
                "Stored " + quantity + " units of " + itemName
        );
    }

    public void printStorage(){
        SystemLogger.INSTANCE.logInfo("WAREHOUSE", "Current warehouse state:");

        storage.forEach((item, qty)->
                SystemLogger.INSTANCE.logInfo(
                        "[LEGACY WAREHOUSE]",
                        item+" -> "+qty
                ));
    }
}
// Koniec, Tydzień 3
// Wzorzec Adapter 2