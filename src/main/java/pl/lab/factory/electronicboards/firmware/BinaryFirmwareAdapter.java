package pl.lab.factory.electronicboards.firmware;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 3
// Implementacja adaptera
public class BinaryFirmwareAdapter implements FirmwareAdapter {
    private BinaryFirmware firmware;

    public BinaryFirmwareAdapter(BinaryFirmware firmware) {
        this.firmware = firmware;
    }

    @Override
    public String getFirmwareString() {
        return firmware.getData().toString();
    }
}
// Koniec, Tydzień 3
// Wzorzec Adapter 3