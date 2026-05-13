package pl.lab.factory.components;

import pl.lab.factory.Pair;
import pl.lab.factory.pcbboards.monitoring.BoardVisitor;

//Tydzien 8 - Segregacja interfejsów
//klasa korzystajaca z wielu interfejsow rozbitego interfejsu
public abstract class AbstractComponent implements BaseComponent, PositionableComponent, VisitableComponent {
    protected boolean mounted = false;
    protected final Pair<Integer, Integer> position;

    public AbstractComponent(Pair<Integer, Integer> position) {
        this.position = position;
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
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
    public void accept(BoardVisitor boardVisitor) {
        boardVisitor.visit(this);
    }
}
// Koniec, Tydzień 8
// Wzorzec ISP 3