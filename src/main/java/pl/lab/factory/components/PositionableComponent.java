package pl.lab.factory.components;

import pl.lab.factory.Pair;

//Tydzien 8 - Segregacja interfejsów
//interfejs powstaly przez rozbicie duzego interfejsu bazowego komponentow
public interface PositionableComponent {

    Pair<Integer, Integer> getPos();

}
// Koniec, Tydzień 8
// Wzorzec ISP 3
