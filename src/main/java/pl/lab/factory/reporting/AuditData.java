package pl.lab.factory.reporting;

public record AuditData(String orderId, String boardType, int quantity, String statusCode) {}