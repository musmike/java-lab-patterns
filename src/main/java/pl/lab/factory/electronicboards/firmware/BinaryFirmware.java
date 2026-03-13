package pl.lab.factory.electronicboards.firmware;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
//firmware to zadaptowania 3
public class BinaryFirmware {
    public BinaryFirmware(byte[] data) {
        this.data = data;
    }

    private byte[] data;
    public byte[] getData() {return data;}
}
//koniec adapter 3
//koniec tydzien 3