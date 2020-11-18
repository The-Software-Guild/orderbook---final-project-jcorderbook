/*
package com.jessica.orderbook.dao;

import com.jessica.orderbook.dao.impl.OrderDaoImpl;
import com.jessica.orderbook.exceptions.NoOrderFoundException;
import com.jessica.orderbook.model.Order;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderDaoImplTest {
	private OrderDao dao;

	@Before
	public void setUp() {
		dao = new OrderDaoImpl();
	}

	@Test
	public void testGetOrders() throws Exception {
		assertEquals(1, dao.getBuyOrders(LocalDate.of(1001, 1, 1)).size());
	}

	@Test(expected = NoOrderFoundException.class)
	public void testGetOrdersFailed() throws Exception {
		dao.getBuyOrders(LocalDate.of(4345, 4, 19));
	}

	@Test
	public void testAddOrder() throws IOException, NoOrderFoundException, URISyntaxException {
		Order order = new Order();
		order.setCustomerName(" Company");
		order.setState("IN");
		order.setTaxRate(new BigDecimal("8.00"));
		order.setProductType("Laminate");
		order.setArea(new BigDecimal("200"));
		order.setCostPerSquareFoot(new BigDecimal("1.75"));
		order.setLaborCostPerSquareFoot(new BigDecimal("2.10"));
		order.setMaterialCost(order.getCostPerSquareFoot()
			                      .multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
		order.setLaborCost(order.getLaborCostPerSquareFoot().multiply(order.getArea())
			                   .setScale(2, RoundingMode.HALF_UP));
		order.setTax(order.getTaxRate().divide(new BigDecimal("100.00"))
			             .multiply((order.getMaterialCost().add(order.getLaborCost())))
			             .setScale(2, RoundingMode.HALF_UP));
		order.setTotal(order.getMaterialCost().add(order.getLaborCost())
			               .add(order.getTax()));
		order.setOrderDate(LocalDate.of(2021, 12, 12));
		dao.addOrder(order);
		final Map<Integer, Order> allOrders = dao.getBuyOrders(LocalDate.of(2021, 12, 12));
		final List<Order> orders = new ArrayList<>(allOrders.values());
		assertTrue(orders.contains(order));
	}

	@Test
	public void testRemoveOrder() throws IOException, NoOrderFoundException, URISyntaxException {
		Order order = new Order();
		order.setCustomerName(" Company");
		order.setState("IN");
		order.setTaxRate(new BigDecimal("8.00"));
		order.setProductType("Laminate");
		order.setArea(new BigDecimal("200"));
		order.setCostPerSquareFoot(new BigDecimal("1.75"));
		order.setLaborCostPerSquareFoot(new BigDecimal("2.10"));
		order.setMaterialCost(order.getCostPerSquareFoot()
			                      .multiply(order.getArea()).setScale(2, RoundingMode.HALF_UP));
		order.setLaborCost(order.getLaborCostPerSquareFoot().multiply(order.getArea())
			                   .setScale(2, RoundingMode.HALF_UP));
		order.setTax(order.getTaxRate().divide(new BigDecimal("100.00"))
			             .multiply((order.getMaterialCost().add(order.getLaborCost())))
			             .setScale(2, RoundingMode.HALF_UP));
		order.setTotal(order.getMaterialCost().add(order.getLaborCost())
			               .add(order.getTax()));
		order.setOrderDate(LocalDate.of(2021, 12, 12));
		dao.removeOrder(order);
		final Map<Integer, Order> allOrders = dao.getBuyOrders(LocalDate.of(2021, 12, 12));
		final List<Order> orders = new ArrayList<>(allOrders.values());
		assertFalse(orders.contains(order));
	}

	@Test
	public void testUpdateOrder() throws IOException, NoOrderFoundException, URISyntaxException {
		final Map<Integer, Order> orders1 = dao.getBuyOrders(LocalDate.of(2021, 12, 12));
		final List<Order> orders2 = new ArrayList<>(orders1.values());
		if (orders2.size() > 0) {
			Order order = orders2.get(0);
			order.setCustomerName("New Name");
			dao.updateOrder(order, LocalDate.of(2021, 12, 12));
			final Map<Integer, Order> allOrders = dao.getBuyOrders(LocalDate.of(2021, 12, 12));
			final List<Order> orders = new ArrayList<>(allOrders.values());
			assertTrue(orders.contains(order));
		}
	}
}

*/
