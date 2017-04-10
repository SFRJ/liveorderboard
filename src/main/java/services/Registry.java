package services;

import model.Order;

import java.util.List;

public interface Registry {

    void add(Order order);
    List<Order> orders();
    void update(List<Order> orders);
}
