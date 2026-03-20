package pl.lab.factory.warehouse.packing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 2 (Konkretny dekorator)
public class FoamProtectionDecorator extends PackingDecorator {
    public FoamProtectionDecorator(PackageImplementor component){
        super(component);
    }

    @Override
    public void packOrder(ProductionOrder order){
        component.packOrder(order);
        SystemLogger.INSTANCE.logInfo("PACKING", "Adding foam padding");
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 2