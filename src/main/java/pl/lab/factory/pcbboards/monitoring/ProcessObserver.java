package pl.lab.factory.pcbboards.monitoring;

import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.monitoring.reporting.ProcessingReport;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec observer 3 - konkretny obserwator
// obserwator otrzymujący zmiany przy każdym etapie produkcji płytki
public class ProcessObserver implements BoardObserver {
    ProcessingReport reportGen = new ProcessingReport();
    BaseBoard b;

    @Override
    public void update() {
        reportGen.generate(b);
    }

    @Override
    public void setBoard(BaseBoard board) {
        b = board;
        b.register(this);
    }

}
//observer 3
//koniec tydzien 6