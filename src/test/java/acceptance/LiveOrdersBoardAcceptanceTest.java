package acceptance;

import model.Order;
import org.junit.Test;
import services.*;

import static java.lang.System.lineSeparator;
import static model.OrderType.SELL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LiveOrdersBoardAcceptanceTest {

    private Registry registry = new OrdersRegistry();
    private Merger merger = new OrderMerger();
    private Formater formater = new SummaryOutputFormater();
    private OrdersFixtureBuilder ordersFixtureBuilder = new OrdersFixtureBuilder();

    private String summary;
    private LiveOrdersBoard liveOrdersBoard = new LiveOrdersBoard(registry, merger, formater);

    @Test
    public void shouldSumarizeCorrectlyTheInformation() throws Exception {
        givenOrdersExistInRegistry();
        whenIRequestASummary();
        thenTheSummaryIsCorrectlyFormated();
    }

    @Test
    public void shouldCancelOrder() throws Exception {
        givenOrdersExistInRegistry();
        andOneOfTheOrdersIsCanceled();
        whenIRequestASummary();
        thenTheSummaryDoesNotContainTheCancelledOrder();
    }

    private void givenOrdersExistInRegistry() {
        liveOrdersBoard.register(ordersFixtureBuilder.withOrderType(SELL).withQuantity(3.5D).withPricePerKilo(306).withUserId("[user1]").build());
        liveOrdersBoard.register(ordersFixtureBuilder.withOrderType(SELL).withQuantity(1.2D).withPricePerKilo(310).withUserId("[user2]").build());
        liveOrdersBoard.register(ordersFixtureBuilder.withOrderType(SELL).withQuantity(1.5D).withPricePerKilo(307).withUserId("[user3]").build());
        liveOrdersBoard.register(ordersFixtureBuilder.withOrderType(SELL).withQuantity(2.0D).withPricePerKilo(306).withUserId("[user4]").build());
    }

    private void andOneOfTheOrdersIsCanceled() {
        liveOrdersBoard.cancel("[user1]", SELL, 306);
    }

    private void whenIRequestASummary() {
        summary = liveOrdersBoard.summary();
    }

    private void thenTheSummaryIsCorrectlyFormated() {
        assertThat(summary).isEqualTo(
                "5.5 kg for £306" + lineSeparator() +
                        "1.5 kg for £307" + lineSeparator() +
                        "1.2 kg for £310" + lineSeparator());
    }

    private void thenTheSummaryDoesNotContainTheCancelledOrder() {
        assertThat(summary).isEqualTo(
                "2.0 kg for £306" + lineSeparator() +
                "1.5 kg for £307" + lineSeparator() +
                "1.2 kg for £310" + lineSeparator());
    }
}