package pl.lab.factory.warehouse.shipping.strategy;

import pl.lab.factory.warehouse.shipping.command.ShippingCommand;

import java.util.List;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Strategy 2 (Interface for Strategies)

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada podstawienia Liskov (LSP)
public interface ShippingDispatcher {
    List<ShippingCommand> sortForShipping(List<ShippingCommand> pendingCommands);
}
// Koniec, Tydzień 8
// Zasada podstawienia Liskov (LSP)

// Koniec, Tydzień 6
// Wzorzec Strategy 2