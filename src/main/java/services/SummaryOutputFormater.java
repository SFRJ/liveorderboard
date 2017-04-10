package services;

import model.SummaryElement;

import java.util.Comparator;
import java.util.List;

import static java.lang.System.lineSeparator;
import static java.util.Collections.sort;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.joining;

public class SummaryOutputFormater implements Formater {

    public String formatOutput(List<SummaryElement> summaryOfSellOrders, List<SummaryElement> summaryOfBuyOrders) {
        String sellSummary = summaryOfSellOrders.stream().sorted(comparingInt(SummaryElement::getPrice))
                .map(e -> e.getQuantity() + " kg for £" + e.getPrice() + lineSeparator()).collect(joining());

        sort(summaryOfBuyOrders, Comparator.comparing(p -> -p.getPrice()));
        String buySummary = summaryOfBuyOrders.stream()
                .map(e -> e.getQuantity() + " kg for £" + e.getPrice() + lineSeparator()).collect(joining());

        return sellSummary + buySummary;
    }

}
