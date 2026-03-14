package pl.lab.factory.scheduling;

import pl.lab.factory.SystemLogger;
import java.util.ArrayList;
import java.util.List;

// Tydzień 3 - Stosowanie strukturalnych wzorców projektowych cz. 1
// Wzorzec Composite 1 (Kompozyt)
// Grupuje dowolną liczbę ProductionTask — zarówno liści (SingleOrderTask), jak i innych partii (ProductionBatch),
// tworząc drzewo o dowolnej głębokości. execute() deleguje do każdego dziecka — klient nie zna struktury drzewa.
public class ProductionBatch implements ProductionTask {

    private final String name;
    private final List<ProductionTask> tasks = new ArrayList<>();

    public ProductionBatch(String name) {
        this.name = name;
    }

    public void add(ProductionTask task) {
        tasks.add(task);
    }

    public void remove(ProductionTask task) {
        tasks.remove(task);
    }

    @Override
    public void execute() throws InterruptedException {
        SystemLogger.INSTANCE.logInfo("ProductionBatch",
                "=== START of batch: " + name
                        + " (" + tasks.size() + " tasks, " + totalUnits() + " total units) ===");

        for (ProductionTask task : tasks) {
            task.execute();
        }

        SystemLogger.INSTANCE.logInfo("ProductionBatch", "=== END of batch: " + name + " ===");
    }

    @Override
    public String getDescription(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("+ [BATCH] ").append(name)
                .append(" (").append(totalUnits()).append(" units)\n");
        for (ProductionTask task : tasks) {
            sb.append(task.getDescription(indent + "  ")).append("\n");
        }
        return sb.toString().stripTrailing();
    }

    @Override
    public int totalUnits() {
        return tasks.stream().mapToInt(ProductionTask::totalUnits).sum();
    }
}
// Koniec, Tydzień 3
// Wzorzec Composite 1