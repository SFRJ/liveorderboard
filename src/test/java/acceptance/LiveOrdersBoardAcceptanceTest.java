package acceptance;

import services.*;

public class LiveOrdersBoardAcceptanceTest {

    private Registry registry = new OrdersRegistry();
    private Merger merger = new OrderMerger();
    private Formater formater = new SummaryOutputFormater();

    private LiveOrdersBoard liveOrdersBoard = new LiveOrdersBoard(registry, merger, formater);


}
