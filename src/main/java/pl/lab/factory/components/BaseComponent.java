package pl.lab.factory.components;

public interface BaseComponent {
    boolean isMounted();
    void mount();
    void print();
    double mofifyResult(double value);
    double getCost();
}
