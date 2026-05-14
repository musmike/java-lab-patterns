package pl.lab.factory.orders.visitor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.BaseElectronicBoardProductionOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// Tydzień 13 - Stosowanie testów
// // Testy jednostkowe (klasa 2) - EuVatVisitor
class EuVatVisitorTest {

    private static final int LARGE_ORDER_QUANTITY = 10;
    private static final int SMALL_ORDER_QUANTITY = 5;
    private static final int SINGLE_ITEM_QUANTITY = 1;

    private static final double EXPECTED_LARGE_BASE_TAX = 115.0; // 10 * 50 * 0.23
    private static final double EXPECTED_SMALL_BASE_TAX = 72.5;  // (5 * 50 * 0.23) + 15.0
    private static final double EXPECTED_LARGE_ELEC_TAX = 345.0; // 10 * 150 * 0.23
    private static final double EXPECTED_SMALL_ELEC_TAX = 49.5;  // (1 * 150 * 0.23) + 15.0
    private static final double EXPECTED_COMBINED_TAX = 187.5;   // 115.0 + 72.5

    private static final String DUMMY_ORDER_ID = "VAT-ORD-01";

    private EuVatVisitor vatVisitor;

    @BeforeEach
    void setUp() {
        vatVisitor = new EuVatVisitor();
    }

    @Test
    // Sprawdza poprawne naliczanie VAT dla dużego zamówienia standardowej płyty.
    void visitCalculatesStandardVatForLargeBaseBoardOrder() {
        BaseBoardProductionOrder orderMock = mockBaseBoardOrder(LARGE_ORDER_QUANTITY);

        vatVisitor.visit(orderMock);

        assertEquals(EXPECTED_LARGE_BASE_TAX, vatVisitor.getTotalVat());
    }

    @Test
    // Weryfikuje, czy małe zamówienie standardowej płyty uwzględnia dodatkową opłatę.
    void visitAppliesSmallOrderFeeForSmallBaseBoardOrder() {
        BaseBoardProductionOrder orderMock = mockBaseBoardOrder(SMALL_ORDER_QUANTITY);

        vatVisitor.visit(orderMock);

        assertEquals(EXPECTED_SMALL_BASE_TAX, vatVisitor.getTotalVat());
    }

    @Test
    // Sprawdza VAT dla dużego zamówienia płyty elektronicznej.
    void visitCalculatesStandardVatForLargeElectronicBoardOrder() {
        BaseElectronicBoardProductionOrder orderMock = mockElectronicBoardOrder(LARGE_ORDER_QUANTITY);

        vatVisitor.visit(orderMock);

        assertEquals(EXPECTED_LARGE_ELEC_TAX, vatVisitor.getTotalVat());
    }

    @Test
    // Weryfikuje naliczenie VAT i opłaty dodatkowej dla małego zamówienia płyty elektronicznej.
    void visitAppliesSmallOrderFeeForSmallElectronicBoardOrder() {
        BaseElectronicBoardProductionOrder orderMock = mockElectronicBoardOrder(SINGLE_ITEM_QUANTITY);

        vatVisitor.visit(orderMock);

        assertEquals(EXPECTED_SMALL_ELEC_TAX, vatVisitor.getTotalVat());
    }

    @Test
    // Sprawdza, czy VAT jest poprawnie sumowany dla wielu odwiedzonych zamówień.
    void getTotalVatAccumulatesTaxesFromMultipleVisitedOrders() {
        BaseBoardProductionOrder largeOrderMock = mockBaseBoardOrder(LARGE_ORDER_QUANTITY);
        BaseBoardProductionOrder smallOrderMock = mockBaseBoardOrder(SMALL_ORDER_QUANTITY);

        vatVisitor.visit(largeOrderMock);
        vatVisitor.visit(smallOrderMock);

        assertEquals(EXPECTED_COMBINED_TAX, vatVisitor.getTotalVat());
    }

    private BaseBoardProductionOrder mockBaseBoardOrder(int quantity) {
        BaseBoardProductionOrder orderMock = mock(BaseBoardProductionOrder.class);

        when(orderMock.getQuantity()).thenReturn(quantity);
        when(orderMock.getId()).thenReturn(DUMMY_ORDER_ID);

        return orderMock;
    }

    private BaseElectronicBoardProductionOrder mockElectronicBoardOrder(int quantity) {
        BaseElectronicBoardProductionOrder orderMock = mock(BaseElectronicBoardProductionOrder.class);

        when(orderMock.getQuantity()).thenReturn(quantity);
        when(orderMock.getId()).thenReturn(DUMMY_ORDER_ID);

        return orderMock;
    }
}
// Koniec, Tydzień 13
// Testy jednostkowe (klasa 2)