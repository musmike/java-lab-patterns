package pl.lab.factory.components;

import pl.lab.factory.SystemLogger;

public class Capacitor implements BaseComponent {

    private double cost;
    private int capacitance;
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
        System.out.println("->[COMPONENT] - Capacitor -> " + capacitance + "F -> Cost: " + cost);
    }

    @Override
    public double mofifyResult(double value) {
        return value + 5 > capacitance ? value - capacitance : value + capacitance;
    }

    @Override
    public double getCost() {
        return cost;
    }
}
