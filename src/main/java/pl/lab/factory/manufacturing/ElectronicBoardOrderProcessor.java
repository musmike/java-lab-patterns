package pl.lab.factory.manufacturing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.assemblyline.*;
import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;
import pl.lab.factory.orders.ProductionOrder;

public class ElectronicBoardOrderProcessor extends OrderProcessor {

    private final BoardProducer boardProducer;

    public ElectronicBoardOrderProcessor() {
        this.boardProducer = BoardProducerFactory.createStandardProducer();
    }

    @Override
    protected void produceUnit(ProductionOrder order) throws InterruptedException {
        BaseElectronicBoard electronicBoard = ((BaseElectronicBoardProductionOrder) order).getBaseElectronicBoard();

        buildBaseBoard(electronicBoard);
        solderComponents(electronicBoard);
        assembleBoard(electronicBoard);
        installFirmware(electronicBoard);
    }

    private void buildBaseBoard(BaseElectronicBoard electronicBoard) throws InterruptedException {
        boardProducer.produce(electronicBoard.getBaseBoard());
        SystemLogger.INSTANCE.logInfo("ElectronicBoardProcessor",
                "    Building Base Board: " + electronicBoard.getBaseBoard());
    }

    private void solderComponents(BaseElectronicBoard electronicBoard) {
        SystemLogger.INSTANCE.logInfo("ElectronicBoardProcessor",
                "    Soldering Components on: " + electronicBoard.getBaseBoard());
    }

    private void assembleBoard(BaseElectronicBoard electronicBoard) {
        SystemLogger.INSTANCE.logInfo("ElectronicBoardProcessor",
                "    Assembling Electronic Board");
    }

    private void installFirmware(BaseElectronicBoard electronicBoard) {
        SystemLogger.INSTANCE.logInfo("ElectronicBoardProcessor",
                "    Installing Firmware: " + electronicBoard.getFirmware());
    }
}
