package pl.lab.factory.warehouse.shipping.command;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 2 (Interfejs polecenia)
// Definiuje wspólny interfejs dla wszystkich konkretnych poleceń w systemie wysyłki.
public interface ShippingCommand {
    void execute();
    void undo();
}
// Koniec, Tydzień 5
// Wzorzec Command 2