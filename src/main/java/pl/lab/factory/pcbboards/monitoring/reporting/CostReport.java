package pl.lab.factory.pcbboards.monitoring.reporting;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.monitoring.CostVisitor;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec template 3 - implementacja i modyfikacja wzorca raportu płytek
public class CostReport extends BoardSummaryTemplate {


    
    @Override
    protected void header(BaseBoard baseBoard) {
        super.header(baseBoard);
        SystemLogger.INSTANCE.log("", "Reporting board costs:");
    }

    @Override
    protected void body(BaseBoard baseBoard) {
        CostVisitor visitor = new CostVisitor();
        baseBoard.accept(visitor);
        SystemLogger.INSTANCE.log("", "Total board cost = " + visitor.getCost());
    }

}
//template 3
//koniec tydzien 6