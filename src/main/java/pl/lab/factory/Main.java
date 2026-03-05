package pl.lab.factory;

import pl.lab.factory.assemblyline.CircuitPrinter;
import pl.lab.factory.assemblyline.Solderer;
import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.orders.*;
import pl.lab.factory.pcbboards.StandardBoard;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SystemLogger.INSTANCE.log("MAIN", "This is the PCB/Electronic board factory!");

        CircuitPrinter printer = CircuitPrinter.getInstance();
        Solderer solderer = Solderer.getInstance();
        ProductionQueue queue = ProductionQueue.getInstance();

        OrderSpecification rohs = new OrderSpecification(
                5.0, 0.1,
                List.of("RoHS", "ISO9001"),
                "Class 2"
        );

        OrderSpecification specPcb = rohs.clone();
        specPcb.addCertificate("IPC-A-610");

        OrderSpecification specElectronic = rohs.clone();
        specElectronic.setQualityStandard("Class 3");
        specElectronic.setVoltageTolerance(2.5);

        StandardBoard boardA = new StandardBoard();
        ProductionOrder orderA = ProductionOrderFactory.createOrder("ORD-001", boardA, 10, specPcb);

        StandardBoard baseForElectronic = new StandardBoard();
        BaseElectronicBoard electronicBoard = new BaseElectronicBoard();
        electronicBoard.setBaseBoard(baseForElectronic);
        electronicBoard.setFirmware("v1.0.0-release");
        electronicBoard.setBoardType(ElectronicType.HIGHLY_DEPENDANT);
        ProductionOrder orderB = ProductionOrderFactory.createOrder("ORD-002", electronicBoard, 3, specElectronic);

        queue.addOrder(orderA);
        queue.addOrder(orderB);
        queue.printQueue();

        while (!queue.isEmpty()) {
            ProductionOrder current = queue.nextOrder();
            current.setStatus(OrderStatus.IN_PRODUCTION);

            SystemLogger.INSTANCE.logInfo("PRODUKCJA", "Przetwarzanie: " + current.getId()
                    + " | spec: " + current.getSpecification());

            if (current instanceof BaseBoardProductionOrder bpo) {
                printer.printCircuits(bpo.getBoard());
                solderer.solderBoard(bpo.getBoard());
            } else if (current instanceof BaseElectronicBoardProductionOrder ebpo) {
                printer.printCircuits(ebpo.getBaseElectronicBoard().getBaseBoard());
                solderer.solderBoard(ebpo.getBaseElectronicBoard().getBaseBoard());
            }

            current.setStatus(OrderStatus.COMPLETED);
            SystemLogger.INSTANCE.logInfo("PRODUKCJA", current.getId() + " -> " + current.getStatus());
        }

        SystemLogger.INSTANCE.log("MAIN", "Wszystkie zlecenia przetworzone. Koniec.");
    }
}