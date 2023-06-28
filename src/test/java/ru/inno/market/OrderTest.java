package ru.inno.market;

import org.junit.Before;
import org.junit.Test;
import ru.inno.market.model.Category;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.Order;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderTest {

    private Order order;
    private Client client;
    private Item item;

    @Before
    public void setUp() {

        client = new Client(1, "Dima");
        item = new Item(1, "Apple iPhone SE", Category.SMARTPHONES, 97990);
        order = new Order(1, client);
    }

    @Test
    public void testAddItem() {

        int initialCartSize = order.getCart().size();

        order.addItem(item);

        assertEquals(initialCartSize + 1, order.getCart().size());
        assertEquals(1, (int) order.getCart().get(item));
    }

    @Test
    public void testApplyDiscount() {

        double initialTotalPrice = order.getTotalPrice();
        double discount = 0.2;

        order.applyDiscount(discount);

        assertEquals(initialTotalPrice * (1 - discount), order.getTotalPrice(), 0.001);
        assertTrue(order.isDiscountApplied());
    }

    @Test
    public void testGetClient() {

        Client result = order.getClient();

        assertEquals(client, result);
    }

    @Test
    public void testGetCart() {

        order.addItem(item);

        Map<Item, Integer> result = order.getCart();

        Map<Item, Integer> expected = new HashMap<>();
        expected.put(item, 1);
        assertEquals(expected, result);
    }

    @Test
    public void testGetTotalPrice() {

        order.addItem(item);

        double result = order.getTotalPrice();

        assertEquals(item.getPrice(), result, 0.001);
    }

    @Test
    public void testIsDiscountApplied() {

        assertFalse(order.isDiscountApplied());
    }
}

