package com.jessica.orderbook.service;

import com.jessica.orderbook.dao.impl.OrderDaoImpl;
import com.jessica.orderbook.model.Order;
import com.jessica.orderbook.model.OrderType;
import com.jessica.orderbook.model.Trade;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OrderBookServiceTest {
	private OrderBookService service;

	@Before
	public void setUp() {
		service = new OrderBookService(new OrderDaoImpl());
	}

	@Test
	public void testGetOrders() {
		final Map<String, Order> allOrders = service.getAllOrders();
		assertNotNull(allOrders);
	}

	@Test
	public void testAddOrder() {
		Order order = new Order();
		order.setQuantity(10);
		order.setPrice(190.000);
		order.setOrderType(OrderType.SELL);
		order.setOrderID("SORD1181");
		service.addOrder(order);
		final List<Order> allOrders = service.getSellOrders();
		assertTrue(allOrders.contains(order));
	}

	@Test
	public void testUpdateOrder() {
		final List<Order> orders1 = service.getBuyOrders();
		if (orders1.size() > 0) {
			Order order = orders1.get(0);
			order.setQuantity(100);
			service.updateOrder(order);
			final List<Order> allOrders = service.getBuyOrders();
			assertTrue(allOrders.contains(order));
		}
	}

	@Test
	public void testRemoveOrder() {
		final List<Order> orders1 = service.getBuyOrders();
		if (orders1.size() > 0) {
			Order order = orders1.get(0);
			service.removeOrder(order);
			final List<Order> allOrders = service.getBuyOrders();
			assertFalse(allOrders.contains(order));
		}
	}

	@Test
	public void testAllTrades() {
		final List<Trade> allTrades = service.getAllTrades();
		assertNotNull(allTrades);
	}
}
