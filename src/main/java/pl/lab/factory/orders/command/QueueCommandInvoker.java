package pl.lab.factory.orders.command;

import pl.lab.factory.SystemLogger;
import java.util.Stack;


// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 1 (Invoker)
// Wykonuje komendy i przechowuje historię na stosie
// undo() zdejmuje ostatnią i cofa jej działanie.
public class QueueCommandInvoker {
    private final Stack<OrderQueueCommand> history = new Stack<>();

    public void executeCommand(OrderQueueCommand command) {
        command.execute();
        history.push(command);
    }

    public void undo() {
        if (!history.isEmpty()) {
            OrderQueueCommand lastCommand = history.pop();
            SystemLogger.INSTANCE.logInfo("CommandManager", "UNDO operation: " + lastCommand.getClass().getSimpleName());
            lastCommand.undo();
        } else {
            SystemLogger.INSTANCE.logInfo("CommandManager", "Nothing to undo!");
        }
    }
}
// Koniec, Tydzień 5
// Wzorzec Command 1