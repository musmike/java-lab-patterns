package pl.lab.factory.pcbboards;

import java.util.ArrayList;
import java.util.List;

import pl.lab.factory.components.BaseComponent;
import pl.lab.factory.components.SignalProcessorComponent;
import pl.lab.factory.components.VisitableComponent;
import pl.lab.factory.manufacturing.ManufacturableProduct;
import pl.lab.factory.pcbboards.monitoring.BoardObserver;
import pl.lab.factory.pcbboards.monitoring.BoardVisitor;
import pl.lab.factory.pcbboards.monitoring.ObservableBoard;
import pl.lab.factory.pcbboards.monitoring.VisitableBoard;

public abstract class BaseBoard implements ManufacturableProduct, Cloneable, ObservableBoard, VisitableBoard {
    protected List<BoardObserver> observers = new ArrayList<>();
    protected boolean[] changed = {false, false, false};
    private static final short MOUNTING_CHANGE = 0;
    private static final short PRINTING_CHANGE = 1;
    private static final short SOLDERING_CHANGE = 2;

    protected ArrayList<BaseComponent> components = new ArrayList<>();
    protected double workingVoltage;
    protected BoardMaterials boardMaterial;
    protected SolderMaterials solderType;
    protected double thickness;
    protected int width;
    protected int length;
    protected int boardColor;
    protected int layers;
    protected boolean circuitsPrinted = false;
    protected boolean componentsMounted = false;
    protected boolean soldered = false;

    public double getWorkingVoltage() { return workingVoltage; }
    public BoardMaterials getBoardMaterial() { return boardMaterial; }
    public SolderMaterials getSolderType() { return solderType; }
    public double getThickness() { return thickness; }
    public int getWidth() { return width; }
    public int getLength() { return length; }
    public int getBoardColor() { return boardColor; }
    public int getLayers() { return layers; }

    public boolean isCircuitsPrinted() {
        return circuitsPrinted;
    }

    public boolean isComponentsMounted() {
        return componentsMounted;
    }

    public boolean isSoldered() {
        return soldered;
    }

    public boolean isReady() { 
        return circuitsPrinted && soldered; 
    }

    protected void markAsChangedAndNotify(int changeIndex) {
        changed[changeIndex] = true;
        notifyObservers();
    }

    public void printCircuits(){
        circuitsPrinted = true;
        markAsChangedAndNotify(PRINTING_CHANGE);
    }

    public void mountComponents(){
        for (BaseComponent component : components){
            component.mount();
        }
        componentsMounted = true;
        markAsChangedAndNotify(MOUNTING_CHANGE);
    }

    public double getComponentCost(){
        double cost = 0;
        for(BaseComponent component : components){
            cost += component.getCost();
        }
        return cost;
    }

    public double getValue(double value){
        for(BaseComponent component : components){
            if(component instanceof SignalProcessorComponent spc){
                value = spc.modifyResult(value);
            }
        }
        return value;
    }

    public void printComponentInfo(){
        for (BaseComponent component : components) {
            component.print();
        }
    }

    public void solder(){
        soldered = true;
        markAsChangedAndNotify(SOLDERING_CHANGE);
    }

    public BaseBoard deepCopy() {
        try {
            return (BaseBoard) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public void register(BoardObserver boardObserver) throws RuntimeException{
        if(boardObserver == null)
            throw new RuntimeException("Observer not found!");

        if(!observers.contains(boardObserver))
            observers.add(boardObserver);

    }

    @Override
    public void unregister(BoardObserver boardObserver) {
        observers.remove(boardObserver);
    }

    @Override
    public void notifyObservers() {
        List<BoardObserver> observersList;
        if(!changed[MOUNTING_CHANGE] && !changed[PRINTING_CHANGE] && !changed[SOLDERING_CHANGE])
            return;
        observersList = new ArrayList<>(this.observers);
        observersList.forEach(observer -> {
            observer.update();
        });
        changed = new boolean[]{false, false, false};
    }

    @Override
    public String getUpdate(BoardObserver boardObserver) {
        if(!changed[MOUNTING_CHANGE] && !changed[PRINTING_CHANGE] && !changed[SOLDERING_CHANGE])
            return "Nothing changed!";

        return "" +
        (changed[MOUNTING_CHANGE] ? "[OBS] Board was mounted!" : "") +
        (changed[PRINTING_CHANGE] ? "[OBS] Board was printed!" : "") +
        (changed[SOLDERING_CHANGE] ? "[OBS] Board was soldered!" : "")
        ;
    }

    @Override
    public void accept(BoardVisitor boardVisitor) {
        boardVisitor.visit(this);
        for (BaseComponent component : components) {
            if (component instanceof VisitableComponent visitableComponent) {
                visitableComponent.accept(boardVisitor);
            }
        }
    }
}