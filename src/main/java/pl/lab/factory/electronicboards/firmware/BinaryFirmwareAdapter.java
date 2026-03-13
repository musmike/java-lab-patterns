package pl.lab.factory.electronicboards.firmware;

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
