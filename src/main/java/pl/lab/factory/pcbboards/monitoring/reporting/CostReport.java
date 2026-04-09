package pl.lab.factory.pcbboards.monitoring.reporting;

import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.pcbboards.monitoring.CostVisitor;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec template 3 - implementacja i modyfikacja wzorca raportu płytek
public class CostReport extends BoardSummaryTemplate {


    
    @Override
    protected void header(BaseBoard board) {
        super.header(board);
        System.out.println("Reporting board costs:");
    }

    @Override
    protected void body(BaseBoard board) {
        CostVisitor visitor = new CostVisitor();
        board.accept(visitor);
        System.out.println("Total board cost = " + visitor.getCost());
    }

}
//template 3
//koniec tydzien 6