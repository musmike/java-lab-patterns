package pl.lab.factory.pcbboards.monitoring;

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
    public void setBoard(BaseBoard board) {
        baseBoard = board;
        try{
            baseBoard.register(this);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
//observer 3
//koniec tydzien 6