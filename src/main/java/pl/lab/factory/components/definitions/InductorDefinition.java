package pl.lab.factory.components.definitions;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Flyweight 2 - definicja konkretnego componentu 2/3
public class InductorDefinition implements ComponentDefinition{

    private final int inductance;
    private final double cost;

    public int getInductance() {
        return inductance;
    }

    public InductorDefinition(int inductance, double cost) {
        this.inductance = inductance;
        this.cost = cost;
    }

    @Override
    public char getType() {
        return 'I';
    }

    @Override
    public double getCost() {
        return cost;
    }
}
//flyweight 2
//koniec tydzien 4