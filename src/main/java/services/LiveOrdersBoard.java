package services;

import model.Order;
import model.OrderType;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.stream.Collectors.*;
import static model.OrderType.SELL;

public class LiveOrdersBoard {

    private Merger merger;
    private Formater formater;
    private Registry ordersRegistry;

    public LiveOrdersBoard(Registry ordersRegistry, Merger merger, Formater formater) {
        this.formater = formater;
        this.ordersRegistry = ordersRegistry;
        this.merger = merger;
    }

    public void register(Order order) {
            ordersRegistry.add(order);
    }

    public void cancel(String userId, OrderType orderType, double pricePerKilo) {

        List<Order> ordersToCancel = ordersRegistry.orders().stream()
                .filter(orderFindingCriteria(userId, orderType, pricePerKilo))
                .collect(Collectors.toList());

        if(ordersToCancel.size() == 1)
            ordersRegistry.remove(ordersToCancel.get(0));
    }

    public String summary() {
        Map<Boolean, List<Order>> allOrders = ordersRegistry.orders().stream().collect(
                partitioningBy(orderType()));

        List<Order> sellOrders = allOrders.get(TRUE);
        List<Order> buyOrders = allOrders.get(FALSE);

        Map<Integer, List<Order>> sellOrdersGroupedByPrice = sellOrders.stream().collect(groupingBy(Order::getPricePerKilo));
        Map<Integer, List<Order>> buyOrdersGroupedByPrice = buyOrders.stream().collect(groupingBy(Order::getPricePerKilo));

        return formater.formatOutput(merger.mergeOrdersWithSamePrice(sellOrdersGroupedByPrice), merger.mergeOrdersWithSamePrice(buyOrdersGroupedByPrice));
    }

    private Predicate<Order> orderType() {
        return o -> o.getOrderType().equals(SELL);
    }

    private Predicate<Order> orderFindingCriteria(String userId, OrderType orderType, double pricePerKilo) {
        return o -> o.getUserId().equals(userId) &&
                o.getOrderType().equals(orderType) &&
                o.getPricePerKilo() == pricePerKilo;
    }

}
