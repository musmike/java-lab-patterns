package pl.lab.factory.electronicboards.firmware;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 3
// Implementacja adaptera
public class ProprietaryFirmwareAdapter implements FirmwareAdapter {
    private ProprietaryFirmware firmware;

    public ProprietaryFirmwareAdapter(ProprietaryFirmware firmware) {
        this.firmware = firmware;
    }

    @Override
    public String getFirmwareString() {
        return firmware.firmwareCode;
    }
}
// Koniec, Tydzień 3
// Wzorzec Adapter 3