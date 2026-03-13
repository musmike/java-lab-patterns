package pl.lab.factory.electronicboards.firmware;

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
