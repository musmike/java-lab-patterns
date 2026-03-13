package pl.lab.factory.electronicboards.firmware;

//tydzien 3 - implementacja adaptera
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
//koniec tydzien 3