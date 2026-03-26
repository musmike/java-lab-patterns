package pl.lab.factory.safetymediator;

import pl.lab.factory.safetymediator.components.SafetyDevice;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 2 (Interfejs mediatora bezpieczeństwa)
public interface SafetyMediator {
    void notify(SafetyDevice sender, String event);
    void resetSystem();
    SafetySystemMemento save();
    void restore(SafetySystemMemento memento);
}
// Koniec, Tydzień 5
// Wzorzec Mediator 2