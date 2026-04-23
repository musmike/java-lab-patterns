package pl.lab.factory.warehouse.shipping.strategy;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.warehouse.shipping.command.ShippingCommand;
import pl.lab.factory.warehouse.shipping.visitor.PriorityCalculator;
import pl.lab.factory.warehouse.shipping.visitor.CommandVisitor;

import java.util.List;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 2 (Concrete Strategy)

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada podstawienia Liskov (LSP)
public class PriorityFirstDispatcher implements ShippingDispatcher{
    private final CommandVisitor priorityVisitor = new PriorityCalculator();
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
// Koniec, Tydzień 8
// Zasada podstawienia Liskov (LSP)

// Koniec, Tydzień 6
// Wzorzec Strategy 2