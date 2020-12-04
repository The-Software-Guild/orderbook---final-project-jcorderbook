/*
package com.jessica.orderbook.service;

import com.jessica.orderbook.dao.impl.OrderDaoImpl;
import com.jessica.orderbook.exceptions.NoOrderFoundException;
import com.jessica.orderbook.model.Order;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OrderBookServiceTest {
	private OrderBookService service;

	@Before
	public void setUp() {
		service = new OrderBookService(new OrderDaoImpl(),
			new ProductDaoImpl(), new TaxDaoImpl());
	}

	@Test
	public void testGetOrders() throws Exception {
		assertEquals(1, service.getAllOrders(LocalDate.of(2222, 12, 12)).size());
	}

	@Test(expected = NoOrderFoundException.class)
	public void testGetOrdersFailed() throws Exception {
		service.getAllOrders(LocalDate.of(1999, 1, 1));
	}

	@Test
	public void testCalculateCosts() throws Exception {
		Order order1 = new Order();
		order1.setCustomerName("Hudson Inc.");
		order1.setState("OH");
		order1.setProductType("Wood");
		order1.setArea(new BigDecimal("200"));

		Order order2 = new Order();
		order2.setCustomerName("Hudson Inc.");
		order2.setState("OH");
		order2.setProductType("Wood");
		order2.setArea(new BigDecimal("200"));

		assertEquals(service.calculateAndUpdateOrder(order1, true),
			service.calculateAndUpdateOrder(order2, true));
	}

	@Test
	public void testAddOrder() throws Exception {
		Order order = new Order();
		order.setCustomerName("Hudson Inc.");
		order.setState("OH");
		order.setProductType("Wood");
		order.setArea(new BigDecimal("200"));
		order.setOrderDate(LocalDate.of(2021, 12, 12));
		service.calculateAndUpdateOrder(order, false);
		service.addToMemory(order);
		final Map<Integer, Order> allOrders = service.getAllOrders(LocalDate.of(2021, 12, 12));
		final List<Order> orders = new ArrayList<>(allOrders.values());
		assertTrue(orders.contains(order));
	}

	@Test
	public void testUpdateOrder() throws Exception {
		final Map<Integer, Order> allOrders1 = service.getAllOrders(LocalDate.of(2021, 12, 12));
		final List<Order> orders = new ArrayList<>(allOrders1.values());
		Order order = new Order();
		if (orders.size() > 0) {
			order = orders.get(0);
		}
		order.setCustomerName("New Hudson Inc.");
		service.updateOrder(order, LocalDate.of(2021, 12, 12));
		final Map<Integer, Order> allOrders = service.getAllOrders(LocalDate.of(2021, 12, 12));
		final List<Order> orders2 = new ArrayList<>(allOrders.values());
		assertTrue(orders2.contains(order));
	}

	@Test
	public void testRemoveOrder() throws Exception {
		Order order = new Order();
		order.setOrderDate(LocalDate.of(2021, 12, 12));
		order.setOrderNumber(4);
		service.remove(order);
		final Map<Integer, Order> allOrders = service.getAllOrders(LocalDate.of(2021, 12, 12));
		final List<Order> orders = new ArrayList<>(allOrders.values());
		assertFalse(orders.contains(order));
	}

	@Test
	public void testAllProductInfo() throws Exception {
		final Map<String, Product> allProducts = service.getAllProducts();
		assertNotNull(allProducts);
	}

	@Test
	public void testAllTaxInfo() throws Exception {
		final Map<String, Tax> allTaxInfo = service.getAllTaxInfo();
		assertNotNull(allTaxInfo);
	}
}
*/
