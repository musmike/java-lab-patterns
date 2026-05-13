package pl.lab.factory.warehouse.packing;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Bridge 2 (Konkretny Implementor)

// Tydzień 8 - Stosowanie podstawienia Liskov i odwracania zależności, segregacja iterfejsów
// Zasada odwracania zależności (DIP) (klasa pochodna)
public class PlasticBoxPacking extends AbstractBoxPacking {
    @Override protected String getBoxLogName() { return "[PLASTIC BOX]"; }
    @Override protected String getBoxDescription() { return "plastic box"; }
}
// Koniec, Tydzień 8
// Zasada odwracania zależności (DIP)

// Koniec, Tydzień 3
// Wzorzec Bridge 2