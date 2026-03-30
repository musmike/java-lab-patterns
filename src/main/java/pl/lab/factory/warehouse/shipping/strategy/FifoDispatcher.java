package pl.lab.factory.warehouse.shipping.strategy;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.warehouse.shipping.command.ShippingCommand;

import java.util.List;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 2 (Concrete Strategy)
public class FifoDispatcher implements ShippingDispatcher{
    @Override
    public List<ShippingCommand> sortForShipping(List<ShippingCommand> pending){
        SystemLogger.INSTANCE.logInfo("SHIPPING STRATEGY", "Applying FIFO strategy (Standard sequence)...");
        return pending;
    }
}
// Koniec, Tydzień 6
// Wzorzec Strategy 2