package pl.lab.factory.pcbboards.monitoring;

import pl.lab.factory.components.*;
import pl.lab.factory.pcbboards.BaseBoard;

public class CountingVisitor implements BoardVisitor{

    public int getCapacitors() {
        return capacitors;
    }

    public int getResistors() {
        return resistors;
    }

    public int getInductors() {
        return inductors;
    }

    public int getGroup() {
        return group;
    }

    private int capacitors = 0;
    private int resistors = 0;
    private int inductors = 0;
    private int group = 0;

    @Override
    public void visit(BaseBoard board) {
    }

    @Override
    public void visit(BaseComponent component) {
        if(component instanceof Capacitor){
            capacitors++;
        } else if (component instanceof Resistor){
            resistors++;
        } else if (component instanceof Inductor){
            inductors++;
        } else if (component instanceof ComponentGroup){
            group++;
        }
    }
}
