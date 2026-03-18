package pl.lab.factory.components.definitions;

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
        return 0;
    }
}
