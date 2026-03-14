package pl.lab.factory.electronicboards.firmware;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 3
// Firmware do zadaptowania
public class BinaryFirmware {
    private byte[] data;

    public BinaryFirmware(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {return data;}
}
// Koniec, Tydzień 3
// Wzorzec Adapter 3