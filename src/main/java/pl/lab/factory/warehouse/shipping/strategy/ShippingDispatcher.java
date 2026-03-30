package pl.lab.factory.warehouse.shipping.strategy;

import pl.lab.factory.warehouse.shipping.command.ShippingCommand;

import java.util.List;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 2 (Interface for Strategies)
public interface ShippingDispatcher {
    List<ShippingCommand> sortForShipping(List<ShippingCommand> pendingCommands);
}
// Koniec, Tydzień 6
// Wzorzec Strategy 2