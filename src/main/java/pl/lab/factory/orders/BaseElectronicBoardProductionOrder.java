package pl.lab.factory.orders;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.orders.visitor.OrderVisitor;


// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 1 (Concrete Element)
public class BaseElectronicBoardProductionOrder extends ProductionOrder {
    private BaseElectronicBoard electronicBoard;

    private BaseElectronicBoardProductionOrder(Builder builder) {
        super(builder.id, builder.quantity, builder.status, builder.specification);
        this.electronicBoard = builder.electronicBoard;
    }

    public BaseElectronicBoard getBaseElectronicBoard() {
        return electronicBoard;
    }

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Prototype 3
    @Override
    public ProductionOrder clone(String newId) {
        return ProductionOrderFactory.createOrder(
                newId,
                electronicBoard.clone(),
                this.quantity,
                this.specification.clone()
        );
    }
    // Koniec, Tydzień 2
    // Wzorzec Prototype 3


    @Override
    public void accept(OrderVisitor visitor) {
        visitor.visit(this);
    }

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Builder 2
    // Builder konstruuje obiekt BaseElectronicBoardProductionOrder etapami —
    // umożliwia opcjonalne ustawienie specyfikacji i statusu bez wielu konstruktorów

    public static class Builder {
        private static final int DEFAULT_QUANTITY = 1;

        private BaseElectronicBoard electronicBoard;
        private String id;
        private int quantity = DEFAULT_QUANTITY;
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

    // Koniec, Tydzień 2
    // Wzorzec Builder 2
}
// Koniec, Tydzień 6
// Wzorzec Visitor 1