package pl.lab.factory.pcbboards.monitoring;

import pl.lab.factory.components.BaseComponent;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec Visitor 3 - interfejs klas wizytujących
// określa mechanizm odwiedzania płytki
public interface BoardVisitor {
    void visit(BaseBoard board);
    void visit(BaseComponent component);
}
//visitor 3
//koniec tydzien 6