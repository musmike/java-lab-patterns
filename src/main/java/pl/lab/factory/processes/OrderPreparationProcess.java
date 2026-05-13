package pl.lab.factory.processes;

import pl.lab.factory.manufacturing.BaseBoardOrderProcessor;
import pl.lab.factory.manufacturing.ElectronicBoardOrderProcessor;
import pl.lab.factory.manufacturing.StandardBoardFactory;
import pl.lab.factory.orders.OrderContext;
import pl.lab.factory.orders.OrderSpecification;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.ProductionOrderFactory;
import pl.lab.factory.processes.context.FactoryContext;

import java.util.ArrayList;
import java.util.List;

public class OrderPreparationProcess {

    private static final double STANDARD_VOLTAGE_TOLERANCE = 5.0;
    private static final double STANDARD_THICKNESS_TOLERANCE = 0.1;
    private static final double TIGHT_VOLTAGE_TOLERANCE = 2.5;

    private static final String CERTIFICATION_ROHS = "RoHS";
    private static final String CERTIFICATION_ISO9001 = "ISO9001";
    private static final String CERTIFICATION_IPC = "IPC-A-610";
    private static final String QUALITY_CLASS_2 = "Class 2";
    private static final String QUALITY_CLASS_3 = "Class 3";

    private static final String PCB_ORDER_ID = "ORD-001";
    private static final int PCB_ORDER_QUANTITY = 10;
    private static final String STANDARD_ELECTRONIC_ORDER_ID = "ORD-002";
    private static final int STANDARD_ELECTRONIC_ORDER_QUANTITY = 3;
    private static final String SUPER_ELECTRONIC_ORDER_ID = "ORD-003";
    private static final int SUPER_ELECTRONIC_ORDER_QUANTITY = 2;
    private static final String PCB_ORDER_CLONE_ID = "ORD-004";
    private static final String ELECTRONIC_ORDER_CLONE_ID = "ORD-005";

    private final FactoryContext context;

    public OrderPreparationProcess(FactoryContext context) {
        this.context = context;
    }

    public void execute() {
        initializeProcessors();
        createOrderSpecifications();
        createFinalOrders();
    }

    private void initializeProcessors() {
        context.setPcbProcessor(new BaseBoardOrderProcessor());
        context.setElectronicBoardProcessor(new ElectronicBoardOrderProcessor());
    }

    private void createOrderSpecifications() {
        OrderSpecification baseSpec = createBaseSpec();
        context.setSpecPcb(createPcbSpec(baseSpec));
        context.setSpecElectronic(createElectronicSpec(baseSpec));
    }

    private OrderSpecification createBaseSpec() {
        return OrderSpecification.builder()
                .voltageTolerance(STANDARD_VOLTAGE_TOLERANCE)
                .thicknessTolerance(STANDARD_THICKNESS_TOLERANCE)
                .addCertificate(CERTIFICATION_ROHS)
                .addCertificate(CERTIFICATION_ISO9001)
                .qualityStandard(QUALITY_CLASS_2)
                .build();
    }

    private OrderSpecification createPcbSpec(OrderSpecification baseSpec) {
        OrderSpecification specPcb = baseSpec.clone();
        specPcb.addCertificate(CERTIFICATION_IPC);
        specPcb.displayCertificates(PCB_ORDER_ID);
        return specPcb;
    }

    private OrderSpecification createElectronicSpec(OrderSpecification baseSpec) {
        OrderSpecification specElectronic = baseSpec.clone();
        specElectronic.setQualityStandard(QUALITY_CLASS_3);
        specElectronic.setVoltageTolerance(TIGHT_VOLTAGE_TOLERANCE);
        return specElectronic;
    }

    private void createFinalOrders() {
        context.setStandardPcbBoard(new StandardBoardFactory().createBoard());
        createBaseOrders();
        cloneOrders();
        collectAllOrders();
    }

    private void createBaseOrders() {
        createPcbOrder();
        createStandardElectronicOrder();
        createSuperElectronicOrder();
    }

    private void createPcbOrder() {
        ProductionOrder pcbOrder = ProductionOrderFactory.createOrder(
            context.getStandardPcbBoard(),
            new OrderContext(PCB_ORDER_ID, PCB_ORDER_QUANTITY, context.getSpecPcb())
        );
        context.setPcbOrder(pcbOrder);
    }

    private void createStandardElectronicOrder() {
        ProductionOrder standardElectronicOrder = ProductionOrderFactory.createOrder(
            context.getHighlyDependentElectronicBoard(),
            new OrderContext(STANDARD_ELECTRONIC_ORDER_ID, STANDARD_ELECTRONIC_ORDER_QUANTITY, context.getSpecElectronic())
        );
        context.setStandardElectronicOrder(standardElectronicOrder);
    }

    private void createSuperElectronicOrder() {
        ProductionOrder superElectronicOrder = ProductionOrderFactory.createOrder(
            context.getHotfixElectronicBoard(),
            new OrderContext(SUPER_ELECTRONIC_ORDER_ID, SUPER_ELECTRONIC_ORDER_QUANTITY, context.getSpecElectronic())
        );
        context.setSuperElectronicOrder(superElectronicOrder);
    }

    private void cloneOrders() {
        context.setClonedPcbOrder(context.getPcbOrder().clone(PCB_ORDER_CLONE_ID));
        context.setClonedElectronicOrder(
            context.getStandardElectronicOrder().clone(ELECTRONIC_ORDER_CLONE_ID)
        );
    }

    private void collectAllOrders() {
        context.setAllOrders(new ArrayList<>(List.of(
            context.getPcbOrder(),
            context.getStandardElectronicOrder(),
            context.getSuperElectronicOrder(),
            context.getClonedPcbOrder(),
            context.getClonedElectronicOrder()
        )));
    }
}