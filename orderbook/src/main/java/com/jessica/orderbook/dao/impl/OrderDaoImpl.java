package com.jessica.orderbook.dao.impl;

import com.jessica.orderbook.dao.OrderDao;
import com.jessica.orderbook.model.Order;
import com.jessica.orderbook.model.OrderType;
import com.jessica.orderbook.util.FileUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Order dao.
 */
@Component
public class OrderDaoImpl implements OrderDao {

	private static final List<Order> allOrders;

	public OrderDaoImpl() {
	}

	static {
		try {
			allOrders = FileUtils.loadOrders();
		} catch (IOException e) {
			throw new RuntimeException("Could not read the file");
		}
	}

	@Override
	public List<Order> getBuyOrders() {
		return allOrders.stream().filter(order -> order.getOrderType() == OrderType.BUY)
			       .collect(Collectors.toList());
	}

	@Override
	public List<Order> getSellOrders() {
		return allOrders.stream().filter(order -> order.getOrderType() == OrderType.SELL)
			       .collect(Collectors.toList());
	}

	/*@Override
	public void addOrder(Order order) throws IOException {
		FileUtils.add(order);
	}

	@Override
	public void removeOrder(Order order) throws IOException {
		FileUtils.remove(order);
	}

	@Override
	public void updateOrder(Order order, LocalDate userDate) {
		String fileName = userDate.format(DateTimeFormatter.ofPattern(MMDDYYYY));
		FileUtils.updateOrder(order, fileName);
	}

	@Override
	public void exportOrders() throws IOException {
		FileUtils.exportOrders();
	}*/
}
