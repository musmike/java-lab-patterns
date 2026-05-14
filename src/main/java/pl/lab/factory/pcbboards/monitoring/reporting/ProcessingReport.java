package pl.lab.factory.pcbboards.monitoring.reporting;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.monitoring.ProductionStateVisitor;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec template 3 - implementacja i modyfikacja wzorca raportu płytek
public class ProcessingReport extends BoardSummaryTemplate {

    @Override
    protected void body(BaseBoard baseBoard) {
        ProductionStateVisitor visitor = new ProductionStateVisitor();
        baseBoard.accept(visitor);
        SystemLogger.INSTANCE.log("", "Board is in the following state:\n" + visitor.getReport());
    }

}
//template 3
//koniec tydzien 6