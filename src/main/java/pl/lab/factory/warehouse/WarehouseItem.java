package pl.lab.factory.warehouse;

import pl.lab.factory.SystemLogger;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 2 (Liść)
public class WarehouseItem implements WarehouseComponent{
    private final String name;
    private final int quantity;

    public WarehouseItem(String name, int quantity)
    {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public void print(String indent){
        SystemLogger.INSTANCE.logInfo(
                "WAREHOUSE",
                indent+"- "+name+" | qty: "+quantity
        );
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 2