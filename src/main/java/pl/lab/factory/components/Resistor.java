package pl.lab.factory.components;

public class Resistor implements BaseComponent {

    private int resistance;
    private double cost;
    private boolean mounted = false;

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
        return value/resistance;
    }

    @Override
    public double getCost() {
        return cost;
    }
}
