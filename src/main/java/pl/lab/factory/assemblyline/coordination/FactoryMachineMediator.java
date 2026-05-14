package pl.lab.factory.assemblyline.coordination;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.assemblyline.CircuitPrinter;
import pl.lab.factory.assemblyline.Machine;
import pl.lab.factory.assemblyline.Mounter;
import pl.lab.factory.assemblyline.Solderer;
import pl.lab.factory.assemblyline.assemblingstrategies.*;
import pl.lab.factory.assemblyline.machinesubroutines.MachineAdjusterDecorator;
import pl.lab.factory.assemblyline.machinesubroutines.MachineWarmupDecorator;
import pl.lab.factory.assemblyline.machinesubroutines.MounterValueTesterDecorator;
import pl.lab.factory.pcbboards.BaseBoard;

import java.util.concurrent.TimeUnit;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 3 (Konkretny Mediator)
public class FactoryMachineMediator implements MachineMediator{
    private static final int MANUAL_MOUNT_DELAY_MS = 100;
    private static final double DEFAULT_TEST_VALUE = 1.0;

    private final CircuitPrinter printer = CircuitPrinter.getInstance();
    private final Mounter mounter = Mounter.getInstance();
    private final Solderer solderer = Solderer.getInstance();


    @Override
    public void requestPrint(BaseBoard baseBoard, PrintingStrategy strategy) {
        SystemLogger.INSTANCE.log("", "Printing the circuit using laser!");
        printer.perform(baseBoard);
    }

    @Override
    public void requestMount(BaseBoard baseBoard, MountingStrategy strategy) {
        if (strategy instanceof AutoMount) {
            executeAutoMount(baseBoard);
        }
        else if (strategy instanceof ManualMount) {
            executeManualMount(baseBoard);
        }
    }

    private void executeAutoMount(BaseBoard baseBoard) {
        Machine decorated =
                new MachineWarmupDecorator<>(
                        new MachineAdjusterDecorator<>(
                                new MounterValueTesterDecorator(mounter, DEFAULT_TEST_VALUE)
                        )
                );
        decorated.perform(baseBoard);
        SystemLogger.INSTANCE.log("", "Automatically mounting components!");
    }

    private void executeManualMount(BaseBoard baseBoard) {
        baseBoard.mountComponents();
        try {
            TimeUnit.MILLISECONDS.sleep(MANUAL_MOUNT_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        SystemLogger.INSTANCE.log("", "Manually mounting components!");
    }

    @Override
    public void requestSolder(BaseBoard baseBoard, SolderingStrategy strategy) {
        SystemLogger.INSTANCE.log("", "Soldering components...");

        if (strategy instanceof ReflowSolder) {
            solderer.setReflowMode(true);
            SystemLogger.INSTANCE.log("", "Using reflow soldering method!");
        }

        else if (strategy instanceof WaveSolder) {
            solderer.setReflowMode(false);
            SystemLogger.INSTANCE.log("", "Using wave soldering method!");
        }

        solderer.perform(baseBoard);
    }
}

//mediator 3
//koniec tydzien 5