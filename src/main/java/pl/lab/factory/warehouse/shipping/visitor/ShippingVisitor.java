package pl.lab.factory.warehouse.shipping.visitor;

import pl.lab.factory.warehouse.shipping.command.ShipOrderCommand;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 2 (Interface Visitor)
public interface ShippingVisitor {
    int visit(ShipOrderCommand command);
}
// Koniec, Tydzień 6
// Wzorzec Visitor 2