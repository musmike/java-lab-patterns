package pl.lab.factory.manufacturing;

import pl.lab.factory.assemblyline.BoardProducer;
import pl.lab.factory.assemblyline.QualityControlDecorator;
import pl.lab.factory.assemblyline.SimpleBoardProducer;
import pl.lab.factory.assemblyline.TimedProducerDecorator;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.ProductionOrder;


// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada odwracania zależności (DIP) 1 - wersja z klasą abstrakcyjną (Klasa pochodna)
// BaseBoardOrderProcessor jest klasą pochodną, która implementuje abstrakcyjną metodę z OrderProcessor.
public class BaseBoardOrderProcessor extends OrderProcessor {

    private final BoardProducer producer;

    // Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
    // Wzorzec Decorator 1 (Użycie łańcucha dekoratorów)
    // Zamiast bezpośredniego wywoływania CircuitPrinter i Solderer,
    // budowany jest łańcuch dekoratorów:
    // SimpleBoardProducer  →  TimedProducerDecorator  →  QualityControlDecorator
    // Każdy dekorator dodaje nową odpowiedzialność bez modyfikowania poprzednich warstw.
    public BaseBoardOrderProcessor() {
        this.producer = new QualityControlDecorator(new TimedProducerDecorator(new SimpleBoardProducer()));
    }
    // Koniec, Tydzień 3
    // Wzorzec Decorator 1


    @Override
    protected void produceUnit(ProductionOrder order) throws InterruptedException {
        BaseBoardProductionOrder bpo = (BaseBoardProductionOrder) order;
        producer.produce(bpo.getBoard());
    }
}
// Koniec, Tydzień 8
// Zasada odwracania zależności (DIP) 1 - wersja z klasą abstrakcyjną

