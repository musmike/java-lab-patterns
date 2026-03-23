package pl.lab.factory.mediator;

import pl.lab.factory.orders.ProductionOrder;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 1 (Mediator – interfejs)
// Definiuje interfejs do komunikacji z obiektami Colleague (Departments).
public interface FactoryMediator {
    void notify(Department sender, String event, ProductionOrder order);
}
// Koniec, Tydzień 5
// Wzorzec Mediator 1
