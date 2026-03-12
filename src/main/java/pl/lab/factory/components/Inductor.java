package pl.lab.factory.components;

public class Inductor implements BaseComponent{

    private double cost;
    private int inductance;
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
