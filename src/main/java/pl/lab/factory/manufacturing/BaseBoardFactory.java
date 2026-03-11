package pl.lab.factory.manufacturing;

import pl.lab.factory.assemblyline.*;
import pl.lab.factory.pcbboards.BaseBoard;

public abstract class BaseBoardFactory {

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Factory 1 (Factory Method)
    // Abstrakcyjna metoda fabryczna — podklasy decydują jaki konkretny typ
    // BaseBoard zostanie utworzony; BaseBoardFactory nie zna szczegółów implementacji
    public abstract BaseBoard createBoard();
    // Koniec, Tydzień 2
    // Wzorzec Factory 1


    // Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
    // Wzorzec Decorator 1 (Użycie łańcucha dekoratorów)
    // Zamiast bezpośredniego wywoływania CircuitPrinter i Solderer,
    // budowany jest łańcuch dekoratorów:
    //   StandardBoardProducer  →  TimedProducerDecorator  →  QualityControlDecorator
    // Każdy dekorator dodaje nową odpowiedzialność bez modyfikowania poprzednich warstw.
    public void produceBoard(BaseBoard board) throws InterruptedException {
        BoardProducer producer = new QualityControlDecorator(new TimedProducerDecorator(new SimpleBoardProducer()));
        producer.produce(board);
    }
    // Koniec, Tydzień 3
    // Wzorzec Decorator 1
}