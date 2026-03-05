package pl.lab.factory.orders;

import pl.lab.factory.pcbboards.BaseBoard;

public class BaseBoardProductionOrder extends ProductionOrder {
    private BaseBoard board;

    private BaseBoardProductionOrder(Builder builder) {
        super(builder.id, builder.quantity, builder.status);
        this.board = builder.board;
    }

    public BaseBoard getBoard() { return board; }

    public static class Builder {
        private BaseBoard board;
        private String id;
        private int quantity = 1;
        private OrderStatus status = OrderStatus.PENDING;

        public Builder(String id, BaseBoard board) {
            this.id = id;
            this.board = board;
        }

        public Builder setQuantity(int quantity) { this.quantity = quantity; return this; }

        public BaseBoardProductionOrder build() { return new BaseBoardProductionOrder(this); }
    }
}
