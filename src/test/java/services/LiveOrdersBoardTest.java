package services;

import model.Order;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

public class LiveOrdersBoardTest {

    private List<Order> ordersRegistry = mock(List.class);
    private LiveOrdersBoard liveOrdersBoard;

    @Test
    public void shouldRegisterAnOrder() throws Exception {
        givenThereAreNoOrdersRegistered();

        liveOrdersBoard.register(new Order("user1", 3.5D, 306, "SELL"));

        assertThat(liveOrdersBoard.allOrders()).isNotEmpty();
    }

    @Test
    public void shouldCancelOrder() throws Exception {
        givenAnOrderInTheLiveOrdersBoard();

        liveOrdersBoard.cancel("user1", "BUY", 303D);

        assertThat(liveOrdersBoard.allOrders()).isEmpty();
    }

    @Test
    public void shouldNotCancelOrder() throws Exception {
        givenAnOrderInTheLiveOrdersBoard();

        liveOrdersBoard.cancel("user1", "BORROW", 303D);

        assertThat(ordersRegistry).isNotEmpty();
    }

    @Test
    public void shouldMergeOrdersWithSamePrice() throws Exception {
        givenTwoOrdersWithSameTypeAndPrice();

        String summary = liveOrdersBoard.summary();

        assertThat(summary).isEqualTo(
                "5.5 kg for £306" + lineSeparator()
        );
    }

    @Test
    public void shouldMergeManyOrdersWithSamePrice() throws Exception {
        givenThreeOrdersWithSameTypeAndPrice();

        String summary = liveOrdersBoard.summary();

        assertThat(summary).isEqualTo(
                "3.0 kg for £306"+ lineSeparator()
        );
    }

    @Test
    public void shouldNotMergeOrdersOfSamePriceForDifferentOrderType() throws Exception {
        givenTwoOrdersWithSamePriceButDifferentType();

        String summary = liveOrdersBoard.summary();

        assertThat(summary).isEqualTo(
                "3.5 kg for £306" + lineSeparator() +
                "2.0 kg for £306" + lineSeparator()
        );
    }

    @Test
    public void shouldReturnSummary() throws Exception {
        givenALiveOrdersBoardWithSomeOrders();

        String summary = liveOrdersBoard.summary();

        assertThat(summary).isEqualTo(
                "5.5 kg for £306" + lineSeparator() +
                "1.5 kg for £307" + lineSeparator() +
                "1.2 kg for £310" + lineSeparator()
        );
    }

    @Test
    public void shouldDisplaySellOrdersWithSmallestPricesFirst() throws Exception {
        givenSomeUnorderedSellOrders();

        String summary = liveOrdersBoard.summary();

        assertThat(summary).isEqualTo(
                "1.0 kg for £101" + lineSeparator() +
                "1.0 kg for £103" + lineSeparator() +
                "1.0 kg for £105" + lineSeparator()
        );
    }

    @Test
    public void shouldDisplayBuyOrdersWithBiggerPricesFirst() throws Exception {
        givenSomeUnorderedBuyOrders();

        String summary = liveOrdersBoard.summary();

        assertThat(summary).isEqualTo(
                "1.0 kg for £105" + lineSeparator() +
                "1.0 kg for £103" + lineSeparator() +
                "1.0 kg for £101" + lineSeparator()
        );
    }

    private void givenAnOrderInTheLiveOrdersBoard() {
        ordersRegistry = new ArrayList<Order>() {{
            add(new Order("user1", 3.5D, 303, "BUY"));
        }};
        liveOrdersBoard = new LiveOrdersBoard(ordersRegistry);
    }

    private void givenALiveOrdersBoardWithSomeOrders() {
        ordersRegistry = new ArrayList<Order>() {{
            add(new Order("user1",3.5D,306,"SELL"));
            add(new Order("user2",1.2D,310,"SELL"));
            add(new Order("user3",1.5D,307,"SELL"));
            add(new Order("user4",2.0D,306,"SELL"));
        }};
        liveOrdersBoard = new LiveOrdersBoard(ordersRegistry);
    }

    private void givenTwoOrdersWithSameTypeAndPrice() {
        ordersRegistry = new ArrayList<Order>() {{
            add(new Order("user1",3.5D,306,"SELL"));
            add(new Order("user4",2.0D,306,"SELL"));
        }};
        liveOrdersBoard = new LiveOrdersBoard(ordersRegistry);
    }

    private void givenThreeOrdersWithSameTypeAndPrice() {
        ordersRegistry = new ArrayList<Order>() {{
            add(new Order("user1",1.0D,306,"SELL"));
            add(new Order("user2",1.0D,306,"SELL"));
            add(new Order("user3",1.0D,306,"SELL"));
        }};
        liveOrdersBoard = new LiveOrdersBoard(ordersRegistry);
    }

    private void givenTwoOrdersWithSamePriceButDifferentType() {
        ordersRegistry = new ArrayList<Order>() {{
            add(new Order("user4",2.0D,306,"BUY"));
            add(new Order("user1",3.5D,306,"SELL"));
        }};
        liveOrdersBoard = new LiveOrdersBoard(ordersRegistry);
    }

    private void givenSomeUnorderedSellOrders() {
        ordersRegistry = new ArrayList<Order>() {{
            add(new Order("user1",1.0D,105,"SELL"));
            add(new Order("user2",1.0D,101,"SELL"));
            add(new Order("user3",1.0D,103,"SELL"));
        }};
        liveOrdersBoard = new LiveOrdersBoard(ordersRegistry);
    }

    private void givenSomeUnorderedBuyOrders() {
        ordersRegistry = new ArrayList<Order>() {{
            add(new Order("user2",1.0D,101,"BUY"));
            add(new Order("user1",1.0D,105,"BUY"));
            add(new Order("user3",1.0D,103,"BUY"));
        }};
        liveOrdersBoard = new LiveOrdersBoard(ordersRegistry);
    }

    private void givenThereAreNoOrdersRegistered() {
        ordersRegistry = new ArrayList<>();
        liveOrdersBoard = new LiveOrdersBoard(ordersRegistry);
    }


}