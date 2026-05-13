package pl.lab.factory.components;

import pl.lab.factory.Pair;
import pl.lab.factory.components.definitions.CapacitorDefinition;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Liść komponentu
public class Capacitor extends AbstractComponent implements SignalProcessorComponent {
    private static final short VOLTAGE_COMPARATOR = 5;
    private final CapacitorDefinition definition;

    public Capacitor(CapacitorDefinition definition, Pair<Integer, Integer> position) {
        super(position);
        this.definition = definition;
    }

    @Override
    public void print() {
        System.out.println("->[COMPONENT] - Capacitor -> " + definition.getCapacitance() + "F -> Cost: " + definition.getCost());
    }

    @Override
    public double modifyResult(double value) {
        return value + VOLTAGE_COMPARATOR > definition.getCapacitance()
                ? value - definition.getCapacitance()
                : value + definition.getCapacitance();
    }

    @Override
    public double getCost() {
        return definition.getCost();
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 3