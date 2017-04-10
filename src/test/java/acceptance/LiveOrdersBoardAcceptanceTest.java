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

    private void givenOrdersExistInRegistry() {
        registry.add(ordersFixtureBuilder.withOrderType(SELL).withQuantity(3.5D).withPricePerKilo(306).withUserId("[user1]").build());
        registry.add(ordersFixtureBuilder.withOrderType(SELL).withQuantity(1.2D).withPricePerKilo(310).withUserId("[user2]").build());
        registry.add(ordersFixtureBuilder.withOrderType(SELL).withQuantity(1.5D).withPricePerKilo(307).withUserId("[user3]").build());
        registry.add(ordersFixtureBuilder.withOrderType(SELL).withQuantity(2.0D).withPricePerKilo(306).withUserId("[user4]").build());
    }

    private void whenIRequestASummary() {
        summary = liveOrdersBoard.summary();
    }

    private void thenTheSummaryIsCorrectlyFormated() {
        assertThat(summary).isEqualTo(
                "5.5 kg for £306" + lineSeparator() +
                "1.5 kg for £307" + lineSeparator() +
                "1.2 kg for £310" +lineSeparator());
    }
}
