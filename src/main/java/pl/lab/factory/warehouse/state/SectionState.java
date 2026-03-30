package pl.lab.factory.warehouse.state;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec State 2 (Interface State)
public interface SectionState {
    boolean canAddItem();
    void printStatus(String sectionName);
}
// Koniec, Tydzień 6
// Wzorzec State 2