package pl.lab.factory.components.definitions;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Flyweight 2 - definicja konkretnego componentu 1/3
public class CapacitorDefinition implements ComponentDefinition{

    private final int capacitance;
    private final double cost;

    public CapacitorDefinition(int capacitance, double cost) {
        this.capacitance = capacitance;
        this.cost = cost;
    }

    public int getCapacitance() {
        return capacitance;
    }

    @Override
    public char getType() {
        return 'C';
    }

    @Override
    public double getCost() {
        return 0;
    }
}
//flyweight 2
//koniec tydzien 4