package pl.lab.factory.components;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Komponent podstawowy
public interface BaseComponent {
    boolean isMounted();
    void mount();
    void print();
    double mofifyResult(double value);
    double getCost();
}
// Koniec, Tydzień 3
// Wzorzec Composite 3