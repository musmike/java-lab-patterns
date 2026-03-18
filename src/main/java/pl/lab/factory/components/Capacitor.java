package pl.lab.factory.components;

import pl.lab.factory.Pair;
import pl.lab.factory.components.definitions.CapacitorDefinition;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Liść komponentu
public class Capacitor implements BaseComponent {

    private CapacitorDefinition definition;
    private boolean mounted = false;
    private final Pair<Integer, Integer> position;

    public Capacitor(CapacitorDefinition definition, Pair<Integer, Integer> position) {
        this.definition = definition;
        this.position = position;
    }

    @Override
    public Pair<Integer, Integer> getPos() {
        return position;
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
        System.out.println("->[COMPONENT] - Capacitor -> " + definition.getCapacitance() + "F -> Cost: " + definition.getCost());
    }

    @Override
    public double mofifyResult(double value) {
        return value + 5 > definition.getCapacitance() ? value - definition.getCapacitance() : value + definition.getCapacitance();
    }

    @Override
    public double getCost() {
        return definition.getCost();
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 3