package pl.lab.factory.assemblyline;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Decorator 1 (Konkretny dekorator – kontrola jakości)
// Po zakończeniu produkcji sprawdza, czy płytka jest gotowa (isReady()). W przypadku niezgodności
// rzuca wyjątek i loguje błąd — bez modyfikacji żadnej istniejącej klasy produkcyjnej.
public class QualityControlDecorator extends BoardProducerDecorator {

    public QualityControlDecorator(BoardProducer boardProducer) {
        super(boardProducer);
    }

    @Override
    public void produce(BaseBoard baseBoard) throws InterruptedException {
        boardProducer.produce(baseBoard);

        if (!baseBoard.isReady()) {
            SystemLogger.INSTANCE.logError("QualityControl",
                    "REJECTED: board failed quality control | " + baseBoard);
            throw new RuntimeException("Board couldn't be manufactured!");
        }

        SystemLogger.INSTANCE.logInfo("QualityControl",
                "OK: board passed quality control | " + baseBoard);
    }
}
// Koniec, Tydzień 3
// Wzorzec Decorator 1