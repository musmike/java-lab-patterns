package pl.lab.factory.processes;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.assemblyline.BoardProducer;
import pl.lab.factory.assemblyline.SmartBoardProducer;
import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.electronicboards.ElectronicType;
import pl.lab.factory.electronicboards.firmware.*;
import pl.lab.factory.electronicboards.firmware.programmers.*;
import pl.lab.factory.manufacturing.BoardPrototypes;
import pl.lab.factory.manufacturing.ElectronicBoardFactory;
import pl.lab.factory.manufacturing.SuperElectronicBoardFactory;
import pl.lab.factory.pcbboards.monitoring.CountingVisitor;
import pl.lab.factory.pcbboards.monitoring.ProcessObserver;
import pl.lab.factory.pcbboards.monitoring.reporting.CostReport;
import pl.lab.factory.processes.context.FactoryContext;

public class BoardPreparationProcess {
    private static final String FIRMWARE_PROTOTYPE = "v0.0.1-prototype";
    private static final String FIRMWARE_RELEASE = "v1.0.0-release";
    private static final String FIRMWARE_HOTFIX = "v1.1.0-hotfix";
    private static final String PROTOTYPE_DEPENDENT_V1 = "highly-dependant-v1";
    private static final String LOG_CONTEXT = "PROTOTYPE";

    private final FactoryContext context;

    public BoardPreparationProcess(FactoryContext context) {
        this.context = context;
    }

    public void execute() throws InterruptedException {
        testSmartBoardAndFirmware();
        testCreatingPrototypes();
    }

    private void testSmartBoardAndFirmware() throws InterruptedException {
        BaseElectronicBoard sqcEBoard = createSmartBoard();
        observeAndProduceSmartBoard(sqcEBoard);
        programFaultyDevice(sqcEBoard);
        verifyBoardComponents(sqcEBoard);
    }

    private BaseElectronicBoard createSmartBoard() {
        return SuperElectronicBoardFactory.createBoard(ElectronicType.NORMAL);
    }

    private void observeAndProduceSmartBoard(BaseElectronicBoard board) throws InterruptedException {
        ProcessObserver observer = new ProcessObserver();
        observer.setBoard(board.getBaseBoard());

        CostReport costSummary = new CostReport();
        costSummary.generate(board.getBaseBoard());

        BoardProducer smartProducer = new SmartBoardProducer();
        smartProducer.produce(board.getBaseBoard());
    }

    private void programFaultyDevice(BaseElectronicBoard board) {
        FirmwareAdapter firmwareAdapter = new HighLevelFirmwareAdapter(new HighLevelFirmware(FIRMWARE_PROTOTYPE));
        ProgrammingDevice faultyDevice = new FaultyDevice();
        FirmwareProgrammer oldProgrammer = new OldFirmwareProgrammer(faultyDevice);

        oldProgrammer.program(board, firmwareAdapter);
    }

    private void verifyBoardComponents(BaseElectronicBoard board) {
        board.getBaseBoard().printComponentInfo();
        CountingVisitor counter = new CountingVisitor();
        board.getBaseBoard().accept(counter);
        System.out.println("[COMPONENT INFO] Board contains " + counter.getResistors() + " resistors.");
    }

    private void testCreatingPrototypes() {
        BoardPrototypes prototypesRegistry = setupPrototypeRegistry();

        BaseElectronicBoard electronicBoardA = createAndProgramReleaseClone(prototypesRegistry);
        BaseElectronicBoard electronicBoardB = createAndProgramHotfixClone(prototypesRegistry);

        context.setElectronicBoardA(electronicBoardA);
        context.setElectronicBoardB(electronicBoardB);

        logPrototypeComparisons(prototypesRegistry.createFromPrototype(PROTOTYPE_DEPENDENT_V1), electronicBoardA, electronicBoardB);
    }

    private BoardPrototypes setupPrototypeRegistry() {
        BaseElectronicBoard prototype = ElectronicBoardFactory.createBoard(ElectronicType.HIGHLY_DEPENDANT);
        BoardPrototypes registry = new BoardPrototypes();
        registry.registerPrototype(PROTOTYPE_DEPENDENT_V1, prototype);
        return registry;
    }

    private BaseElectronicBoard createAndProgramReleaseClone(BoardPrototypes registry) {
        BaseElectronicBoard cloneA = registry.createFromPrototype(PROTOTYPE_DEPENDENT_V1);
        FirmwareAdapter adapterA = new HighLevelFirmwareAdapter(new HighLevelFirmware(FIRMWARE_RELEASE));
        InternalProgrammer programmerA = new InternalProgrammer(new SeggerJLinkDevice());

        programmerA.program(cloneA, adapterA);
        return cloneA;
    }

    private BaseElectronicBoard createAndProgramHotfixClone(BoardPrototypes registry) {
        BaseElectronicBoard cloneB = registry.createFromPrototype(PROTOTYPE_DEPENDENT_V1);
        System.out.println("[COMPONENT INFO] Gathering information about boards components!");
        verifyBoardComponents(cloneB);

        FirmwareAdapter adapterB = new ProprietaryFirmwareAdapter(new ProprietaryFirmware(FIRMWARE_HOTFIX));
        InternalProgrammer programmerB = new InternalProgrammer(new STLinkDevice());

        programmerB.program(cloneB, adapterB);
        return cloneB;
    }

    private void logPrototypeComparisons(BaseElectronicBoard proto, BaseElectronicBoard a, BaseElectronicBoard b) {
        SystemLogger.INSTANCE.logInfo(LOG_CONTEXT, "Prototype: firmware=" + proto.getFirmware());
        SystemLogger.INSTANCE.logInfo(LOG_CONTEXT, "Clone A:   firmware=" + a.getFirmware());
        SystemLogger.INSTANCE.logInfo(LOG_CONTEXT, "Clone B:   firmware=" + b.getFirmware());
    }
}