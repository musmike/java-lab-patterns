package pl.lab.factory.electronicboards.firmware;
//tydzien 3 - firmware to zadaptowania
public class HighLevelFirmware {
    private String code;

    public HighLevelFirmware(String code) {
        this.code = code;
    }

    public String getCode() {return code.trim();}
}
//koniec tydzien 3