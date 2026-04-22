package pl.lab.factory.components;

import pl.lab.factory.pcbboards.monitoring.BoardVisitor;

//Tydzien 8 - Segregacja interfejsów
//interfejs powstaly przez rozbicie duzego interfejsu bazowego komponentow
public interface VisitableComponent {
    public abstract void accept(BoardVisitor v);
}
// Koniec, Tydzień 8
// Wzorzec ISP 3
