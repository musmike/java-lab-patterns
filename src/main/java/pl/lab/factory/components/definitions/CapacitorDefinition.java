package pl.lab.factory.components.definitions;

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
