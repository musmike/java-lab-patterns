package pl.lab.factory.components;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Liść komponentu
public class Resistor implements BaseComponent {

    private int resistance;
    private double cost;
    private boolean mounted = false;

    public Resistor(int resistance, double cost) {
        this.resistance = resistance;
        this.cost = cost;
    }

    @Override
    public boolean isMounted() {
        return mounted;
    }

    @Override
    public void mount() {
        mounted = true;
    }

    @Override
    public void print() {
        System.out.println("->[COMPONENT] - Resistor -> " + resistance + "Ω -> Cost: " + cost);
    }

    @Override
    public double mofifyResult(double value) {
        return value / resistance;
    }

    @Override
    public double getCost() {
        return cost;
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 3