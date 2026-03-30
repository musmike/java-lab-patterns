package pl.lab.factory.warehouse.shipping.strategy;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.warehouse.shipping.command.ShippingCommand;
import pl.lab.factory.warehouse.shipping.visitor.PriorityCalculator;
import pl.lab.factory.warehouse.shipping.visitor.ShippingVisitor;

import java.util.List;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 2 (Concrete Strategy)
public class PriorityFirstDispatcher implements ShippingDispatcher{
    private final ShippingVisitor priorityVisitor = new PriorityCalculator();
    @Override
    public List<ShippingCommand> sortForShipping(List<ShippingCommand> pending){
        SystemLogger.INSTANCE.logInfo("SHIPPING STRATEGY", "Applying Priority-First strategy...");
        pending.sort((c1, c2) ->{
           int p1 = c1.accept(priorityVisitor);
           int p2 = c2.accept(priorityVisitor);

           return Integer.compare(p2, p1);
        });
        return pending;
    }
}
// Koniec, Tydzień 6
// Wzorzec Strategy 2