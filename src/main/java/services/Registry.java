package services;

import model.Order;

import java.util.List;

public interface Registry {

    void add(Order order);
    void remove(Order order);
    List<Order> orders();
}
