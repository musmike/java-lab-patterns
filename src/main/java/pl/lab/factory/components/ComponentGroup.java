package pl.lab.factory.components;

import java.util.ArrayList;
import java.util.List;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
//kompozyt komponentu 3
public class ComponentGroup implements BaseComponent{

    String name;
    private List<BaseComponent> components = new ArrayList<>();

    public ComponentGroup(String name) {
        this.name = name;
    }

    public void add(BaseComponent component){
        components.add(component);
    }
    public void remove(BaseComponent component){
        components.remove(component);
    }
    public void remove(int idx){
        components.remove(idx);
    }

    @Override
    public boolean isMounted() {
        boolean mounted = true;
        for(BaseComponent c : components){
            if(!c.isMounted()){
                mounted = false;
                break;
            }
        }
        return mounted;
    }

    @Override
    public void mount() {
        for(BaseComponent c : components){
            c.mount();
        }
    }

    @Override
    public void print() {
        System.out.println("[COMPONENT GROUP] - " + name);
        for(BaseComponent c : components){
            c.print();
        }
        System.out.println("[COMPONENT GROUP] - " + name + " [END]");
    }

    @Override
    public double mofifyResult(double value) {
        double result = value;
        for(BaseComponent c : components){
            result = c.mofifyResult(result);
        }
        return result;
    }

    @Override
    public double getCost() {
        double cost = 0;
        for(BaseComponent c : components){
            cost += c.getCost();
        }
        return cost;
    }
}
//koniec component 3
//koniec tydzien 3