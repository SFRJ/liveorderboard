package services;

import model.Order;
import model.SummaryElement;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static model.OrderType.BUY;
import static model.OrderType.SELL;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderMergerTest {

    private Map<Integer,List<Order>> orders = new HashMap<>();
    private OrderMerger orderMerger = new OrderMerger();

    @Test
    public void shouldMergeOrdersWithSamePrice() throws Exception {
        givenTwoOrdersWithSameTypeAndPrice();

        List<SummaryElement> mergeResult = orderMerger.mergeOrdersWithSamePrice(orders);

        SummaryElement mergedOrder = mergeResult.get(0);

        assertThat(mergeResult.size()).isEqualTo(1);
        assertThat(mergedOrder.getPrice()).isEqualTo(306);
        assertThat(mergedOrder.getQuantity()).isEqualTo(5.5);
    }

    @Test
    public void shouldMergeThreeOrdersWithTheSameTypeAndPrice() throws Exception {
        givenThreeOrdersWithSameTypeAndPrice();

        List<SummaryElement> mergeResult = orderMerger.mergeOrdersWithSamePrice(orders);

        SummaryElement mergedOrder = mergeResult.get(0);

        assertThat(mergeResult.size()).isEqualTo(1);
        assertThat(mergedOrder.getPrice()).isEqualTo(306);
        assertThat(mergedOrder.getQuantity()).isEqualTo(3.0);
    }

    @Test
    public void shouldNotMergeOrdersOfDifferentTypes() throws Exception {

        givenTwoOrdersWithSamePriceButDifferentType();

        List<SummaryElement> mergeResult = orderMerger.mergeOrdersWithSamePrice(orders);

        assertThat(mergeResult.size()).isEqualTo(2);
    }

    private void givenTwoOrdersWithSameTypeAndPrice() {
        orders.put(306, asList(
                new Order("user1", 3.5D, 306, SELL),
                new Order("user2", 2.0D, 306, SELL)
        ));

    }

    private void givenThreeOrdersWithSameTypeAndPrice() {
        orders.put(306, asList(
                new Order("user1", 1.0D, 306, SELL),
                new Order("user2", 1.0D, 306, SELL),
                new Order("user2", 1.0D, 306, SELL)
        ));
    }

    private void givenTwoOrdersWithSamePriceButDifferentType() {
        orders.put(2, asList(
                new Order("user4", 2.0D, 306, BUY)
        ));
        orders.put(1, asList(
                new Order("user1", 3.5D, 306, SELL)
        ));
    }


}