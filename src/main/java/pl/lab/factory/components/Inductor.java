package pl.lab.factory.components;

import pl.lab.factory.Pair;
import pl.lab.factory.components.definitions.InductorDefinition;
import pl.lab.factory.pcbboards.monitoring.BoardVisitor;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Liść komponentu
//Tydzien 8 - Segregacja interfejsów
//klasa korzystajaca z wielu interfejsow rozbitego interfejsu
public class Inductor implements BaseComponent, PositionableComponent, SignalProcessorComponent, VisitableComponent {

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
    public void accept(BoardVisitor v) {
        v.visit(this);
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 3
// Koniec, Tydzień 8
// Wzorzec ISP 3