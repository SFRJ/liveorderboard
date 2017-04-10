package services;

import model.Order;
import model.SummaryElement;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static model.OrderType.BUY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

public class LiveOrdersBoardTest {

    private Registry ordersRegistry = mock(Registry.class);
    private Merger merger = mock(Merger.class);
    private Formater formater = mock(Formater.class);

    private Order someOrder;
    private LiveOrdersBoard liveOrdersBoard = new LiveOrdersBoard(ordersRegistry,merger,formater);

    @Test
    public void shouldRegisterAnOrder() throws Exception {
        givenSomeOrder();

        liveOrdersBoard.register(someOrder);
        verify(ordersRegistry).add(someOrder);
    }

    @Test
    public void shouldCancelOrder() throws Exception {
        givenAnOrderExistsInTheRegistry();

        liveOrdersBoard.cancel("id",BUY,1D);
        verify(ordersRegistry).update(emptyList());
    }

    @Test
    public void shouldReturnASummary() throws Exception {
        givenAnOrderExistsInTheRegistry();
        SummaryElement summaryElement = new SummaryElement(someOrder.getQuantity(), someOrder.getPricePerKilo());

        when(ordersRegistry.orders()).thenReturn(asList(someOrder));
        when(merger.mergeOrdersWithSamePrice(anyMap())).thenReturn(asList(summaryElement));
        when(formater.formatOutput(anyList(), anyList())).thenReturn("1 kg for £1");


        String summary = liveOrdersBoard.summary();
        assertThat(summary).isEqualTo("1 kg for £1");
    }


    private void givenAnOrderExistsInTheRegistry() {
       someOrder = new Order("id", 1D, 1, BUY);
    }

    private void givenSomeOrder() {
        someOrder = new Order();
    }


}