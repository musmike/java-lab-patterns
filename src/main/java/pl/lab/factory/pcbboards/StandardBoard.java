package pl.lab.factory.pcbboards;

import pl.lab.factory.Pair;
import pl.lab.factory.components.Capacitor;
import pl.lab.factory.components.ComponentDefinitionFactory;
import pl.lab.factory.components.ComponentGroup;
import pl.lab.factory.components.Resistor;

public class StandardBoard extends BaseBoard {

    private final double DEFAULT_VOLTAGE = 3.3;
    private final BoardMaterials DEFAULT_BOARD_MATERIAL = BoardMaterials.FR_4;
    private final SolderMaterials DEFAULT_SOLDER_MATERIAL = SolderMaterials.SOLDER_ALLOY;
    private final double DEFAULT_THICKNESS = 1.6;
    private final Pair<Integer, Integer> DEFAULT_SIZE = new Pair<>(100, 100);
    private final int DEFAULT_COLOR = 0x007700;
    private final int DEFAULT_LAYERS = 2;

    public StandardBoard() {
        this.workingVoltage = DEFAULT_VOLTAGE;
        this.boardMaterial = DEFAULT_BOARD_MATERIAL;
        this.solderType = DEFAULT_SOLDER_MATERIAL;
        this.thickness = DEFAULT_THICKNESS;
        this.width = DEFAULT_SIZE.first();
        this.length = DEFAULT_SIZE.second();
        this.boardColor = DEFAULT_COLOR;
        this.layers = DEFAULT_LAYERS;
        ComponentGroup mainGroup = new ComponentGroup("Main group");
        mainGroup.add(new Resistor(ComponentDefinitionFactory.getResistorDefinition(20, 0.2), new Pair<>(1,1)));
        mainGroup.add(new Capacitor(ComponentDefinitionFactory.getCapacitorDefinition(5, 0.2), new Pair<>(3, 1)));
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