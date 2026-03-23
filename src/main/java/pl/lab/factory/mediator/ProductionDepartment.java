package pl.lab.factory.mediator;

import pl.lab.factory.manufacturing.OrderProcessor;
import pl.lab.factory.orders.BaseBoardProductionOrder;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 1 (Konkretny Colleague — produkcja)
// Pełni rolę inicjatora. Po zakończeniu pracy powiadamia mediatora
// eventem ORDER_COMPLETED. Nie wie, co się potem z tym zamówieniem stanie.
public class ProductionDepartment extends Department {
    private final OrderProcessor pcbProcessor;
    private final OrderProcessor electronicBoardProcessor;

    public ProductionDepartment(FactoryMediator mediator,
                                OrderProcessor pcbProcessor, OrderProcessor electronicBoardProcessor) {
        super(mediator);
        this.pcbProcessor = pcbProcessor;
        this.electronicBoardProcessor = electronicBoardProcessor;
    }

    public void manufacture(ProductionOrder order) throws InterruptedException {
        OrderProcessor processor = (order instanceof BaseBoardProductionOrder)
                ? pcbProcessor
                : electronicBoardProcessor;

        processor.process(order);

        if (mediator != null && order.getStatus() == OrderStatus.COMPLETED) {
            mediator.notify(this, "ORDER_COMPLETED", order);
        }
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 1
