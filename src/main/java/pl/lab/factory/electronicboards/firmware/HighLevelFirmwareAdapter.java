package pl.lab.factory.electronicboards.firmware;
// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
//implementacja adaptera 3
public class HighLevelFirmwareAdapter implements FirmwareAdapter{
    private HighLevelFirmware firmware;

    public HighLevelFirmwareAdapter(HighLevelFirmware firmware) {
        this.firmware = firmware;
    }

    @Override
    public String getFirmwareString() {
        return firmware.getCode();
    }
}
//koniec adapter 3
//koniec tydzien 3