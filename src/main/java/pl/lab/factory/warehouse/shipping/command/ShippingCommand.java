package pl.lab.factory.warehouse.shipping.command;

import pl.lab.factory.warehouse.shipping.visitor.CommandVisitor;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 2 (Interfejs polecenia)
// Definiuje wspólny interfejs dla wszystkich konkretnych poleceń w systemie wysyłki.
public interface ShippingCommand {
    void execute();
    void undo();

    // Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
    // Wzorzec Visitor 2 (Interface Element)
    int accept(CommandVisitor visitor);
    // Koniec, Tydzień 6
    // Wzorzec Visitor 2
}
// Koniec, Tydzień 5
// Wzorzec Command 2