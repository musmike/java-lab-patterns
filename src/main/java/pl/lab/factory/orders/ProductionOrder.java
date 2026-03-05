package pl.lab.factory.orders;

public abstract class ProductionOrder {
    protected String id;
    protected int quantity;
    protected OrderStatus status;

    public ProductionOrder(String id, int quantity, OrderStatus status) {
        this.id = id;
        this.quantity = quantity;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
