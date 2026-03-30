package pl.lab.factory.warehouse.shipping.visitor;

import pl.lab.factory.warehouse.shipping.PriorityShipping;
import pl.lab.factory.warehouse.shipping.command.ShipOrderCommand;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 2 (Concrete Visitor)
public class PriorityCalculator implements CommandVisitor {
    @Override
    public int visit(ShipOrderCommand command){
        if(command.getStrategy() instanceof PriorityShipping){
            return 10;
        }
        return 0;
    }
}
// Koniec, Tydzień 6
// Wzorzec Visitor 2