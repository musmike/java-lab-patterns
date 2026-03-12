package pl.lab.factory.warehouse.shipping;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 2 (Abstrakcja)
public abstract class ShippingStrategy {
    protected PackageImplementor implementor;

    public ShippingStrategy(PackageImplementor implementor){
        this.implementor = implementor;
    }

    public abstract void shipOrder(ProductionOrder order);
}
// Koniec, Tydzień 3
// Wzorzec Bridge 2