package model;

public class Order {

    private String userId;
    private double quantity;
    private int pricePerKilo;
    private OrderType orderType;

    public Order() {
    }

    public Order(String userId, double quantity, int pricePerKilo, OrderType orderType) {
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

    public OrderType getOrderType() {
        return orderType;
    }
}
