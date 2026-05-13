package pl.lab.factory.pcbboards.monitoring;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.monitoring.reporting.ProcessingReport;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec observer 3 - konkretny obserwator
// obserwator otrzymujący zmiany przy każdym etapie produkcji płytki
public class ProcessObserver implements BoardObserver {
    ProcessingReport reportGenerator = new ProcessingReport();
    BaseBoard baseBoard;

    @Override
    public void update() {
        reportGenerator.generate(baseBoard);
    }

    @Override
    public void setBoard(BaseBoard baseBoard) {
        this.baseBoard = baseBoard;
        registerAsObserver();
    }

    private void registerAsObserver() {
        try {
            this.baseBoard.register(this);
        } catch (Exception e) {
            SystemLogger.INSTANCE.logError("ProcessObserver", e.getMessage());
            throw new IllegalStateException("Failed to register observer on board.", e);
        }
    }

}
//observer 3
//koniec tydzien 6