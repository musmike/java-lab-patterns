package pl.lab.factory.pcbboards;

import pl.lab.factory.components.Capacitor;
import pl.lab.factory.components.ComponentGroup;
import pl.lab.factory.components.Resistor;

public class StandardBoard extends BaseBoard {

    public StandardBoard() {
        this.workingVoltage = 3.3;
        this.boardMaterial = BoardMaterials.FR_4;
        this.solderType = SolderMaterials.SOLDER_ALLOY;
        this.thickness = 1.6;
        this.width = 100;
        this.length = 100;
        this.boardColor = 0x007700;
        this.layers = 2;
        ComponentGroup mainGroup = new ComponentGroup("Main group");
        mainGroup.add(new Resistor(20, 0.2));
        mainGroup.add(new Capacitor(5, 0.2));
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