package pl.lab.factory.warehouse.shipping;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 2 (Konkretny dekorator)
public class AntiStaticBagDecorator extends PackingDecorator{
    public AntiStaticBagDecorator(PackageImplementor component){
        super(component);
    }

    @Override
    public void packOrder(ProductionOrder order){
        component.packOrder(order);
        SystemLogger.INSTANCE.logInfo("PACKING", "Adding anti-static bag");
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 2