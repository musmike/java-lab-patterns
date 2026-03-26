package pl.lab.factory.safetymediator;

import java.util.Stack;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Memento 2 (Opiekun - Caretaker)
public class SafetyHistory {
    private final Stack<SafetySystemMemento> history = new Stack<>();

    public void save(SafetySystemMemento memento) {
        history.push(memento);
    }

    public SafetySystemMemento undo() {
        if (history.isEmpty()) return null;
        return history.pop();
    }
}
// Koniec, Tydzień 5
// Wzorzec Memento 2