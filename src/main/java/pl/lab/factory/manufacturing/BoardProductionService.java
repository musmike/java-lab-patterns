package pl.lab.factory.manufacturing;

import pl.lab.factory.assemblyline.BoardProducer;
import pl.lab.factory.assemblyline.QualityControlDecorator;
import pl.lab.factory.assemblyline.SimpleBoardProducer;
import pl.lab.factory.assemblyline.TimedProducerDecorator;
import pl.lab.factory.pcbboards.BaseBoard;


public class BoardProductionService {

    private final BoardProducer producer;

    // Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
    // Wzorzec Decorator 1 (Użycie łańcucha dekoratorów)
    // Zamiast bezpośredniego wywoływania CircuitPrinter i Solderer,
    // budowany jest łańcuch dekoratorów:
    // SimpleBoardProducer  →  TimedProducerDecorator  →  QualityControlDecorator
    // Każdy dekorator dodaje nową odpowiedzialność bez modyfikowania poprzednich warstw.
    public BoardProductionService() {
        this.producer = new QualityControlDecorator(new TimedProducerDecorator(new SimpleBoardProducer()));
    }
    // Koniec, Tydzień 3
    // Wzorzec Decorator 1

    public void produce(BaseBoard board) throws InterruptedException {
        producer.produce(board);
    }
}

