package pl.lab.factory.pcbboards;

import pl.lab.factory.components.BaseComponent;
import pl.lab.factory.manufacturing.ManufacturableProduct;

import java.util.ArrayList;

public abstract class BaseBoard implements ManufacturableProduct, Cloneable {

    protected ArrayList<BaseComponent> components = new ArrayList<>();
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

    public boolean is_circuitsPrinted() {
        return _circuitsPrinted;
    }

    public boolean is_componentsMounted() {
        return _componentsMounted;
    }

    public boolean is_soldered() {
        return _soldered;
    }

    public abstract boolean isReady();
    public void printCircuits(){
        _circuitsPrinted = true;
    }
    public void mountComponents(){
        for (BaseComponent c : components){
            c.mount();
        }
        _componentsMounted = true;
    }
    public double getComponentCost(){
        double cost = 0;
        for(BaseComponent c : components){
            cost += c.getCost();
        }
        return cost;
    }
    public double getValue(double value){
        for(BaseComponent c : components){
            value = c.mofifyResult(value);
        }
        return value;
    }
    public void getComponentInfo(){
        for(BaseComponent c : components){
            c.print();
        }
    }
    public void solder(){
        _soldered = true;
    }

    public BaseBoard deepCopy() {
        try {
            return (BaseBoard) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
