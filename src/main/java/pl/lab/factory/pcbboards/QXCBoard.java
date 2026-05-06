package pl.lab.factory.pcbboards;

import pl.lab.factory.Pair;
import pl.lab.factory.components.Capacitor;
import pl.lab.factory.components.ComponentDefinitionFactory;
import pl.lab.factory.components.ComponentGroup;
import pl.lab.factory.components.Inductor;
import pl.lab.factory.components.Resistor;

public class QXCBoard extends BaseBoard{

    private final double DEFAULT_VOLTAGE = 5;
    private final BoardMaterials DEFAULT_BOARD_MATERIAL = BoardMaterials.COPPER;
    private final SolderMaterials DEFAULT_SOLDER_MATERIAL = SolderMaterials.WITH_GOLD;
    private final double DEFAULT_THICKNESS = 2.3;
    private final Pair<Integer, Integer> DEFAULT_SIZE = new Pair<>(120, 120);
    private final int DEFAULT_COLOR = 0x007700;
    private final int DEFAULT_LAYERS = 3;

    public QXCBoard() {
        this.workingVoltage = DEFAULT_VOLTAGE;
        this.boardMaterial = DEFAULT_BOARD_MATERIAL;
        this.solderType = DEFAULT_SOLDER_MATERIAL;
        this.thickness = DEFAULT_THICKNESS;
        this.width = DEFAULT_SIZE.first();
        this.length = DEFAULT_SIZE.second();
        this.boardColor = DEFAULT_COLOR;
        this.layers = DEFAULT_LAYERS;
        ComponentGroup mainGroup = new ComponentGroup("SCQ");
        mainGroup.add(new Resistor(ComponentDefinitionFactory.getResistorDefinition(20, 20.2), new Pair<>(1,1)));
        mainGroup.add(new Capacitor(ComponentDefinitionFactory.getCapacitorDefinition(5, 12.2), new Pair<>(3, 1)));
        mainGroup.add(new Resistor(ComponentDefinitionFactory.getResistorDefinition(20, 3.2), new Pair<>(1,1)));
        ComponentGroup subGroup = new ComponentGroup("sub CAWD");
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
