package pl.lab.factory.pcbboards.monitoring.reporting;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec template 3 - wzorzec raportów stanu płytek
public abstract class BoardSummaryTemplate {
    public final void generate(BaseBoard baseBoard){
        header(baseBoard);
        body(baseBoard);
        footer(baseBoard);
    }

    protected void header(BaseBoard baseBoard){
        SystemLogger.INSTANCE.log("", "--> Board summary start:");
    }
    protected void footer(BaseBoard baseBoard){
        SystemLogger.INSTANCE.log("", ":<-- Board summary end");
    }
    protected abstract void body(BaseBoard baseBoard);
}
//template 3
//koniec tydzien 6