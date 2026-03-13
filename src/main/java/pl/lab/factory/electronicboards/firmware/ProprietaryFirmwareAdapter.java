package pl.lab.factory.electronicboards.firmware;

public class ProprietaryFirmwareAdapter implements FirmwareAdapter{
    private ProprietaryFirmware firmware;

    public ProprietaryFirmwareAdapter(ProprietaryFirmware firmware) {
        this.firmware = firmware;
    }

    @Override
    public String getFirmwareString() {
        return firmware.firmwareCode;
    }
}
