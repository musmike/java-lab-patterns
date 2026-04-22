package pl.lab.factory.components;

import pl.lab.factory.Pair;
import pl.lab.factory.components.definitions.ResistorDefinition;
import pl.lab.factory.pcbboards.monitoring.BoardVisitor;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Liść komponentu
//Tydzien 8 - Segregacja interfejsów
//klasa korzystajaca z wielu interfejsow rozbitego interfejsu
public class Resistor implements BaseComponent, PositionableComponent, SignalProcessorComponent, VisitableComponent {

    private ResistorDefinition definition;
    private boolean mounted = false;
    private final Pair<Integer, Integer> position;

    public Resistor(ResistorDefinition definition, Pair<Integer, Integer> position) {
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
        System.out.println("->[COMPONENT] - Resistor -> " + definition.getResistance() + "Ω -> Cost: " + definition.getCost());
    }

    @Override
    public double mofifyResult(double value) {
        return value / definition.getResistance();
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