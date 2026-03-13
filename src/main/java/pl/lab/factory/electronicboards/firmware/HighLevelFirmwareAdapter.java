package pl.lab.factory.electronicboards.firmware;

//tydzien 3 - implementacja adaptera
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
//koniec tydzien 3