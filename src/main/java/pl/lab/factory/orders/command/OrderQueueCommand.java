package pl.lab.factory.orders.command;


// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 1 (Interfejs komendy)
// Wspólny kontrakt dla wszystkich komend kolejki
// execute() wykonuje operację, undo() ją cofa.
public interface OrderQueueCommand {
    void execute();
    void undo();
}
// Koniec, Tydzień 5
// Wzorzec Command 1
