package services;

import model.SummaryElement;

import java.util.List;

public interface Formater {

    String formatOutput(List<SummaryElement> summaryOfSellOrders, List<SummaryElement> summaryOfBuyOrders);
}
