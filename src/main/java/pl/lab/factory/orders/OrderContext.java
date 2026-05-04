package pl.lab.factory.orders;

public record OrderContext(String id, int quantity, OrderSpecification spec) {}