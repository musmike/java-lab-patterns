package pl.lab.factory.pcbboards.monitoring;

import pl.lab.factory.components.BaseComponent;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec Visitor 3 - konkretna klasa wizytująca
// oblicza koszt produkcji płytki przechowując jego wartość
public class CostVisitor implements BoardVisitor {
    private double cost = 0;
    @Override
    public void visit(BaseBoard baseBoard) {
        // possible addition of future board cost
    }

    @Override
    public void visit(BaseComponent component) {
        cost += component.getCost();
    }

    public double getCost() {
        return cost;
    }

}
//visitor 3
//koniec tydzien 6