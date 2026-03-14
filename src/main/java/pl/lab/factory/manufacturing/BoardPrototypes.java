package pl.lab.factory.manufacturing;

import pl.lab.factory.electronicboards.BaseElectronicBoard;

import java.util.HashMap;
import java.util.Map;

// Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
// Wzorzec Prototype 1
// Rejestr prototypów płytek elektronicznych — zamiast tworzyć każdą płytkę
// od zera, klonowany jest wcześniej zarejestrowany wzorzec (prototyp)

public class BoardPrototypes {
    private final Map<String, BaseElectronicBoard> prototypes = new HashMap<>();

    public void registerPrototype(String name, BaseElectronicBoard board) {
        prototypes.put(name, board);
    }

    public BaseElectronicBoard createFromPrototype(String name) {
        BaseElectronicBoard prototype = prototypes.get(name);
        if (prototype == null)
            throw new IllegalArgumentException("No prototype named: " + name);
        return prototype.clone();
    }
}

// Koniec, Tydzień 2
// Wzorzec Prototype 1