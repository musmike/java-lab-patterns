package pl.lab.factory.pcbboards.monitoring;
// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec Visitor 3 - interfest klas wizytowanych
// pozwala określić proces wizytacji klasy
public interface VisitableBoard {
    void accept(BoardVisitor boardVisitor);
}
//visitor 3
//koniec tydzien 6
