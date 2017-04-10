package services;

import model.Order;
import model.SummaryElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderMerger implements Merger {

    public List<SummaryElement> mergeOrdersWithSamePrice(Map<Integer, List<Order>> ordersGroupedByPrice) {

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
}
