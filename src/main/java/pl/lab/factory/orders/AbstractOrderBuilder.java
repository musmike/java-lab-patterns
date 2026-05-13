package pl.lab.factory.orders;

public abstract class AbstractOrderBuilder<T extends AbstractOrderBuilder<T>> {
    protected static final int DEFAULT_QUANTITY = 1;

    protected String id;
    protected int quantity = DEFAULT_QUANTITY;
    protected OrderStatus status = OrderStatus.PENDING;
    protected OrderSpecification orderSpecification;

    public AbstractOrderBuilder(String id) {
        this.id = id;
    }

    public T setQuantity(int quantity) {
        this.quantity = quantity;
        return self();
    }

    public T setStatus(OrderStatus status) {
        this.status = status;
        return self();
    }

    public T setSpecification(OrderSpecification orderSpecification) {
        this.orderSpecification = orderSpecification;
        return self();
    }

    protected abstract T self();
}