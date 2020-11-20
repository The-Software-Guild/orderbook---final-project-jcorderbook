package com.jessica.orderbook.dao;

import com.jessica.orderbook.dao.impl.OrderDaoImpl;
import com.jessica.orderbook.model.Order;
import com.jessica.orderbook.model.OrderType;
import com.jessica.orderbook.model.Trade;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OrderDaoImplTest {
	private OrderDao dao;

	@Before
	public void setUp() {
		dao = new OrderDaoImpl();
	}

	@Test
	public void testAddBuyOrder() {
		Order order = new Order();
		order.setQuantity(10);
		order.setPrice(190.000);
		order.setOrderType(OrderType.BUY);
		order.setOrderID("BORD1181");
		dao.addOrder(order);
		final List<Order> allOrders = dao.getBuyOrders();
		assertTrue(allOrders.contains(order));
	}

	@Test
	public void testAddSellOrder() {
		Order order = new Order();
		order.setQuantity(10);
		order.setPrice(190.000);
		order.setOrderType(OrderType.SELL);
		order.setOrderID("SORD1181");
		dao.addOrder(order);
		final List<Order> allOrders = dao.getSellOrders();
		assertTrue(allOrders.contains(order));
	}

	@Test
	public void testUpdateOrder() {
		final List<Order> orders1 = dao.getBuyOrders();
		if (orders1.size() > 0) {
			Order order = orders1.get(0);
			order.setQuantity(100);
			dao.updateOrder(order);
			final List<Order> allOrders = dao.getBuyOrders();
			assertTrue(allOrders.contains(order));
		}
	}

	@Test
	public void testRemoveOrder() {
		final List<Order> orders1 = dao.getBuyOrders();
		if (orders1.size() > 0) {
			Order order = orders1.get(0);
			dao.removeOrder(order);
			final List<Order> allOrders = dao.getBuyOrders();
			assertFalse(allOrders.contains(order));
		}
	}

	@Test
	public void testAddTrade() {
		Trade trade = new Trade();
		trade.setExecutionTime(LocalDateTime.now());
		trade.setQuantity(20);
		trade.setPrice(190.11111);
		dao.addTrade(trade);
		final List<Trade> allTrades = dao.getAllTrades();
		assertTrue(allTrades.contains(trade));
	}

	@Test
	public void testGenerateSellOrder() {
		final Order order = dao.generateSellOrder();
		assertNotNull(order);
	}

	@Test
	public void testGenerateBuyOrder() {
		final Order order = dao.generateBuyOrder();
		assertNotNull(order);
	}
}
