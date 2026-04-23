package pl.lab.factory.warehouse.shipping.strategy;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.warehouse.shipping.command.ShippingCommand;

import java.util.List;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 2 (Concrete Strategy)

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada podstawienia Liskov (LSP)
public class FifoDispatcher implements ShippingDispatcher{
    @Override
    public List<ShippingCommand> sortForShipping(List<ShippingCommand> pending){
        SystemLogger.INSTANCE.logInfo("SHIPPING STRATEGY", "Applying FIFO strategy (Standard sequence)...");
        return pending;
    }
}
// Koniec, Tydzień 8
// Zasada podstawienia Liskov (LSP)

// Koniec, Tydzień 6
// Wzorzec Strategy 2