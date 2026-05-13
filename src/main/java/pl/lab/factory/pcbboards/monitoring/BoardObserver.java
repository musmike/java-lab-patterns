package pl.lab.factory.pcbboards.monitoring;

import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec observer 3 - interfejs obserwatora
public interface BoardObserver {
    public void setBoard(BaseBoard baseBoard);
    public void update();
}
//observer 3
//koniec tydzien 6