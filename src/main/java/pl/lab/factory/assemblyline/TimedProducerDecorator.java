package pl.lab.factory.assemblyline;

import java.util.concurrent.TimeUnit;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 1 (Konkretny dekorator – pomiar czasu)
// Rozszerza produkcję o mierzenie i logowanie czasu wykonania,
// nie zmieniając klasy SimpleBoardProducer ani żadnego innego producenta.
public class TimedProducerDecorator extends BoardProducerDecorator {
    private final short DEFAULT_TIMEOUT = 5;


    public TimedProducerDecorator(BoardProducer boardProducer) {
        super(boardProducer);
    }

    @Override
    public void produce(BaseBoard board) throws InterruptedException {
        long start = System.currentTimeMillis();
        boardProducer.produce(board);
        TimeUnit.MILLISECONDS.sleep(DEFAULT_TIMEOUT);
        long elapsed = System.currentTimeMillis() - start;
        SystemLogger.INSTANCE.logInfo("TimedProducer",
                "Production time: " + elapsed + " ms | " + board);
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 1