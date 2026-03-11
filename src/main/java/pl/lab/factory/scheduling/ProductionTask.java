package pl.lab.factory.scheduling;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 1 (Komponent)
// Wspólny kontrakt dla liści (SingleOrderTask) i kompozytów (ProductionBatch). Klient wywołuje execute()
// nie wiedząc, czy ma do czynienia z jednym zleceniem czy całą hierarchią zagnieżdżonych partii.
public interface ProductionTask {
    void execute() throws InterruptedException;
    String getDescription(String indent);
    int totalUnits();
}
// Koniec, Tydzień 3
// Wzorzec Composite 1
