package pl.lab.factory.assemblyline;

import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 1 (Interfejs komponentu)
// Definiuje kontrakt dla wszystkich producentów płytek —
// zarówno konkretnych implementacji, jak i dekoratorów
public interface BoardProducer {
    void produce(BaseBoard board) throws InterruptedException;
}
// Koniec, Tydzień 3
// Wzorzec Decorator 1