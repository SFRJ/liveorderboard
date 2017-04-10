package services;

import model.SummaryElement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

public class SummaryOutputFormaterTest {

    private List<SummaryElement> summaryOfSellOrders = new ArrayList<>();
    private List<SummaryElement> summaryOfBuyOrders = new ArrayList<>();

    private SummaryOutputFormater summaryOutputFormater = new SummaryOutputFormater();

    @Test
    public void shouldContainSellOrders() throws Exception {

        summaryOfSellOrders.add(new SummaryElement(1.2D,310));

        String output = summaryOutputFormater.formatOutput(summaryOfSellOrders, summaryOfBuyOrders);

        assertThat(output).isEqualTo("1.2 kg for £310" + lineSeparator());
    }

    @Test
    public void shouldContainBuyOrders() throws Exception {

        summaryOfBuyOrders.add(new SummaryElement(1.2D,310));

        String output = summaryOutputFormater.formatOutput(summaryOfSellOrders, summaryOfBuyOrders);

        assertThat(output).isEqualTo("1.2 kg for £310" + lineSeparator());
    }

    @Test
    public void shouldContainBuyAndSellOrders() throws Exception {

        summaryOfSellOrders.add(new SummaryElement(1.2D,310));
        summaryOfBuyOrders.add(new SummaryElement(1.2D,310));

        String output = summaryOutputFormater.formatOutput(summaryOfSellOrders, summaryOfBuyOrders);

        assertThat(output).isEqualTo("1.2 kg for £310" + lineSeparator() + "1.2 kg for £310" + lineSeparator());
    }

    @Test
    public void shouldDisplayBuyOrdersWithBiggerPricesFirst() throws Exception {
        givenSomeUnorderedBuyOrders();

        String output = summaryOutputFormater.formatOutput(summaryOfSellOrders, summaryOfBuyOrders);

        assertThat(output).isEqualTo(
                "1.0 kg for £105" + lineSeparator() +
                        "1.0 kg for £103" + lineSeparator() +
                        "1.0 kg for £101" + lineSeparator()
        );
    }

    private void givenSomeUnorderedSellOrders() {
        summaryOfSellOrders.add(new SummaryElement(1.0D, 105));
        summaryOfSellOrders.add(new SummaryElement(1.0D, 101));
        summaryOfSellOrders.add(new SummaryElement(1.0D, 103));
    }

    private void givenSomeUnorderedBuyOrders() {
        summaryOfBuyOrders.add(new SummaryElement(1.0D, 101));
        summaryOfBuyOrders.add(new SummaryElement(1.0D, 105));
        summaryOfBuyOrders.add(new SummaryElement(1.0D, 103));
    }
}