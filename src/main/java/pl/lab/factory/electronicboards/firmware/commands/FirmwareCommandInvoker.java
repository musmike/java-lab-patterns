package pl.lab.factory.electronicboards.firmware.commands;

import pl.lab.factory.SystemLogger;

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

    public void executeAll() {
        for (FirmwareCommand command : commandList) {
            executeWithRetry(command);
        }
    }

    private void executeWithRetry(FirmwareCommand command) {
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            try {
                command.execute();
                return;
            } catch (RuntimeException e) {
                attempts++;
                handleRetryFailure(attempts, e);
            }
        }
    }

    private void handleRetryFailure(int attempts, RuntimeException e) {
        SystemLogger.INSTANCE.logError("[Firmware Invoker]",
                "Command failed, retrying... (" + attempts + ")");

        if (attempts >= MAX_ATTEMPTS) {
            SystemLogger.INSTANCE.logError("[Firmware Invoker]",
                    "Command failed completely after " + MAX_ATTEMPTS + " tries!!!");
        }
    }
}

//command 3
//koniec tydzien 5