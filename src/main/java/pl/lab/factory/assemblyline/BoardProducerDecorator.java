package pl.lab.factory.assemblyline;

import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 1 (Abstrakcyjny dekorator)
// Przechowuje referencję do opakowanego producenta i deleguje do niego wywołanie.
// Podklasy nadpisują produce(), dodając własne zachowanie przed lub po delegacji.
public abstract class BoardProducerDecorator implements BoardProducer {

    protected final BoardProducer boardProducer;

    public BoardProducerDecorator(BoardProducer boardProducer) {
        this.boardProducer = boardProducer;
    }

    @Override
    public void produce(BaseBoard board) throws InterruptedException {
        this.boardProducer.produce(board);
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 1


