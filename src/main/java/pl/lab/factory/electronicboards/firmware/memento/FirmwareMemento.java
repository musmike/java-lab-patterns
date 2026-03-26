package pl.lab.factory.electronicboards.firmware.memento;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Memento 3 (Klasa Memento)
public class FirmwareMemento {
    private final String firmware;
    private final boolean programmed;

    public FirmwareMemento(String firmware, boolean programmed) {
        this.firmware = firmware;
        this.programmed = programmed;
    }

    public String getFirmware() {
        return firmware;
    }

    public boolean isProgrammed() {
        return programmed;
    }
}

//memento 3
//koniec tydzien 5