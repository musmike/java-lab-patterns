package pl.lab.factory.components.iteration;

import pl.lab.factory.components.BaseComponent;
import pl.lab.factory.components.ComponentGroup;

import java.util.Iterator;
import java.util.Stack;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Iterator 3 (Implementacja iteratora bazowego)
public class CompositeIterator implements Iterator<BaseComponent> {
    private Stack<Iterator<BaseComponent>> stack = new Stack<>();

    public CompositeIterator(ComponentGroup root) {
        stack.push(root.getComponents().iterator());
    }


    @Override
    public boolean hasNext() {
        while(!stack.empty()){
            Iterator<BaseComponent> iterator = stack.peek();

            if(iterator.hasNext()){
                return true;
            } else stack.pop();
        }
        return false;
    }

    @Override
    public BaseComponent next() {
        Iterator<BaseComponent> iterator = stack.peek();
        BaseComponent component = iterator.next();

        if(component instanceof ComponentGroup group){
            stack.push(group.getComponents().iterator());
        }
        return component;
    }
}

//iterator 3
//koniec tydzien 5