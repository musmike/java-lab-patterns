package pl.lab.factory.electronicboards.firmware;

//tydzien 3 - implementacja adaptera
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
//koniec tydzien 3