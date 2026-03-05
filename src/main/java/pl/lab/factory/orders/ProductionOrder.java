package pl.lab.factory.orders;

public class ProductionOrder {
    private final String internalRef;
    private final int quantity;
    private final OrderType orderType;
    private OrderStatus status;

    public ProductionOrder(String internalRef, int quantity, OrderType orderType, OrderStatus status) {
        this.internalRef = internalRef;
        this.quantity = quantity;
        this.orderType = orderType;
        this.status = status;
    }

    public String getInternalRef() {
        return internalRef;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
