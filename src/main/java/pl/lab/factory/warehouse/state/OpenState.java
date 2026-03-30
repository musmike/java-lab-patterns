package pl.lab.factory.warehouse.state;

import pl.lab.factory.SystemLogger;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 2 (Concrete State)
public class OpenState implements SectionState{
    @Override
    public boolean canAddItem(){
        return true;
    }

    @Override
    public void printStatus(String sectionName){
        SystemLogger.INSTANCE.logInfo("SECTION STATUS", "Section " + sectionName + " is OPEN. Operations allowed.");
    }
}
// Koniec, Tydzień 6
// Wzorzec State 2