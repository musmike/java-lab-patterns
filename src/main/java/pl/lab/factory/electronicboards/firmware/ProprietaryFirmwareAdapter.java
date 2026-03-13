package pl.lab.factory.electronicboards.firmware;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// implementacja adaptera 3
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
//koniec adapter 3
//koniec tydzien 3