package pl.lab.factory.pcbboards;

import pl.lab.factory.Pair;
import pl.lab.factory.components.Capacitor;
import pl.lab.factory.components.ComponentDefinitionFactory;
import pl.lab.factory.components.ComponentGroup;
import pl.lab.factory.components.Inductor;
import pl.lab.factory.components.Resistor;

public class QXCBoard extends BaseBoard{

    public QXCBoard() {
        this.workingVoltage = 5;
        this.boardMaterial = BoardMaterials.COPPER;
        this.solderType = SolderMaterials.WITH_GOLD;
        this.thickness = 2.3;
        this.width = 120;
        this.length = 120;
        this.boardColor = 0x007700;
        this.layers = 3;
        ComponentGroup mainGroup = new ComponentGroup("SCQ", new Pair<>(1,1));
        mainGroup.add(new Resistor(ComponentDefinitionFactory.getResistorDefinition(20, 20.2), new Pair<>(1,1)));
        mainGroup.add(new Capacitor(ComponentDefinitionFactory.getCapacitorDefinition(5, 12.2), new Pair<>(3, 1)));
        mainGroup.add(new Resistor(ComponentDefinitionFactory.getResistorDefinition(20, 3.2), new Pair<>(1,1)));
        ComponentGroup subGroup = new ComponentGroup("sub CAWD", new Pair<>(20,20));
        subGroup.add(new Inductor(ComponentDefinitionFactory.getInductorDefinition(15, 4.5), new Pair<>(21,20)));
        mainGroup.add(subGroup);
        this.components.add(mainGroup);
    }

    @Override
    public double getWorkingVoltage() { return workingVoltage; }

    @Override
    public BoardMaterials getBoardMaterial() { return boardMaterial; }

    @Override
    public SolderMaterials getSolderType() { return solderType; }

    @Override
    public double getThickness() { return thickness; }

    @Override
    public int getWidth() { return width; }

    @Override
    public int getLength() { return length; }

    @Override
    public int getBoardColor() { return boardColor; }

    @Override
    public int getLayers() { return layers; }

    @Override
    public boolean isReady() { return _circuitsPrinted && _soldered; }

    @Override
    public String toString() {
        return "StandardBoard{" + width + "x" + length + "mm, " + layers + " layers, " + boardMaterial + "}";
    }
}
