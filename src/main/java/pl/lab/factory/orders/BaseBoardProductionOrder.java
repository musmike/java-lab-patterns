package pl.lab.factory.orders;

import pl.lab.factory.pcbboards.BaseBoard;

public class BaseBoardProductionOrder extends ProductionOrder {
    private BaseBoard board;

    private BaseBoardProductionOrder(Builder builder) {
        super(builder.id, builder.quantity, builder.status, builder.specification);
        this.board = builder.board;
    }

    public BaseBoard getBoard() { return board; }

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Prototype 3
    @Override
    public ProductionOrder clone(String newId) {
        return ProductionOrderFactory.createOrder(
                newId,
                board.deepCopy(),
                this.quantity,
                this.specification.clone()
        );
    }
    // Koniec, Tydzień 2
    // Wzorzec Prototype 3


    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Builder 1
    // Builder konstruuje obiekt BaseBoardProductionOrder etapami przez łańcuch
    // wywołań setterów — oddziela proces budowania od reprezentacji obiektu

    public static class Builder {
        private BaseBoard board;
        private String id;
        private int quantity = 1;
        private OrderStatus status = OrderStatus.PENDING;
        private OrderSpecification specification;

        public Builder(String id, BaseBoard board) {
            this.id = id;
            this.board = board;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setStatus(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder setSpecification(OrderSpecification spec) {
            this.specification = spec;
            return this;
        }

        public BaseBoardProductionOrder build() { return new BaseBoardProductionOrder(this); }
    }

    // Koniec, Tydzień 2
    // Wzorzec Builder 1
}
