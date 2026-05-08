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

    private static final double STD_VOLTAGE_TOL = 5.0;
    private static final double STD_THICKNESS_TOL = 0.1;
    private static final double TIGHT_VOLTAGE_TOL = 2.5;

    private static final String CERT_ROHS = "RoHS";
    private static final String CERT_ISO9001 = "ISO9001";
    private static final String CERT_IPC = "IPC-A-610";
    private static final String QUALITY_CLASS_2 = "Class 2";
    private static final String QUALITY_CLASS_3 = "Class 3";

    private static final String ORDER_1_ID = "ORD-001";
    private static final int ORDER_1_QTY = 10;
    private static final String ORDER_2_ID = "ORD-002";
    private static final int ORDER_2_QTY = 3;
    private static final String ORDER_3_ID = "ORD-003";
    private static final int ORDER_3_QTY = 2;
    private static final String ORDER_BASE_CLONE_ID = "ORD-004";
    private static final String ORDER_ELEC_CLONE_ID = "ORD-005";

    private final FactoryContext context;

    public OrderPreparationProcess(FactoryContext context) {
        this.context = context;
    }

    public void execute() {
        // -- Creating orders --
        createOrderSpecifications();
        createFinalOrders();
    }

    private void createOrderSpecifications() {
        context.setPcbProcessor(new BaseBoardOrderProcessor());
        context.setElectronicBoardProcessor(new ElectronicBoardOrderProcessor());

        OrderSpecification baseSpec = createBaseSpec();
        context.setSpecPcb(createPcbSpec(baseSpec));
        context.setSpecElectronic(createElectronicSpec(baseSpec));
    }

    private OrderSpecification createBaseSpec() {
        return OrderSpecification.builder()
                .voltageTolerance(STD_VOLTAGE_TOL)
                .thicknessTolerance(STD_THICKNESS_TOL)
                .addCertificate(CERT_ROHS)
                .addCertificate(CERT_ISO9001)
                .qualityStandard(QUALITY_CLASS_2)
                .build();
    }

    private OrderSpecification createPcbSpec(OrderSpecification baseSpec) {
        OrderSpecification specPcb = baseSpec.clone();
        specPcb.addCertificate(CERT_IPC);
        specPcb.displayCertificates(ORDER_1_ID);
        return specPcb;
    }

    private OrderSpecification createElectronicSpec(OrderSpecification baseSpec) {
        OrderSpecification specElectronic = baseSpec.clone();
        specElectronic.setQualityStandard(QUALITY_CLASS_3);
        specElectronic.setVoltageTolerance(TIGHT_VOLTAGE_TOL);
        return specElectronic;
    }

    private void createFinalOrders() {
        context.setBoardA(new StandardBoardFactory().createBoard());

        ProductionOrder orderA = ProductionOrderFactory.createOrder(
                context.getBoardA(), new OrderContext(ORDER_1_ID, ORDER_1_QTY, context.getSpecPcb()));

        ProductionOrder orderB = ProductionOrderFactory.createOrder(
                context.getElectronicBoardA(), new OrderContext(ORDER_2_ID, ORDER_2_QTY, context.getSpecElectronic()));

        ProductionOrder orderC = ProductionOrderFactory.createOrder(
                context.getElectronicBoardB(), new OrderContext(ORDER_3_ID, ORDER_3_QTY, context.getSpecElectronic()));

        context.setOrderA(orderA);
        context.setOrderB(orderB);
        context.setOrderC(orderC);

        context.setOrderBaseClone(orderA.clone(ORDER_BASE_CLONE_ID));
        context.setOrderElectronicClone(orderB.clone(ORDER_ELEC_CLONE_ID));

        context.setAllOrders(new ArrayList<>(List.of(
                orderA, orderB, orderC, context.getOrderBaseClone(), context.getOrderElectronicClone()
        )));
    }
}