package services;

import model.SummaryElement;

import java.util.List;

import static java.lang.System.lineSeparator;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.joining;

public class SummaryOutputFormater implements Formater {

    public String formatOutput(List<SummaryElement> summaryOfSellOrders, List<SummaryElement> summaryOfBuyOrders) {
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

}
