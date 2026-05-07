package pl.lab.factory.assemblyline.assemblingstrategies;

import pl.lab.factory.pcbboards.BaseBoard;

// tydzien 7 - stosowanie zasad SRP i OCP
// zasada OCP - klasa bazowa otwarta na szerokie poszerzanie poprzez dziedziczenie

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec strategy 3 - interfejs bazowy strategi wytwarzania płytek
public interface AssemblingStrategy {
    void execute(BaseBoard board) throws InterruptedException;
}
//strategy 3
//koniec tydzien 6
//koniec tydzien 7