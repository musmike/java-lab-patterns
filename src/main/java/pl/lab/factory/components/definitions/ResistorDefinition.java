package pl.lab.factory.components.definitions;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Flyweight 2 - definicja konkretnego componentu 3/3
public class ResistorDefinition implements ComponentDefinition{
    private final int resistance;
    private final double cost;

    public int getResistance() {
        return resistance;
    }

    public ResistorDefinition(int resistance, double cost) {
        this.resistance = resistance;
        this.cost = cost;
    }

    @Override
    public char getType() {
        return 'R';
    }

    @Override
    public double getCost() {
        return cost;
    }
}
//flyweight 2
//koniec tydzien 4