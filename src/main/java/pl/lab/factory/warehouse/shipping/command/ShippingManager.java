package pl.lab.factory.warehouse.shipping.command;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.warehouse.shipping.strategy.FifoDispatcher;
import pl.lab.factory.warehouse.shipping.strategy.ShippingDispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 2 (Invoker - Wywołujący)
// Przechowuje historię poleceń i zarządza ich wykonywaniem oraz wycofywaniem (operacja undo).
public class ShippingManager {
    private final List<ShippingCommand> commandHistory = new ArrayList<>();
    private final Stack<ShippingCommand> undoStack = new Stack<>();

    // Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
    // Wzorzec Strategy 2 (Concrete Strategy)
    private List<ShippingCommand> pendingCommands = new ArrayList<>();
    private ShippingDispatcher dispatcher = new FifoDispatcher(); // domyślnie

    public void setDispatcher(ShippingDispatcher dispatcher){
        this.dispatcher = dispatcher;
    }

    public void addCommand(ShippingCommand cmd){
        pendingCommands.add(cmd);
    }

    public void shipEverything(){
        List<ShippingCommand> sorted = dispatcher.sortForShipping(new ArrayList<>(pendingCommands));

        for(ShippingCommand cmd : sorted){
            cmd.execute();
            commandHistory.add(cmd);
        }
        pendingCommands.clear();
    }

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