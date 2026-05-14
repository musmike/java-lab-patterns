package pl.lab.factory.orders.invoice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.pricing.PricingStrategy;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

// Tydzień 13 - Stosowanie testów
// Testy jednostkowe (klasa 1) - OrderInvoiceDraftState
class OrderInvoiceDraftStateTest {

    private static final double BASE_INVOICE_AMOUNT = 200.0;
    private static final double DISCOUNT_PERCENTAGE = 10.0;
    private static final double EXPECTED_DISCOUNTED_AMOUNT = 180.0;
    private static final double NEW_CALCULATED_AMOUNT = 250.0;
    private static final int SINGLE_INVOCATION = 1;

    private OrderInvoice invoiceMock;
    private ProductionOrder orderMock;
    private PricingStrategy strategyMock;
    private OrderInvoiceDraftState draftState;

    @BeforeEach
    void setUp() {
        invoiceMock = mock(OrderInvoice.class);
        orderMock = mock(ProductionOrder.class);
        strategyMock = mock(PricingStrategy.class);
        draftState = new OrderInvoiceDraftState();

        when(invoiceMock.getOrder()).thenReturn(orderMock);
        when(orderMock.getId()).thenReturn("DRAFT-ORD-01");
    }

    @Test
    // Weryfikuje, że faktura w stanie draft nie może zostać opłacona i rzuca wyjątek.
    void payThrowsExceptionBecauseDraftCannotBePaid() {
        assertThrows(IllegalStateException.class, () -> {
            draftState.pay(invoiceMock);
        });
    }

    @Test
    // Sprawdza, że nie można wygenerować paragonu dla nieopłaconego draftu.
    void generateReceiptThrowsExceptionBecauseDraftIsNotPaid() {
        assertThrows(IllegalStateException.class, () -> {
            draftState.generateReceipt(invoiceMock);
        });
    }

    @Test
    // Weryfikuje, czy zastosowanie rabatu poprawnie zmniejsza kwotę faktury.
    void applyDiscountReducesInvoiceAmountCorrectly() {
        when(invoiceMock.getAmount()).thenReturn(BASE_INVOICE_AMOUNT);

        draftState.applyDiscount(invoiceMock, DISCOUNT_PERCENTAGE);

        verify(invoiceMock, times(SINGLE_INVOCATION)).setAmount(EXPECTED_DISCOUNTED_AMOUNT);
    }

    @Test
    // Sprawdza, czy cena faktury jest poprawnie przeliczana przy użyciu podanej strategii cenowej.
    void recalculatePriceUpdatesAmountUsingProvidedStrategy() {
        when(strategyMock.calculatePrice(orderMock)).thenReturn(NEW_CALCULATED_AMOUNT);

        draftState.recalculatePrice(invoiceMock, strategyMock);

        verify(invoiceMock, times(SINGLE_INVOCATION)).setAmount(NEW_CALCULATED_AMOUNT);
    }

    @Test
    // Weryfikuje, czy potwierdzenie draftu zmienia stan faktury na unpaid.
    void confirmTransitionsInvoiceToUnpaidState() {
        draftState.confirm(invoiceMock);

        verify(invoiceMock, times(SINGLE_INVOCATION)).setState(any(OrderInvoiceUnpaidState.class));
    }
}
// Koniec, Tydzień 13
// Testy jednostkowe (klasa 1)