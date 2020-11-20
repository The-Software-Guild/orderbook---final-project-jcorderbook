package com.jessica.orderbook.dao.impl;

import com.jessica.orderbook.dao.OrderDao;
import com.jessica.orderbook.model.Order;
import com.jessica.orderbook.model.OrderType;
import com.jessica.orderbook.model.Trade;
import com.jessica.orderbook.util.FileUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Order dao.
 */
@Component
public class OrderDaoImpl implements OrderDao {
	private static final int HIGH_PRICE = 191;
	private static final int LOW_PRICE = 190;
	private static final int HIGH_QTY = 25;
	private static final int LOW_QTY = 20;
	private static int TRADE_COUNTER = 0;
	private static int BUY_ORDER_COUNTER;
	private static int SELL_ORDER_COUNTER;
	private static final String TRADE_PREFIX = "TRADE-";
	private static final String BUY_ORDER_PREFIX = "BORD";
	private static final String SELL_ORDER_PREFIX = "SORD";
	private static final Map<String, Order> allOrders;
	private static final Map<String, Trade> allTrades = new HashMap<>();

	/**
	 * Instantiates a new Order dao.
	 */
	public OrderDaoImpl() {
	}

	static {
		try {
			allOrders = FileUtils.loadOrders();
			BUY_ORDER_COUNTER = allOrders.values().stream()
				                    .filter(order -> order.getOrderType() == OrderType.BUY)
				                    .map(Order::getOrderID)
				                    .mapToInt(key -> Integer.parseInt(key.substring(key.indexOf("D") + 1)))
				                    .max().getAsInt();
			SELL_ORDER_COUNTER = allOrders.values().stream()
				                     .filter(order -> order.getOrderType() == OrderType.SELL)
				                     .map(Order::getOrderID)
				                     .mapToInt(key -> Integer.parseInt(key.substring(key.indexOf("D") + 1)))
				                     .max().getAsInt();
		} catch (IOException e) {
			throw new RuntimeException("Could not read the file");
		}
	}

	@Override
	public List<Order> getBuyOrders() {
		return allOrders.values()
			       .stream().filter(order -> order.getOrderType() == OrderType.BUY)
			       .sorted(Comparator.comparing(Order::getPrice))
			       .collect(Collectors.toList());
	}

	@Override
	public List<Order> getSellOrders() {
		return allOrders.values().stream().filter(order -> order.getOrderType() == OrderType.SELL)
			       .sorted(Comparator.comparing(Order::getPrice))
			       .collect(Collectors.toList());
	}

	@Override
	public Map<String, Order> getAllOrders() {
		return allOrders;
	}

	@Override
	public void removeOrder(Order order) {
		allOrders.remove(order.getOrderID());
	}

	@Override
	public void updateOrder(Order order) {
		allOrders.put(order.getOrderID(), order);
	}

	@Override
	public void addTrade(Trade trade) {
		trade.setTradeId(TRADE_PREFIX + TRADE_COUNTER);
		allTrades.put(trade.getTradeId(), trade);
		TRADE_COUNTER++;
	}

	@Override
	public Trade getTradeById(String tradeId) {
		return allTrades.get(tradeId);
	}

	@Override
	public void addOrder(Order order) {
		allOrders.put(order.getOrderID(), order);
	}

	@Override
	public Order generateSellOrder() {
		Order order = new Order();
		order.setOrderType(OrderType.SELL);
		SELL_ORDER_COUNTER++;
		order.setOrderID(SELL_ORDER_PREFIX + SELL_ORDER_COUNTER);
		order.setPrice((Math.random() * (HIGH_PRICE - LOW_PRICE)) + LOW_PRICE);
		order.setQuantity((int) (Math.random() * (HIGH_QTY - LOW_QTY)) + LOW_QTY);
		return order;
	}

	@Override
	public Order generateBuyOrder() {
		Order order = new Order();
		order.setOrderType(OrderType.BUY);
		BUY_ORDER_COUNTER++;
		order.setOrderID(BUY_ORDER_PREFIX + BUY_ORDER_COUNTER);
		order.setPrice((Math.random() * (HIGH_PRICE - LOW_PRICE)) + LOW_PRICE);
		order.setQuantity((int) (Math.random() * (HIGH_QTY - LOW_QTY)) + LOW_QTY);
		return order;
	}

	@Override
	public List<Trade> getAllTrades() {
		final ArrayList<Trade> trades = new ArrayList<>(allTrades.values());
		trades.sort(Comparator.comparing(Trade::getExecutionTime));
		return trades;
	}

	/**
	 * Exit.
	 */
	@Override
	public void exit() {
		FileUtils.save(new ArrayList<>(allOrders.values()));
	}
}
