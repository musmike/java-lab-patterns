package pl.lab.factory.electronicboards.firmware.programmers;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.firmware.FirmwareAdapter;
import pl.lab.factory.electronicboards.firmware.commands.FinishProgrammingCommand;
import pl.lab.factory.electronicboards.firmware.commands.FirmwareCommandInvoker;
import pl.lab.factory.electronicboards.firmware.commands.StartProgrammingCommand;
import pl.lab.factory.electronicboards.firmware.commands.WriteFirmwareCommand;

public class OldFirmwareProgrammer extends FirmwareProgrammer{
    public OldFirmwareProgrammer(ProgrammingDevice device) {
        super(device);
    }

    @Override
    public void program(BaseElectronicBoard board, FirmwareAdapter firmwareAdapter) {
        FirmwareCommandInvoker invoker = new FirmwareCommandInvoker();
        invoker.add(new StartProgrammingCommand(device));
        invoker.add(new WriteFirmwareCommand(device, board, firmwareAdapter));
        invoker.add(new FinishProgrammingCommand(device));

        invoker.executeAll();
    }
}
