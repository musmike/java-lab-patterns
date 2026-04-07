package pl.lab.factory.orders.visitor;

import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 1 (Interface Visitor)
// Definiuje metody odwiedzin dla każdego konkretnego typu zamówienia.
public interface OrderVisitor {
    void visit(BaseBoardProductionOrder order);
    void visit(BaseElectronicBoardProductionOrder order);
}
// Koniec, Tydzień 6
// Wzorzec Visitor 1