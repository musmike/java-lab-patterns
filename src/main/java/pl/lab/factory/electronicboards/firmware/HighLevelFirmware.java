package pl.lab.factory.electronicboards.firmware;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Adapter 3
// Firmware do zadaptowania
public class HighLevelFirmware {
    private String code;

    public HighLevelFirmware(String code) {
        this.code = code;
    }

    public String getCode() {return code.trim();}
}
// Koniec, Tydzień 3
// Wzorzec Adapter 3