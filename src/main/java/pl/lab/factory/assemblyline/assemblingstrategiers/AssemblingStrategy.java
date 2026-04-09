package pl.lab.factory.assemblyline.assemblingstrategiers;

import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec strategy 3 - interfejs bazowy strategi wytwarzania płytek
public interface AssemblingStrategy {
    void execute(BaseBoard board) throws InterruptedException;
}
//strategy 3
//koniec tydzien 6