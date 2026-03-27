package pl.lab.factory.safetymediator;

import java.util.Map;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Memento 2 (Stan Systemu Bezpieczeństwa)
public class SafetySystemMemento {
    private final Map<String, String> deviceStates;
    public SafetySystemMemento(Map<String, String> states){
        this.deviceStates = Map.copyOf(states);
    }

    public Map<String, String> getSavedStates(){
        return deviceStates;
    }
}
// Koniec, Tydzień 5
// Wzorzec Memento 2