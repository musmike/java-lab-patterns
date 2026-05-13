package pl.lab.factory.orders;

import pl.lab.factory.orders.visitor.OrderVisitor;
import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 1 (Concrete Element)
public class BaseBoardProductionOrder extends ProductionOrder {
    private BaseBoard baseBoard;

    private BaseBoardProductionOrder(Builder builder) {
        super(new OrderContext(builder.id, builder.quantity, builder.orderSpecification), builder.status);
        this.baseBoard = builder.baseBoard;
    }

    public BaseBoard getBaseBoard() { return baseBoard; }

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Prototype 3
    @Override
    public ProductionOrder clone(String newId) {
        OrderContext context = new OrderContext(newId, this.quantity, this.specification.clone());
        return ProductionOrderFactory.createOrder(baseBoard.deepCopy(), context);
    }
    // Koniec, Tydzień 2
    // Wzorzec Prototype 3


    @Override
    public void accept(OrderVisitor visitor) {
        visitor.visit(this);
    }


    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Builder 1
    // Builder konstruuje obiekt BaseBoardProductionOrder etapami przez łańcuch
    // wywołań setterów — oddziela proces budowania od reprezentacji obiektu

    public static class Builder extends AbstractOrderBuilder<Builder> {
        protected BaseBoard baseBoard;

        public Builder(String id, BaseBoard baseBoard) {
            super(id);
            this.baseBoard = baseBoard;
        }

        @Override
        protected Builder self() {
            return this;
        }

        public BaseBoardProductionOrder build() {
            return new BaseBoardProductionOrder(this);
        }
    }

    // Koniec, Tydzień 2
    // Wzorzec Builder 1
}
// Koniec, Tydzień 6
// Wzorzec Visitor 1