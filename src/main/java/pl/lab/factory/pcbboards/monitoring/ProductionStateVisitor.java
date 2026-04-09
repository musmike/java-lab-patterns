package pl.lab.factory.pcbboards.monitoring;

import pl.lab.factory.components.BaseComponent;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec Visitor 3 - konkretna klasa wizytująca
// sprawdza stan produkcji płytki generując prosty raport
public class ProductionStateVisitor implements BoardVisitor{
    private StringBuilder report = new StringBuilder();
    @Override
    public void visit(BaseBoard board) {
        if (board.is_componentsMounted())
            report.append("[VIS] Components mounted\n");

        if (board.is_circuitsPrinted())
            report.append("[VIS] Circuits printed\n");

        if (board.is_soldered())
            report.append("[VIS] Soldered\n");

        if (!board.isReady())
            report.append("[VIS] Board NOT ready\n");
    }
    @Override
    public void visit(BaseComponent component) {
        // possible addition of per-component report
    }
    public StringBuilder getReport() {
        return report;
    }

    
}
//visitor 3
//koniec tydzien 6