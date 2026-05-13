package pl.lab.factory.components;

import pl.lab.factory.Pair;
import pl.lab.factory.components.definitions.ResistorDefinition;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Liść komponentu
public class Resistor extends AbstractComponent implements SignalProcessorComponent {
    private final ResistorDefinition definition;

    public Resistor(ResistorDefinition definition, Pair<Integer, Integer> position) {
        super(position);
        this.definition = definition;
    }

    @Override
    public void print() {
        System.out.println("->[COMPONENT] - Resistor -> " + definition.getResistance() + "Ω -> Cost: " + definition.getCost());
    }

    @Override
    public double modifyResult(double value) {
        return value / definition.getResistance();
    }

    @Override
    public double getCost() {
        return definition.getCost();
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 3