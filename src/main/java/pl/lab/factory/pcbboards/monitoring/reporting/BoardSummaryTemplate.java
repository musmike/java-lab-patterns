package pl.lab.factory.pcbboards.monitoring.reporting;

import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec template 3 - wzorzec raportów stanu płytek
public abstract class BoardSummaryTemplate {
    public final void generate(BaseBoard board){
        header(board);
        body(board);
        footer(board);
    }

    protected void header(BaseBoard board){
        System.out.println("--> Board summary start:");
    }
    protected void footer(BaseBoard board){
        System.out.println(":<-- Board summary end");
    }
    protected abstract void body(BaseBoard board);
}
//template 3
//koniec tydzien 6