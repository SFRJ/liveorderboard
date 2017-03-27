package services;

import model.Order;
import model.SummaryElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.System.lineSeparator;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class LiveOrdersBoard {

    private List<Order> ordersRegistry;

    public LiveOrdersBoard(List<Order> ordersRegistry) {
        this.ordersRegistry = ordersRegistry;
    }

    public void register(Order order) {
            ordersRegistry.add(order);
    }

    public void cancel(String userId, String orderType, double pricePerKilo) {
        Map<Boolean, List<Order>> partitionedResults = ordersRegistry.stream().collect(
                partitioningBy(orderFindingCriteria(userId, orderType, pricePerKilo)));
        ordersRegistry = partitionedResults.get(FALSE);
    }

    public String summary() {
        Map<Boolean, List<Order>> allOrders = ordersRegistry.stream().collect(
                partitioningBy(orderType()));

        List<Order> sellOrders = allOrders.get(TRUE);
        List<Order> buyOrders = allOrders.get(FALSE);

        Map<Integer, List<Order>> sellOrdersGroupedByPrice = sellOrders.stream().collect(groupingBy(Order::getPricePerKilo));
        Map<Integer, List<Order>> buyOrdersGroupedByPrice = buyOrders.stream().collect(groupingBy(Order::getPricePerKilo));

        return formatOutput(mergeOrdersWithSamePrice(sellOrdersGroupedByPrice), mergeOrdersWithSamePrice(buyOrdersGroupedByPrice));
    }

    private List<SummaryElement> mergeOrdersWithSamePrice(Map<Integer, List<Order>> ordersGroupedByPrice) {

        List<SummaryElement> output = new ArrayList<>();

        for (Map.Entry<Integer, List<Order>> entries : ordersGroupedByPrice.entrySet()) {

            double mergedQuantities = 0D;

            for (Order order : entries.getValue()) {
                mergedQuantities += order.getQuantity();
            }

            output.add(new SummaryElement(mergedQuantities, entries.getKey()));
        }

        return output;
    }

    private Predicate<Order> orderType() {
        return o -> o.getOrderType().equals("SELL");
    }

    private Predicate<Order> orderFindingCriteria(String userId, String orderType, double pricePerKilo) {
        return o -> o.getUserId().equals(userId) &&
                o.getOrderType().equals(orderType) &&
                o.getPricePerKilo() == pricePerKilo;
    }

    private String formatOutput(List<SummaryElement> summaryOfSellOrders, List<SummaryElement> summaryOfBuyOrders) {
        String sellSummary = summaryOfSellOrders.stream()
                .map(e -> e.getQuantity() + " kg for £" + e.getPrice() + lineSeparator()).collect(joining());

        reverseBasedOnPrice(summaryOfBuyOrders);
        String buySummary = summaryOfBuyOrders.stream()
                .map(e -> e.getQuantity() + " kg for £" + e.getPrice() + lineSeparator()).collect(joining());

        return sellSummary + buySummary;
    }

    private void reverseBasedOnPrice(List<SummaryElement> summaryOfBuyOrders) {
        summaryOfBuyOrders.sort(comparingInt(SummaryElement::getPrice)
                .reversed());
    }

    public List<Order> allOrders() {
        return ordersRegistry;
    }
}
