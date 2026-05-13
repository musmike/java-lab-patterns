package pl.lab.factory.components;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Komponent podstawowy
//Tydzien 8 - Segregacja interfejsów
//interfejs podstawowy komponentów bez dodatkowych definicji
public interface BaseComponent {
    boolean isMounted();
    void mount();
    void print();
    double getCost();
}
// Koniec, Tydzień 3
// Wzorzec Composite 3
// Koniec, Tydzień 8
// Wzorzec ISP 3