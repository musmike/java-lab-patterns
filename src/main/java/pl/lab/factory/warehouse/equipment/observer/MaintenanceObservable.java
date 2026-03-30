package pl.lab.factory.warehouse.equipment.observer;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Observer 2 (Interface Observable)
public interface MaintenanceObservable {
    void addObserver(MaintenanceObserver observer);
    void removeObserver(MaintenanceObserver observer);
    void notifyObservers(String reason);
}
// Koniec, Tydzień 6
// Wzorzec Observer 2