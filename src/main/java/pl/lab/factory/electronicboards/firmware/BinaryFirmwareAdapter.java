package pl.lab.factory.electronicboards.firmware;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// implementacja adaptera 3
public class BinaryFirmwareAdapter implements FirmwareAdapter{
    private BinaryFirmware firmware;

    public BinaryFirmwareAdapter(BinaryFirmware firmware) {
        this.firmware = firmware;
    }

    @Override
    public String getFirmwareString() {
        return firmware.getData().toString();
    }
}
//koniec adapter 3
//koniec tydzien 3