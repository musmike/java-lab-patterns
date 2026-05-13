package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.assemblyline.assemblingstrategies.AssemblingStrategy;
import pl.lab.factory.pcbboards.BaseBoard;

//Tydzien 8 - Podstawienie Liskov
//klasa abstrakcyjna ktorej klasy pochodne sa uzywalne w dowolnym miejscu gdzie jest ona wymagana
public abstract class AssemblyStep {
    protected AssemblingStrategy strategy;

    public AssemblyStep(AssemblingStrategy strategy) {
        this.strategy = strategy;
    }

    public abstract void perform(BaseBoard baseBoard) throws InterruptedException;
}
//LSP 3
//koniec tydzien 8