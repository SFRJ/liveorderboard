package acceptance;

import model.Order;
import model.OrderType;

public class OrdersFixtureBuilder {

    private String userId;
    private double quantity;
    private int pricePerKilo;
    private OrderType orderType;

    public OrdersFixtureBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public  OrdersFixtureBuilder withQuantity(double quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrdersFixtureBuilder withPricePerKilo(int pricePerKilo) {
        this.pricePerKilo = pricePerKilo;
        return this;
    }

    public OrdersFixtureBuilder withOrderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public Order build() {
        return new Order(userId, quantity, pricePerKilo, orderType);
    }
}
