package pl.lab.factory.components;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Liść komponentu
public class Inductor implements BaseComponent {

    private double cost;
    private int inductance;
    private boolean mounted = false;

    public Inductor(int inductance, double cost) {
        this.cost = cost;
        this.inductance = inductance;
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
        System.out.println("->[COMPONENT] - Inductor -> " + inductance + "H -> Cost: " + cost);
    }

    @Override
    public double mofifyResult(double value) {
        return value * inductance;
    }

    @Override
    public double getCost() {
        return cost;
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 3