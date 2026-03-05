package pl.lab.factory.orders;

import pl.lab.factory.electronicboards.BaseElectronicBoard;

public class BaseElectronicBoardProductionOrder extends ProductionOrder {
    private BaseElectronicBoard electronicBoard;

    private BaseElectronicBoardProductionOrder(Builder builder) {
        super(builder.id, builder.quantity, builder.status, builder.specification);
        this.electronicBoard = builder.electronicBoard;
    }

    public BaseElectronicBoard getBaseElectronicBoard() {
        return electronicBoard;
    }

    public static class Builder {
        private BaseElectronicBoard electronicBoard;
        private String id;
        private int quantity = 1;
        private OrderStatus status = OrderStatus.PENDING;
        private OrderSpecification specification;

        public Builder(String id, BaseElectronicBoard electronicBoard) {
            this.id = id;
            this.electronicBoard = electronicBoard;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setStatus(OrderStatus status) {
            this.status = status; return this;
        }

        public Builder setSpecification(OrderSpecification spec) {
            this.specification = spec;
            return this;
        }

        public BaseElectronicBoardProductionOrder build() {
            return new BaseElectronicBoardProductionOrder(this);
        }
    }
}
