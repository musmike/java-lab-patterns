package pl.lab.factory.orders;

public abstract class ProductionOrder {
    protected String id;
    protected int quantity;
    protected OrderStatus status;
    protected OrderSpecification specification;

    public ProductionOrder(String id, int quantity, OrderStatus status, OrderSpecification specification) {
        this.id = id;
        this.quantity = quantity;
        this.status = status;
        this.specification = specification;
    }

    public String getId() { return id; }

    public int getQuantity() { return quantity; }

    public OrderStatus getStatus() { return status; }

    public OrderSpecification getSpecification() { return specification; }

    public void setStatus(OrderStatus status) { this.status = status; }

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Prototype 3
    public abstract ProductionOrder clone(String newId);
    // Koniec, Tydzień 2
    // Wzorzec Prototype 3

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " | id: " + id
                + " | qty: " + quantity
                + " | status: " + status
                + (specification != null ? " | spec: " + specification : "");
    }
}
