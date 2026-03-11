package pl.lab.factory;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.manufacturing.BoardProductionService;
import pl.lab.factory.manufacturing.BoardPrototypes;
import pl.lab.factory.manufacturing.ElectronicBoardFactory;
import pl.lab.factory.manufacturing.StandardBoardFactory;
import pl.lab.factory.orders.*;
import pl.lab.factory.pcbboards.BaseBoard;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SystemLogger.INSTANCE.logInfo("MAIN", "This is the PCB/Electronic board factory!");

        ProductionQueue queue = ProductionQueue.getInstance();
        StandardBoardFactory boardFactory = new StandardBoardFactory();

        BoardProductionService productionService = new BoardProductionService();

        OrderSpecification rohs = OrderSpecification.builder()
                .voltageTolerance(5.0)
                .thicknessTolerance(0.1)
                .addCertificate("RoHS")
                .addCertificate("ISO9001")
                .qualityStandard("Class 2")
                .build();

        OrderSpecification specPcb = rohs.clone();
        specPcb.addCertificate("IPC-A-610");

        OrderSpecification specElectronic = rohs.clone();
        specElectronic.setQualityStandard("Class 3");
        specElectronic.setVoltageTolerance(2.5);

        BaseElectronicBoard electronicBoardPrototype = ElectronicBoardFactory.createBoard(ElectronicType.HIGHLY_DEPENDANT, "v1.0.0-release");

        BoardPrototypes boardPrototypes = new BoardPrototypes();
        boardPrototypes.registerPrototype("highly-dependant-v1", electronicBoardPrototype);

        BaseElectronicBoard electronicBoardA = boardPrototypes.createFromPrototype("highly-dependant-v1");
        BaseElectronicBoard electronicBoardB = boardPrototypes.createFromPrototype("highly-dependant-v1");
        electronicBoardB.setFirmware("v1.1.0-hotfix");

        SystemLogger.INSTANCE.logInfo("PROTOTYPE", "Prototyp: firmware=" + electronicBoardPrototype.getFirmware());
        SystemLogger.INSTANCE.logInfo("PROTOTYPE", "Klon A:   firmware=" + electronicBoardA.getFirmware());
        SystemLogger.INSTANCE.logInfo("PROTOTYPE", "Klon B:   firmware=" + electronicBoardB.getFirmware());

        BaseBoard boardA = boardFactory.createBoard();

        ProductionOrder orderA = ProductionOrderFactory.createOrder("ORD-001", boardA, 10, specPcb);
        ProductionOrder orderB = ProductionOrderFactory.createOrder("ORD-002", electronicBoardA, 3, specElectronic);
        ProductionOrder orderC = ProductionOrderFactory.createOrder("ORD-003", electronicBoardB, 2, specElectronic);
        ProductionOrder orderBaseClone = orderA.clone("ORD-004");
        ProductionOrder orderElectronicClone = orderB.clone("ORD-005");

        queue.addOrder(orderA);
        queue.addOrder(orderB);
        queue.addOrder(orderC);
        queue.addOrder(orderBaseClone);
        queue.addOrder(orderElectronicClone);
        queue.printQueue();
        TimeUnit.SECONDS.sleep(1);

        while (!queue.isEmpty()) {
            ProductionOrder current = queue.nextOrder();
            current.setStatus(OrderStatus.IN_PRODUCTION);

            SystemLogger.INSTANCE.logInfo("PRODUKCJA", "Rozpoczęto zlecenie: " + current.getId()
                    + " | qty: " + current.getQuantity()
                    + " | spec: " + current.getSpecification());

            for (int i = 1; i <= current.getQuantity(); i++) {
                SystemLogger.INSTANCE.logInfo("PRODUKCJA", "  [" + i + "/" + current.getQuantity() + "] "
                        + current.getId() + " — produkcja jednostki...");

                BaseBoard boardToProduce = null;
                if (current instanceof BaseBoardProductionOrder bpo) {
                    boardToProduce = bpo.getBoard();
                } else if (current instanceof BaseElectronicBoardProductionOrder ebpo) {
                    boardToProduce = ebpo.getBaseElectronicBoard().getBaseBoard();
                }

                if (boardToProduce != null) {
                    productionService.produce(boardToProduce);
                }

                TimeUnit.MILLISECONDS.sleep(500);
            }

            current.setStatus(OrderStatus.COMPLETED);
            SystemLogger.INSTANCE.logInfo("PRODUKCJA", current.getId() + " -> " + current.getStatus());
            TimeUnit.MILLISECONDS.sleep(500);
        }

        SystemLogger.INSTANCE.logInfo("MAIN", "Wszystkie zlecenia przetworzone. Koniec.");
    }
}