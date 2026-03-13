package pl.lab.factory.electronicboards.firmware;

public class HighLevelFirmware {
    private String code;

    public HighLevelFirmware(String code) {
        this.code = code;
    }

    public String getCode() {return code.trim();}
}
