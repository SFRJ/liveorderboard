package services;

import model.Order;
import model.SummaryElement;

import java.util.List;
import java.util.Map;

public interface Merger {

    List<SummaryElement> mergeOrdersWithSamePrice(Map<Integer, List<Order>> ordersGroupedByPrice);
}
