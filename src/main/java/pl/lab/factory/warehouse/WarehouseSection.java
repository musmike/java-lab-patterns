package pl.lab.factory.warehouse;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.warehouse.state.OpenState;
import pl.lab.factory.warehouse.state.SectionState;

import java.util.ArrayList;
import java.util.List;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 2 (Kompozyt)
public class WarehouseSection implements WarehouseComponent {
    private final String name;
    private final List<WarehouseComponent> children = new ArrayList<>();

    // Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
    // Wzorzec State 2 (Context)
    private SectionState state = new OpenState();

    public void setState(SectionState state){
        this.state = state;
    }

    public SectionState getState(){
        return state;
    }

    // Metoda add() pochodzi ze wzorca Composite, ale zmieniona dla wzorca State
    public void add(WarehouseComponent component){
        if(state.canAddItem()){
            children.add(component);
            SystemLogger.INSTANCE.logInfo(this.getName().toUpperCase(), "Order sent");
        }
        else{
            state.printStatus(this.getName());
            SystemLogger.INSTANCE.logInfo(this.getName().toUpperCase(), "Sending order is cancelled");
        }
    }
    // Koniec, Tydzień 6
    // Wzorzec State 2

    public WarehouseSection(String name){
        this.name = name;
    }

    @Override
    public void removeOrder(String orderId){
        children.removeIf(component->
                component instanceof WarehouseItem item && item.getOrderId().equals(orderId));
        for(WarehouseComponent component : children){
            component.removeOrder(orderId);
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