package pl.lab.factory.pcbboards.monitoring;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz.2
// wzorzec observer 3 - interfejs klasy obserwowanej
public interface ObservableBoard {
    public void register(BoardObserver boardObserver);
    public void unregister(BoardObserver boardObserver);

    public void notifyObservers();
    public String getUpdate(BoardObserver boardObserver);
}
//observer 3
//koniec tydzien 6