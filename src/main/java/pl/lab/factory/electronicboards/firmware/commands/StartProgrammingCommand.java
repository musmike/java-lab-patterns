package pl.lab.factory.electronicboards.firmware.commands;

import pl.lab.factory.electronicboards.firmware.programmers.ProgrammingDevice;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Command 3 (Konkretne polecenie)
public class StartProgrammingCommand implements FirmwareCommand{
    private ProgrammingDevice device;

    public StartProgrammingCommand(ProgrammingDevice device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.start();
    }
}

//command 3
//koniec tydzien 5