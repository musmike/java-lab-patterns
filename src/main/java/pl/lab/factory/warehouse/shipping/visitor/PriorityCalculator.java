package pl.lab.factory.warehouse.shipping.visitor;

import pl.lab.factory.warehouse.shipping.PriorityShipping;
import pl.lab.factory.warehouse.shipping.command.ShipOrderCommand;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 2 (Concrete Visitor)
public class PriorityCalculator implements CommandVisitor {
    private static final int HIGH_PRIORITY_SCORE = 10;
    
    @Override
    public int visit(ShipOrderCommand command){
        if(command.getStrategy() instanceof PriorityShipping){
            return HIGH_PRIORITY_SCORE;
        }
        return 0;
    }
}
// Koniec, Tydzień 6
// Wzorzec Visitor 2