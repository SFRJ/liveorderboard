package services;

import model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersRegistry implements Registry {

    private List<Order> orders = new ArrayList<>();

    @Override
    public void add(Order order) {
        orders.add(order);
    }

    @Override
    public void remove(Order order) {
        orders.remove(order);
    }

    @Override
    public List<Order> orders() {
        return orders;
    }

}
