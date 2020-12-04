package com.jessica.orderbook.service;

import com.jessica.orderbook.dao.OrderDao;
import com.jessica.orderbook.model.Order;
import com.jessica.orderbook.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * The type Order Book service.
 */
@Service
public class OrderBookService {
	private final OrderDao orderDao;

	/**
	 * Instantiates a new Order Book service.
	 *
	 * @param orderDao the order dao
	 */
	@Autowired
	public OrderBookService(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	/**
	 * Gets all Buy orders.
	 *
	 * @return the all orders
	 */
	public List<Order> getBuyOrders() {
		return orderDao.getBuyOrders();
	}

	/**
	 * Gets all Sell orders.
	 *
	 * @return the all orders
	 */
	public List<Order> getSellOrders() {
		return orderDao.getSellOrders();
	}

	/**
	 * Gets all orders.
	 *
	 * @return the all orders
	 */
	public Map<String, Order> getAllOrders() {
		return orderDao.getAllOrders();
	}

	/**
	 * Remove order.
	 *
	 * @param topBuyOrder the top buy order
	 */
	public void removeOrder(Order topBuyOrder) {
		orderDao.removeOrder(topBuyOrder);
	}

	/**
	 * Update order.
	 *
	 * @param topSellOrder the top sell order
	 */
	public void updateOrder(Order topSellOrder) {
		orderDao.updateOrder(topSellOrder);
	}

	/**
	 * Add trade.
	 *
	 * @param trade the trade
	 */
	public void addTrade(Trade trade) {
		orderDao.addTrade(trade);
	}

	/**
	 * Add order.
	 *
	 * @param order the order
	 */
	public void addOrder(Order order) {
		orderDao.addOrder(order);
	}

	/**
	 * Generate sell order order.
	 *
	 * @return the order
	 */
	public Order generateSellOrder() {
		return orderDao.generateSellOrder();
	}

	/**
	 * Generate buy order order.
	 *
	 * @return the order
	 */
	public Order generateBuyOrder() {
		return orderDao.generateBuyOrder();
	}

	/**
	 * Gets all trades.
	 *
	 * @return the all trades
	 */
	public List<Trade> getAllTrades() {
		return orderDao.getAllTrades();
	}

	/**
	 * Gets find trade.
	 *
	 * @param input the input
	 * @return the find trade
	 */
	public Trade getFindTrade(String input) {
		return orderDao.getTradeById(input);
	}

	/**
	 * Exit.
	 */
	public void exit() {
		orderDao.exit();
	}
}
