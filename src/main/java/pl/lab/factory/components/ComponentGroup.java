package pl.lab.factory.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.components.iteration.CompositeIterator;
import pl.lab.factory.pcbboards.monitoring.BoardVisitor;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Kompozyt komponentu
//Tydzien 8 - Segregacja interfejsów
//klasa korzystajaca z wielu interfejsow rozbitego interfejsu
public class ComponentGroup implements BaseComponent, SignalProcessorComponent, VisitableComponent {
    private String name;

    public List<BaseComponent> getComponents() {
        return components;
    }

    private List<BaseComponent> components = new ArrayList<>();

    public ComponentGroup(String name) {
        this.name = name;
    }

    public void add(BaseComponent component) {
        components.add(component);
    }
    public void remove(BaseComponent component) {
        components.remove(component);
    }
    public void remove(int index) {
        components.remove(index);
    }

    @Override
    public boolean isMounted() {
        Iterator<BaseComponent> it = iterator();
        while(it.hasNext()){
            if(!it.next().isMounted()){
                SystemLogger.INSTANCE.log("", "Iterator found unmounted components!");
                return false;
            }
        }
        SystemLogger.INSTANCE.log("", "Iterator found no unmounted components. Ready for further production!");
        return true;
    }

    @Override
    public void mount() {
        Iterator<BaseComponent> it = iterator();
        while (it.hasNext()){
            it.next().mount();
        }
    }

    @Override
    public void print() {
        SystemLogger.INSTANCE.log("", "[COMPONENT GROUP] - " + name);
        Iterator<BaseComponent> it = iterator();
        while(it.hasNext()){
            it.next().print();
        }
        SystemLogger.INSTANCE.log("[COMPONENT GROUP]", "- " + name + " [END]");
    }

    @Override
    public double modifyResult(double value) {
        double result = value;
        Iterator<BaseComponent> it = iterator();
        while(it.hasNext()){
            if(it.next() instanceof SignalProcessorComponent spc){
                result = spc.modifyResult(result);
            }

        }
        return result;
    }

    @Override
    public double getCost() {
        double cost = 0;
        Iterator<BaseComponent> it = iterator();
        while (it.hasNext()){
            cost += it.next().getCost();
        }
        return cost;
    }

    public Iterator<BaseComponent> iterator(){
        return new CompositeIterator(this);
    }

    @Override
    public void accept(BoardVisitor boardVisitor) {
        boardVisitor.visit(this);
        for(BaseComponent component : components){
            if(component instanceof VisitableComponent visitableComponent){
                visitableComponent.accept(boardVisitor);
            }
        }
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 3
// Koniec, Tydzień 8
// Wzorzec ISP 3