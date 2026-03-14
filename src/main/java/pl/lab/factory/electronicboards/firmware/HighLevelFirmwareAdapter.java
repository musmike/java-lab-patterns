package pl.lab.factory.electronicboards.firmware;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 3
// Implementacja adaptera
public class HighLevelFirmwareAdapter implements FirmwareAdapter {
    private HighLevelFirmware firmware;

    public HighLevelFirmwareAdapter(HighLevelFirmware firmware) {
        this.firmware = firmware;
    }

    @Override
    public String getFirmwareString() {
        return firmware.getCode();
    }
}
// Koniec, Tydzień 3
// Wzorzec Adapter 3