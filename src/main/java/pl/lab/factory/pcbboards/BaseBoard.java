package pl.lab.factory.pcbboards;

import pl.lab.factory.manufacturing.ManufacturableProduct;

public abstract class BaseBoard implements ManufacturableProduct {

    protected double workingVoltage;
    protected BoardMaterials boardMaterial;
    protected SolderMaterials solderType;
    protected double thickness;
    protected int width;
    protected int length;
    protected int boardColor;
    protected int layers;
    protected boolean _circuitsPrinted = false;
    protected boolean _componentsMounted = false;
    protected boolean _soldered = false;


    public abstract double getWorkingVoltage();
    public abstract BoardMaterials getBoardMaterial();
    public abstract SolderMaterials getSolderType();
    public abstract double getThickness();
    public abstract int getWidth();
    public abstract int getLength();
    public abstract int getBoardColor();
    public abstract int getLayers();

    public abstract boolean isReady();
    public void printCircuits(){
        _circuitsPrinted = true;
    }
    public void mountComponents(){
        _componentsMounted = true;
    }
    public void solder(){
        _soldered = true;
    }
}
