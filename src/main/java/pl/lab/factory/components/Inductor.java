package pl.lab.factory.components;

import pl.lab.factory.Pair;
import pl.lab.factory.components.definitions.InductorDefinition;
import pl.lab.factory.pcbboards.monitoring.BoardVisitor;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Liść komponentu
public class Inductor implements BaseComponent {

    private InductorDefinition definition;
    private boolean mounted = false;
    private final Pair<Integer, Integer> position;

    public Inductor(InductorDefinition definition, Pair<Integer, Integer> position) {
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
        System.out.println("->[COMPONENT] - Inductor -> " + definition.getInductance() + "H -> Cost: " + definition.getCost());
    }

    @Override
    public double mofifyResult(double value) {
        return value * definition.getInductance();
    }

    @Override
    public double getCost() {
        return definition.getCost();
    }
    @Override
    public int countByType(Class<? extends BaseComponent> type) {
        return type.isInstance(this) ? 1 : 0;
    }
    @Override
    public void accept(BoardVisitor v) {
        v.visit(this);
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 3