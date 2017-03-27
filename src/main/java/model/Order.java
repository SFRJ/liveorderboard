package model;

public class Order {

    private String userId;
    private double quantity;
    private int pricePerKilo;
    private String orderType;

    public Order() {
    }

    public Order(String userId, double quantity, int pricePerKilo, String orderType) {
        this.userId = userId;
        this.quantity = quantity;
        this.pricePerKilo = pricePerKilo;
        this.orderType = orderType;
    }

    public String getUserId() {
        return userId;
    }

    public double getQuantity() {
        return quantity;
    }

    public int getPricePerKilo() {
        return pricePerKilo;
    }

    public String getOrderType() {
        return orderType;
    }
}
