package pl.lab.factory.warehouse.shipping;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 2 (Abstrakcyjny dekorator)
public abstract class PackingDecorator implements PackageImplementor{
    protected PackageImplementor component;
    public PackingDecorator(PackageImplementor component){
        this.component = component;
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 2