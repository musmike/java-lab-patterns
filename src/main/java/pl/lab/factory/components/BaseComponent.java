package pl.lab.factory.components;

import pl.lab.factory.Pair;
import pl.lab.factory.pcbboards.monitoring.BoardVisitor;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Komponent podstawowy
public interface BaseComponent {
    Pair<Integer, Integer> getPos();
    boolean isMounted();
    void mount();
    void print();
    double mofifyResult(double value);
    double getCost();

    public abstract void accept(BoardVisitor v);
}
// Koniec, Tydzień 3
// Wzorzec Composite 3