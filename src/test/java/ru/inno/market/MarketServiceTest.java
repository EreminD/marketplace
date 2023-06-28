package ru.inno.market;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.inno.market.core.Catalog;
import ru.inno.market.core.MarketService;
import ru.inno.market.model.*;

import static org.junit.Assert.*;

public class MarketServiceTest {

    private MarketService marketService;
    private Catalog catalog;

    @Before
    public void setUp() {
        marketService = new MarketService();
        catalog = new Catalog();
    }

    @Test
    public void testCreateOrderFor() {

        Client client = new Client(1, "Dima");
        int orderId = marketService.createOrderFor(client);

        assertEquals(0, orderId);
    }

    @Test
    public void testAddItemToOrder() {

        Client client = new Client(1, "Dima");
        Item item = catalog.getItemById(1);
        int orderId = marketService.createOrderFor(client);
        marketService.addItemToOrder(item, orderId);
        Order order = marketService.getOrderInfo(orderId);

        assertEquals(1, order.getCart().size());
        assertEquals(item, order.getCart().keySet().iterator().next());
    }

    @Test
    public void testApplyDiscountForOrder() {

        Client client = new Client(1, "Dima");
        Item item = catalog.getItemById(1);
        int orderId = marketService.createOrderFor(client);
        marketService.addItemToOrder(item, orderId);

        double totalPrice = marketService.applyDiscountForOrder(orderId, PromoCodes.FIRST_ORDER);
        Order order = marketService.getOrderInfo(orderId);

        assertEquals(item.getPrice() * 0.8, totalPrice, 0.001);
        assertTrue(order.isDiscountApplied());
    }

    @Test
    public void testGetOrderInfo() {

        Client client = new Client(1, "Dima");
        Item item = catalog.getItemById(1);
        int orderId = marketService.createOrderFor(client);
        marketService.addItemToOrder(item, orderId);

        Order order = marketService.getOrderInfo(orderId);

        assertEquals(orderId, order.getId());
        assertEquals(client, order.getClient());
        assertEquals(1, order.getCart().size());
        assertEquals(item, order.getCart().keySet().iterator().next());
        assertEquals(item.getPrice(), order.getTotalPrice(), 0.001);
        assertFalse(order.isDiscountApplied());
    }
    @Test
    public void testGetCountForItem() {

        Catalog catalog = new Catalog();

        Item item = new Item(1, "Apple iPhone SE", Category.SMARTPHONES, 97990);

        int count = catalog.getCountForItem(item);

        Assertions.assertEquals(10, count);
    }

}
