package pl.lab.factory.manufacturing;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.assemblyline.BoardProducer;
import pl.lab.factory.assemblyline.QualityControlDecorator;
import pl.lab.factory.assemblyline.SimpleBoardProducer;
import pl.lab.factory.assemblyline.TimedProducerDecorator;
import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;
import pl.lab.factory.orders.ProductionOrder;

public class ElectronicBoardOrderProcessor extends OrderProcessor {

    private final BoardProducer pcbProducer;

    public ElectronicBoardOrderProcessor() {
        this.pcbProducer = new QualityControlDecorator(
                new TimedProducerDecorator(new SimpleBoardProducer())
        );
    }

    @Override
    protected void produceUnit(ProductionOrder order) throws InterruptedException {
        BaseElectronicBoard eb = ((BaseElectronicBoardProductionOrder) order).getBaseElectronicBoard();

        buildBaseBoard(eb);
        solderComponents(eb);
        assembleBoard(eb);
        installFirmware(eb);
    }

    private void buildBaseBoard(BaseElectronicBoard board) throws InterruptedException {
        pcbProducer.produce(board.getBaseBoard());
        SystemLogger.INSTANCE.logInfo("ElectronicBoardProcessor",
                "    Building Base Board: " + board.getBaseBoard());
    }

    private void solderComponents(BaseElectronicBoard board) {
        SystemLogger.INSTANCE.logInfo("ElectronicBoardProcessor",
                "    Soldering Components on: " + board.getBaseBoard());
    }

    private void assembleBoard(BaseElectronicBoard board) {
        SystemLogger.INSTANCE.logInfo("ElectronicBoardProcessor",
                "    Assembling Electronic Board");
    }

    private void installFirmware(BaseElectronicBoard board) {
        SystemLogger.INSTANCE.logInfo("ElectronicBoardProcessor",
                "    Installing Firmware: " + board.getFirmware());
    }
}
