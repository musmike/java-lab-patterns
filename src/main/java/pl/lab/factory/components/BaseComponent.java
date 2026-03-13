package pl.lab.factory.components;

//tydzien 3 - composite komponent podstawowy
public interface BaseComponent {
    boolean isMounted();
    void mount();
    void print();
    double mofifyResult(double value);
    double getCost();
}
//koniec tydzien 3