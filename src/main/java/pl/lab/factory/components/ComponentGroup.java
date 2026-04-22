package pl.lab.factory.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.lab.factory.Pair;
import pl.lab.factory.components.iteration.CompositeIterator;
import pl.lab.factory.pcbboards.monitoring.BoardVisitor;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Kompozyt komponentu
public class ComponentGroup implements BaseComponent {
    private String name;
    private final Pair<Integer, Integer> position;

    public List<BaseComponent> getComponents() {
        return components;
    }

    private List<BaseComponent> components = new ArrayList<>();

    public ComponentGroup(String name, Pair<Integer, Integer> position) {
        this.name = name;
        this.position = position;
    }

    public void add(BaseComponent component) {
        components.add(component);
    }
    public void remove(BaseComponent component) {
        components.remove(component);
    }
    public void remove(int idx) {
        components.remove(idx);
    }

    @Override
    public Pair<Integer, Integer> getPos() {
        return position;
    }

    @Override
    public boolean isMounted() {
        Iterator<BaseComponent> it = iterator();
        while(it.hasNext()){
            if(!it.next().isMounted()){
                System.out.println("Iterator found unmounted components!");
                return false;
            }
        }
        System.out.println("Iterator found no unmounted components. Ready for further production!");
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
        System.out.println("[COMPONENT GROUP] - " + name);
        Iterator<BaseComponent> it = iterator();
        while(it.hasNext()){
            it.next().print();
        }
        System.out.println("[COMPONENT GROUP] - " + name + " [END]");
    }

    @Override
    public double mofifyResult(double value) {
        double result = value;
        Iterator<BaseComponent> it = iterator();
        while(it.hasNext()){
            result = it.next().mofifyResult(result);
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
    public void accept(BoardVisitor v) {
        v.visit(this);
        for(BaseComponent c : components){
            c.accept(v);
        }
    }
    
}
// Koniec, Tydzień 3
// Wzorzec Composite 3