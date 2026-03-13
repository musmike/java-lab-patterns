package pl.lab.factory.electronicboards.firmware;

public class BinaryFirmware {
    public BinaryFirmware(byte[] data) {
        this.data = data;
    }

    private byte[] data;
    public byte[] getData() {return data;}
}
