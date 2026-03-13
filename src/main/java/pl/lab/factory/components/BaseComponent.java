package pl.lab.factory.components;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// composite 3 komponent  podstawowy
public interface BaseComponent {
    boolean isMounted();
    void mount();
    void print();
    double mofifyResult(double value);
    double getCost();
}
//koniec component 3
//koniec tydzien 3