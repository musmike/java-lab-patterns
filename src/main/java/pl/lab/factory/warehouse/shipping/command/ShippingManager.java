package pl.lab.factory.warehouse.shipping.command;

import pl.lab.factory.SystemLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 2 (Invoker - Wywołujący)
// Przechowuje historię poleceń i zarządza ich wykonywaniem oraz wycofywaniem (operacja undo).
public class ShippingManager {
    private final List<ShippingCommand> commandHistory = new ArrayList<>();
    private final Stack<ShippingCommand> undoStack = new Stack<>();

    public void processCommand(ShippingCommand command){
        command.execute();
        commandHistory.add(command);
        undoStack.push(command);
    }
    public void undoLastAction(){
        if(!undoStack.isEmpty()){
            ShippingCommand command = undoStack.pop();
            command.undo();
        }else{
            SystemLogger.INSTANCE.logInfo("MANAGER", "Nothing to undo");
        }
    }
}
// Koniec, Tydzień 5
// Wzorzec Command 2