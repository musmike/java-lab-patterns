package pl.lab.factory.components.definitions;

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
        return 0;
    }
}
