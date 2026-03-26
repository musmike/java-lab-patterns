package pl.lab.factory.assemblyline.coordination;

import pl.lab.factory.assemblyline.CircuitPrinter;
import pl.lab.factory.assemblyline.Machine;
import pl.lab.factory.assemblyline.Mounter;
import pl.lab.factory.assemblyline.Solderer;
import pl.lab.factory.assemblyline.assemblingstrategiers.*;
import pl.lab.factory.assemblyline.machinesubroutines.MachineAdjusterDecorator;
import pl.lab.factory.assemblyline.machinesubroutines.MachineWarmupDecorator;
import pl.lab.factory.assemblyline.machinesubroutines.MounterValueTesterDecorator;
import pl.lab.factory.pcbboards.BaseBoard;

import java.util.concurrent.TimeUnit;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 3 (Konkretny Mediator)
public class FactoryMachineMediator implements MachineMediator{
    private final CircuitPrinter printer = CircuitPrinter.getInstance();
    private final Mounter mounter = Mounter.getInstance();
    private final Solderer solderer = Solderer.getInstance();


    @Override
    public void requestPrint(BaseBoard board, PrintingStrategy strategy) {
        System.out.println("Printing the circuit using laser!");
        printer.perform(board);
    }

    @Override
    public void requestMount(BaseBoard board, MountingStrategy strategy) {
        if (strategy instanceof AutoMount) {

            Machine decorated =
                    new MachineWarmupDecorator<>(
                            new MachineAdjusterDecorator<>(
                                    new MounterValueTesterDecorator(mounter, 1)
                            )
                    );

            decorated.perform(board);

            System.out.println("Automatically mounting components!");
        }

        else if (strategy instanceof ManualMount) {

            board.mountComponents();

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Manually mounting components!");
        }
    }

    @Override
    public void requestSolder(BaseBoard board, SolderingStrategy strategy) {
        System.out.println("Soldering components...");

        if (strategy instanceof ReflowSolder) {
            solderer.setReflowMode(true);
            System.out.println("Using reflow soldering method!");
        }

        else if (strategy instanceof WaveSolder) {
            solderer.setReflowMode(false);
            System.out.println("Using wave soldering method!");
        }

        solderer.perform(board);
    }
}

//mediator 3
//koniec tydzien 5