package pl.lab.factory.components;

import pl.lab.factory.components.definitions.CapacitorDefinition;
import pl.lab.factory.components.definitions.ComponentDefinition;
import pl.lab.factory.components.definitions.InductorDefinition;
import pl.lab.factory.components.definitions.ResistorDefinition;

import java.util.HashMap;
import java.util.Map;

// Tydzień 4 - Stosowanie strukturalnych wzorców projektowych cz. 2
// Wzorzec Flyweight 2 - Flyweight factory - rejestr uzywanych komponentow podstawowych
public class ComponentDefinitionFactory {
    private static final Map<String, ComponentDefinition> cache = new HashMap<>();

    private static final String PREFIX_CAPACITOR = "C/";
    private static final String PREFIX_RESISTOR = "R/";
    private static final String PREFIX_INDUCTOR = "I/";
    private static final String SEPARATOR = "/";

    public static CapacitorDefinition getCapacitorDefinition(int capacitance, double cost) {
        String key = PREFIX_CAPACITOR + capacitance + SEPARATOR + cost;
        return (CapacitorDefinition) cache.computeIfAbsent(key, k -> new CapacitorDefinition(capacitance, cost));
    }

    public static ResistorDefinition getResistorDefinition(int resistance, double cost) {
        String key = PREFIX_RESISTOR + resistance + SEPARATOR + cost;
        return (ResistorDefinition) cache.computeIfAbsent(key, k -> new ResistorDefinition(resistance, cost));
    }

    public static InductorDefinition getInductorDefinition(int inductance, double cost) {
        String key = PREFIX_INDUCTOR + inductance + SEPARATOR + cost;
        return (InductorDefinition) cache.computeIfAbsent(key, k -> new InductorDefinition(inductance, cost));
    }
}
//flyweight 2
//koniec tydzien 4