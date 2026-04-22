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
        changed[1] = true;
        notifyObservers();
    }
    public void mountComponents(){
        for (BaseComponent c : components){
            c.mount();
        }
        _componentsMounted = true;
        changed[0] = true;
        notifyObservers();
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
            if(c instanceof SignalProcessorComponent spc){
                value = spc.mofifyResult(value);
            }
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
        changed[2] = true;
        notifyObservers();
    }

    public BaseBoard deepCopy() {
        try {
            return (BaseBoard) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public void register(BoardObserver o) {
        if(o == null)
            System.out.println("Observer not found!");

        if(!observers.contains(o))
            observers.add(o);

    }

    @Override
    public void unregister(BoardObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        List<BoardObserver> observersList;
        if(!changed[0] && !changed[1] && !changed[2])
            return;
        observersList = new ArrayList<>(this.observers);
        observersList.forEach(observer -> {
            observer.update();
        });
        changed = new boolean[]{false, false, false};
    }

    @Override
    public String getUpdate(BoardObserver o) {
        if(!changed[0] && !changed[1] && !changed[2])
            return "Nothing changed!";
        
        return "" + 
        (changed[0] ? "[OBS] Board was mounted!" : "") + 
        (changed[1] ? "[OBS] Board was printed!" : "") + 
        (changed[2] ? "[OBS] Board was soldered!" : "")
        ;
    }

    @Override
    public void accept(BoardVisitor v) {
        v.visit(this);
        for(BaseComponent c : components){
            if(c instanceof VisitableComponent vc){
                vc.accept(v);
            }
        }
    }

}
