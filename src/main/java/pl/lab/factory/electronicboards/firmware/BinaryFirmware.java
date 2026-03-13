package pl.lab.factory.electronicboards.firmware;

//tydzien 3 - firmware to zadaptowania
public class BinaryFirmware {
    public BinaryFirmware(byte[] data) {
        this.data = data;
    }

    private byte[] data;
    public byte[] getData() {return data;}
}
//koniec tydzien 3