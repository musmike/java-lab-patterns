package pl.lab.factory.reporting;

public class AuditData {
    private final String orderId;
    private final String boardType;
    private final int quantity;
    private final String statusCode;

    private AuditData(Builder builder) {
        this.orderId = builder.orderId;
        this.boardType = builder.boardType;
        this.quantity = builder.quantity;
        this.statusCode = builder.statusCode;
    }

    public String getOrderId() { return orderId; }
    public String getBoardType() { return boardType; }
    public int getQuantity() { return quantity; }
    public String getStatusCode() { return statusCode; }

    public static class Builder {
        private String orderId;
        private String boardType;
        private int quantity;
        private String statusCode;

        public Builder orderId(String orderId) { this.orderId = orderId; return this; }
        public Builder boardType(String boardType) { this.boardType = boardType; return this; }
        public Builder quantity(int quantity) { this.quantity = quantity; return this; }
        public Builder statusCode(String statusCode) { this.statusCode = statusCode; return this; }
        
        public AuditData build() { return new AuditData(this); }
    }
}