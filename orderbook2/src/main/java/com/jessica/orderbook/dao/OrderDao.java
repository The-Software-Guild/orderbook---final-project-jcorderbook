package com.jessica.orderbook.dao;

import com.jessica.orderbook.model.Order;
import com.jessica.orderbook.model.Trade;

import java.util.List;
import java.util.Map;

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
	 * Gets all orders.
	 *
	 * @return the all orders
	 */
	Map<String, Order> getAllOrders();

	/**
	 * Remove order.
	 *
	 * @param topBuyOrder the top buy order
	 */
	void removeOrder(Order topBuyOrder);

	/**
	 * Update order.
	 *
	 * @param topSellOrder the top sell order
	 */
	void updateOrder(Order topSellOrder);

	/**
	 * Add trade.
	 *
	 * @param trade the trade
	 */
	void addTrade(Trade trade);

	/**
	 * Gets trade by id.
	 *
	 * @param tradeId the trade id
	 * @return the trade by id
	 */
	Trade getTradeById(String tradeId);

	/**
	 * Add order.
	 *
	 * @param order the order
	 */
	void addOrder(Order order);

	/**
	 * Generate sell order order.
	 *
	 * @return the order
	 */
	Order generateSellOrder();

	/**
	 * Generate buy order order.
	 *
	 * @return the order
	 */
	Order generateBuyOrder();

	/**
	 * Gets all trades.
	 *
	 * @return the all trades
	 */
	List<Trade> getAllTrades();

	/**
	 * Exit.
	 */
	void exit();
}
