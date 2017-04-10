package services;

import model.Order;
import org.junit.Test;
import services.OrdersRegistry;

import static org.assertj.core.api.Assertions.assertThat;

public class OrdersRegistryTest {

    private OrdersRegistry ordersRegistry = new OrdersRegistry();

    @Test
    public void shouldAddOrder() throws Exception {
        givenAnOrderExists(new Order());
        assertThat(ordersRegistry.orders().size()).isEqualTo(1);
    }

    @Test
    public void shouldRemoveOrder() throws Exception {
        Order someOrder = new Order();
        givenAnOrderExists(someOrder);

        ordersRegistry.remove(someOrder);

        assertThat(ordersRegistry.orders().size()).isEqualTo(0);
    }

    private void givenAnOrderExists(Order order) {
        ordersRegistry.add(order);
    }


}