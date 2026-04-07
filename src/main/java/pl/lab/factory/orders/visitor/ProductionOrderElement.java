package pl.lab.factory.orders.visitor;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 1 (Interface Element)
// Definiuje kontrakt dla obiektów, które mogą zostać "odwiedzone" przez wizytatora.
// Gwarantuje, że każdy element w hierarchii (tutaj zamówień) przyjmie wizytatora.
public interface ProductionOrderElement {
    void accept(OrderVisitor visitor);
}
// Koniec, Tydzień 6
// Wzorzec Visitor 1