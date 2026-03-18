package pl.lab.factory.components;

import pl.lab.factory.Pair;

import java.util.ArrayList;
import java.util.List;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 3
// Kompozyt komponentu
public class ComponentGroup implements BaseComponent {
    private String name;
    private final Pair<Integer, Integer> position;

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
        boolean mounted = true;
        for(BaseComponent c : components) {
            if(!c.isMounted()) {
                mounted = false;
                break;
            }
        }
        return mounted;
    }

    @Override
    public void mount() {
        for(BaseComponent c : components) {
            c.mount();
        }
    }

    @Override
    public void print() {
        System.out.println("[COMPONENT GROUP] - " + name);
        for(BaseComponent c : components) {
            c.print();
        }
        System.out.println("[COMPONENT GROUP] - " + name + " [END]");
    }

    @Override
    public double mofifyResult(double value) {
        double result = value;
        for(BaseComponent c : components) {
            result = c.mofifyResult(result);
        }
        return result;
    }

    @Override
    public double getCost() {
        double cost = 0;
        for(BaseComponent c : components) {
            cost += c.getCost();
        }
        return cost;
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 3