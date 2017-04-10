package services;

import model.Order;
import model.SummaryElement;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static model.OrderType.BUY;
import static model.OrderType.SELL;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LiveOrdersBoardTest {

    private List<Order> ordersRegistry = new ArrayList<>();
    private SummaryOutputFormater summaryOutputFormater = mock(SummaryOutputFormater.class);

    private LiveOrdersBoard liveOrdersBoard;

    @Test
    public void shouldRegisterAnOrder() throws Exception {
        givenANewOrderIsRegistered();
        ArgumentCaptor<ArrayList> argumentCaptor = ArgumentCaptor.forClass(ArrayList.class);

        liveOrdersBoard.summary();

        verify(summaryOutputFormater).formatOutput(argumentCaptor.capture(), anyList());
        SummaryElement capturedSummaryBeforeBeingFormatted = ((ArrayList<SummaryElement>) argumentCaptor.getValue()).get(0);

        assertThat(capturedSummaryBeforeBeingFormatted.getQuantity()).isEqualTo(3.5D);
        assertThat(capturedSummaryBeforeBeingFormatted.getPrice()).isEqualTo(306);
    }

    @Test
    public void shouldCancelOrder() throws Exception {
        givenAnOrderInTheLiveOrdersBoard();

        liveOrdersBoard.cancel("user1", BUY, 303D);

        assertThat(liveOrdersBoard.summary()).isNullOrEmpty();
    }

    @Test
    public void shouldNotCancelOrder() throws Exception {
        givenAnOrderInTheLiveOrdersBoard();

        liveOrdersBoard.cancel("user1", SELL, 303D);

        assertThat(ordersRegistry).isNotEmpty();
    }

 /*   @Test
    public void shouldMergeOrdersWithSamePrice() throws Exception {
        givenTwoOrdersWithSameTypeAndPrice();

        String summary = liveOrdersBoard.summary();

        assertThat(summary).isEqualTo("5.5 kg for £306" + lineSeparator()
        );
    }

    @Test
    public void shouldMergeManyOrdersWithSamePrice() throws Exception {
        givenThreeOrdersWithSameTypeAndPrice();

        String summary = liveOrdersBoard.summary();

        assertThat(summary).isEqualTo(
                "3.0 kg for £306" + lineSeparator()
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
    }*/

    private void givenAnOrderInTheLiveOrdersBoard() {
        ordersRegistry.add(new Order("user1", 3.5D, 303, BUY));

        liveOrdersBoard = new LiveOrdersBoard(summaryOutputFormater, ordersRegistry);
    }

    private void givenTwoOrdersWithSameTypeAndPrice() {
        ordersRegistry.add(new Order("user1", 3.5D, 306, SELL));
        ordersRegistry.add(new Order("user4", 2.0D, 306, SELL));

        liveOrdersBoard = new LiveOrdersBoard(summaryOutputFormater, ordersRegistry);
    }

    private void givenThreeOrdersWithSameTypeAndPrice() {
        ordersRegistry.add(new Order("user2", 1.0D, 306, SELL));
        ordersRegistry.add(new Order("user1", 1.0D, 306, SELL));
        ordersRegistry.add(new Order("user3", 1.0D, 306, SELL));

        liveOrdersBoard = new LiveOrdersBoard(summaryOutputFormater, ordersRegistry);
    }

    private void givenTwoOrdersWithSamePriceButDifferentType() {
        ordersRegistry.add(new Order("user4", 2.0D, 306, BUY));
        ordersRegistry.add(new Order("user1", 3.5D, 306, SELL));
        liveOrdersBoard = new LiveOrdersBoard(summaryOutputFormater, ordersRegistry);
    }


    private void givenANewOrderIsRegistered() {
        liveOrdersBoard = new LiveOrdersBoard(summaryOutputFormater, ordersRegistry);
        liveOrdersBoard.register(new Order("user1", 3.5D, 306, SELL));
    }


}