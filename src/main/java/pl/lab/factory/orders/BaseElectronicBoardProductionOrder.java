package pl.lab.factory.orders;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.orders.visitor.OrderVisitor;


// Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
// Wzorzec Visitor 1 (Concrete Element)
public class BaseElectronicBoardProductionOrder extends ProductionOrder {
    private BaseElectronicBoard electronicBoard;

    private BaseElectronicBoardProductionOrder(Builder builder) {
        super(new OrderContext(builder.id, builder.quantity, builder.orderSpecification), builder.status);
        this.electronicBoard = builder.electronicBoard;
    }

    public BaseElectronicBoard getBaseElectronicBoard() {
        return electronicBoard;
    }

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Prototype 3
    @Override
    public ProductionOrder clone(String newId) {
        OrderContext context = new OrderContext(newId, this.quantity, this.specification.clone());
        return ProductionOrderFactory.createOrder(electronicBoard.clone(), context);
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

    public static class Builder extends AbstractOrderBuilder<Builder> {
        protected BaseElectronicBoard electronicBoard;

        public Builder(String id, BaseElectronicBoard electronicBoard) {
            super(id);
            this.electronicBoard = electronicBoard;
        }

        @Override
        protected Builder self() {
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