package pl.lab.factory.warehouse;

import pl.lab.factory.SystemLogger;
import java.util.ArrayList;
import java.util.List;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 2 (Kompozyt)
public class WarehouseSection implements WarehouseComponent{
    private final String name;
    private final List<WarehouseComponent> children = new ArrayList<>();

    public WarehouseSection(String name){
        this.name = name;
    }

    public void add(WarehouseComponent component){
        children.add(component);
    }

    @Override
    public void remove(String orderId){
        children.removeIf(component->
                component instanceof WarehouseItem item && item.getOrderId().equals(orderId));
        for(WarehouseComponent component : children){
            component.remove(orderId);
        }
    }

    @Override
    public void print(String indent){
        SystemLogger.INSTANCE.logInfo(
                "WAREHOUSE",
                indent+"+ "+name
        );
        for(WarehouseComponent child: children){
            child.print(indent + "  ");
        }
    }

    public String getName() {
        return name;
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 2