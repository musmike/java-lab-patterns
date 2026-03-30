package pl.lab.factory.warehouse.shipping.command;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.warehouse.shipping.ShippingFacade;
import pl.lab.factory.warehouse.shipping.ShippingStrategy;
import pl.lab.factory.warehouse.shipping.visitor.ShippingVisitor;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 2 (Konkretne polecenie)
// Kapsułkuje żądanie wysyłki zamówienia, delegując wykonanie do fasady i umożliwiając cofnięcie operacji.
public class ShipOrderCommand implements ShippingCommand {
    private final ShippingFacade facade;
    private final ShippingStrategy strategy;
    private final ProductionOrder order;
    private boolean executed = false;

    public ShipOrderCommand(ShippingFacade facade, ShippingStrategy strategy, ProductionOrder order){
        this.facade = facade;
        this.strategy = strategy;
        this.order = order;
    }

    @Override
    public void execute(){
        facade.ship(strategy, order);
        executed = true;
        SystemLogger.INSTANCE.logInfo("COMMAND", "Order " + order.getId() + " has been shipped");
    }

    @Override
    public void undo(){
        if(executed){
            facade.cancelShipping(order);
            executed = false;
            SystemLogger.INSTANCE.logInfo("COMMAND", "Undo: Order " + order.getId() + " is back in warehouse");
        }else{
            System.out.println("Command was not executed, nothing to undo");
        }
    }

    // Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
    // Wzorzec Visitor 2 (Concrete Element)
    @Override
    public int accept(ShippingVisitor visitor){
        return visitor.visit(this);
    }
    // Koniec, Tydzień 6
    // Wzorzec Visitor 2

    public ShippingStrategy getStrategy() {
        return strategy;
    }
}
// Koniec, Tydzień 5
// Wzorzec Command 2