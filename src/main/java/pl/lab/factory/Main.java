package pl.lab.factory;

import pl.lab.factory.processes.*;
import pl.lab.factory.processes.context.FactoryContext;

public class Main {
    private static final String APP_CONTEXT = Main.class.getSimpleName();

    public static void main(String[] args) {
        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "This is the PCB/Electronic board factory!");

        try {
            new Main().runFactoryProcesses();
        } catch (InterruptedException e) {
            SystemLogger.INSTANCE.logError(APP_CONTEXT, "Process interrupted" + e.getMessage());
            Thread.currentThread().interrupt();
        }

        SystemLogger.INSTANCE.logInfo(APP_CONTEXT, "All orders processed. End.");
    }

    private void runFactoryProcesses() throws InterruptedException {
        FactoryContext context = new FactoryContext();

        new BoardPreparationProcess(context).execute();
        new OrderPreparationProcess(context).execute();
        new FinancialProcess(context).execute();
        new FactoryOperationsProcess(context).execute();
        new WarehouseManagementProcess(context).execute();
        new ReportingAndMetricsProcess(context).execute();

        new FunctionalProgrammingProcess(context).execute();
    }
}