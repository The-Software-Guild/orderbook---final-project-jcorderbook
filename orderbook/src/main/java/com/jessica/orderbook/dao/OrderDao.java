package com.jessica.orderbook.dao;

import com.jessica.orderbook.model.Order;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * The interface Order dao.
 */
public interface OrderDao {

	/**
	 * Gets Buy orders.
	 *
	 * @return the orders
	 */
	List<Order> getBuyOrders();


	/**
	 * Gets Buy orders.
	 *
	 * @return the orders
	 */
	List<Order> getSellOrders();

	/**
	 * Add order.
	 *
	 * @param order the order
	 * @throws IOException the io exception
	 *//*
	void addOrder(Order order) throws IOException;

	*//**
	 * Remove order.
	 *
	 * @param order the order
	 * @throws IOException the io exception
	 *//*
	void removeOrder(Order order) throws IOException;

	*//**
	 * Save to file.
	 *
	 * @param order    the order
	 * @param userDate the user date
	 * @throws URISyntaxException the uri syntax exception
	 *//*
	void updateOrder(Order order, LocalDate userDate) throws URISyntaxException;

	*//**
	 * Backup orders.
	 *
	 * @throws IOException the io exception
	 *//*
	void exportOrders() throws IOException;*/
}
