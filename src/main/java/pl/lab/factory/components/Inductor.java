package pl.lab.factory.components;

import pl.lab.factory.Pair;
import pl.lab.factory.SystemLogger;
import pl.lab.factory.components.definitions.InductorDefinition;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Liść komponentu
public class Inductor extends AbstractComponent implements SignalProcessorComponent {
    private final InductorDefinition definition;

    public Inductor(InductorDefinition definition, Pair<Integer, Integer> position) {
        super(position);
        this.definition = definition;
    }

    @Override
    public void print() {
        SystemLogger.INSTANCE.log("", "->[COMPONENT] - Inductor -> " + definition.getInductance() + "H -> Cost: " + definition.getCost());
    }

    @Override
    public double modifyResult(double value) {
        return value * definition.getInductance();
    }

    @Override
    public double getCost() {
        return definition.getCost();
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 3