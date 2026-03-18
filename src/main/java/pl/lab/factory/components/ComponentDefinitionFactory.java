package pl.lab.factory.components;

import pl.lab.factory.components.definitions.CapacitorDefinition;
import pl.lab.factory.components.definitions.ComponentDefinition;
import pl.lab.factory.components.definitions.InductorDefinition;
import pl.lab.factory.components.definitions.ResistorDefinition;

import java.util.HashMap;
import java.util.Map;

public class ComponentDefinitionFactory {
    private static final Map<String, ComponentDefinition> cache = new HashMap<>();

    public static CapacitorDefinition getCapacitorDefinition(int capacitance, double cost){
        String key = "C/" + capacitance + "/" + cost;
        return (CapacitorDefinition) cache.computeIfAbsent(key, k -> new CapacitorDefinition(capacitance, cost));
    }

    public static ResistorDefinition getResistorDefinition(int resistance, double cost){
        String key = "R/" + resistance + "/" + cost;
        return (ResistorDefinition) cache.computeIfAbsent(key, k -> new ResistorDefinition(resistance, cost));
    }

    public static InductorDefinition getInductorDefinition(int inductance, double cost){
        String key = "I/" + inductance + "/" + cost;
        return (InductorDefinition) cache.computeIfAbsent(key, k -> new InductorDefinition(inductance, cost));
    }
}
