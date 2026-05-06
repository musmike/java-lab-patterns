package pl.lab.factory.electronicboards.firmware.commands;

import pl.lab.factory.electronicboards.firmware.memento.FirmwareMemento;

import java.util.ArrayList;
import java.util.List;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 3 (Invoker)
public class FirmwareCommandInvoker {
    private List<FirmwareCommand> commandList = new ArrayList<>();
    private final short MAX_ATTEMPTS = 3;

    public void add(FirmwareCommand command){
        commandList.add(command);
    }

    public void executeAll(){

        for(FirmwareCommand command : commandList){
            boolean success = false;
            int attempts = 0;
            while(!success && attempts < MAX_ATTEMPTS){
                try {
                    command.execute();
                    success=true;
                } catch (RuntimeException e){
                    attempts++;
                    System.out.println("[Firmware Invoker] Command failed, retrying... (" + attempts + ")");
                    if(attempts >= MAX_ATTEMPTS){
                        System.out.println("[Firmware Invoker] Command failed completely after 3 tries!!!");
                    }
                }
            }
        }
    }
}

//command 3
//koniec tydzien 5