package com.jessica.orderbook.controller;

import com.jessica.orderbook.exceptions.NoOrderFoundException;
import com.jessica.orderbook.exceptions.NoTradeFoundException;
import com.jessica.orderbook.model.Order;
import com.jessica.orderbook.model.Trade;
import com.jessica.orderbook.service.OrderBookService;
import com.jessica.orderbook.view.OrderBookView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * The type Order Book controller.
 */
@Component
public class OrderBookController {
	private final OrderBookService service;
	private final OrderBookView view;
	private static final String ORDER_SUMMARY = "* * * * * * * * * * * * * Order Summary * * * * * * * * * * * * *";

	/**
	 * Instantiates a new Order Book controller.
	 *
	 * @param view    OrderBookView view
	 * @param service OrderBookService
	 */
	@Autowired
	public OrderBookController(
		OrderBookView view, OrderBookService service) {
		this.view = view;
		this.service = service;
	}

	/**
	 * Simulate.
	 */
	public void simulate() {
		boolean isContinue = true;
		int userChoice;
		while (isContinue) {
			try {
				view.displayMainMenu(); //display main menu
				userChoice = view.getUserSelection(); //get and validate user input
				switch (userChoice) {
					case 1:
						displayOrders();
						break;
					case 2:
						isEmptyOrder();
						break;
					case 3:
						displayStats();
						break;
					case 4:
						matchOne();
						break;
					case 5:
						matchAll();
						break;
					case 6:
						addBuyOrder();
						break;
					case 7:
						addSellOrder();
						break;
					case 8:
						findTrade();
						break;
					case 9:
						getAllTrades();
						break;
					case 10:
						exit();
						isContinue = false;
						break;
				}

			} catch (Exception e) {
				System.out.println("Error occurred: " + e.getMessage());
			}
		}
	}

	private void getAllTrades() throws NoTradeFoundException {
		final List<Trade> allTrades = service.getAllTrades();
		if (!allTrades.isEmpty()) {
			view.displayTrades(allTrades);
		} else {
			throw new NoTradeFoundException("No Trades found");
		}
	}

	private void findTrade() throws NoTradeFoundException {
		final String input = view.getInput("Please Enter the Trade Id to be found");
		final Trade trade = service.getFindTrade(input);
		if (trade != null) {
			view.displayTrades(Collections.singletonList(trade));
		} else {
			throw new NoTradeFoundException("No Trades found");
		}
	}

	private void addSellOrder() {
		Order order = service.generateSellOrder();
		view.flush(ORDER_SUMMARY);
		view.flush(order.toString());
		service.addOrder(order);
		view.orderSuccess();
	}

	private void addBuyOrder() {
		Order order = service.generateBuyOrder();
		view.flush(ORDER_SUMMARY);
		view.flush(order.toString());
		service.addOrder(order);
		view.orderSuccess();
	}

	private void matchAll() throws Exception {
		List<Order> buyOrders = service.getBuyOrders();
		List<Order> sellOrders = service.getSellOrders();
		while (!buyOrders.isEmpty()) {
			Order topBuyOrder;
			topBuyOrder = buyOrders.get(0);
			if (doTrade(sellOrders, topBuyOrder)) {
				return;
			}
			buyOrders = service.getBuyOrders();
			sellOrders = service.getSellOrders();
		}
	}

	private boolean doTrade(List<Order> sellOrders, Order topBuyOrder) throws Exception {
		Order topSellOrder = null;
		Trade trade;
		if (sellOrders.size() > 0) {
			topSellOrder = sellOrders.get(0);
		}
		if (topBuyOrder != null && topSellOrder != null) {
			trade = getTrade(topSellOrder, topBuyOrder);
		} else {
			view.flush("No More Buy/Sell Orders Found");
			return true;
		}
		service.addTrade(trade);
		view.trade(trade);
		return false;
	}

	private Trade getTrade(Order topSellOrder, Order topBuyOrder) throws Exception {
		if (topSellOrder != null && topBuyOrder != null) {
			Trade trade;
			trade = new Trade();
			trade.setPrice(topSellOrder.getPrice());
			trade.setExecutionTime(LocalDateTime.now());
			int tradeQuantity;
			if (topSellOrder.getQuantity() > topBuyOrder.getQuantity()) {
				service.removeOrder(topBuyOrder);
				topSellOrder.setQuantity(topSellOrder.getQuantity() - topBuyOrder.getQuantity());
				service.updateOrder(topSellOrder);
				tradeQuantity = topBuyOrder.getQuantity();
			} else if (topSellOrder.getQuantity() < topBuyOrder.getQuantity()) {
				service.removeOrder(topSellOrder);
				topBuyOrder.setQuantity(topBuyOrder.getQuantity() - topSellOrder.getQuantity());
				service.updateOrder(topBuyOrder);
				tradeQuantity = topSellOrder.getQuantity();
			} else {
				service.removeOrder(topSellOrder);
				service.removeOrder(topBuyOrder);
				tradeQuantity = topSellOrder.getQuantity();
			}
			trade.setQuantity(tradeQuantity);
			return trade;
		}
		throw new Exception("Trade not possible");
	}

	private void matchOne() throws Exception {
		List<Order> buyOrders = service.getBuyOrders();
		List<Order> sellOrders = service.getSellOrders();
		Order topBuyOrder = null;
		if (buyOrders.size() > 0) {
			topBuyOrder = buyOrders.get(0);
		}
		doTrade(sellOrders, topBuyOrder);
	}

	private void displayStats() throws NoOrderFoundException {
		List<Order> buyOrders = service.getBuyOrders();
		List<Order> sellOrders = service.getSellOrders();
		if (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
			view.displayStats(buyOrders, sellOrders);
		} else {
			throw new NoOrderFoundException("No order found");
		}
	}

	private void isEmptyOrder() {
		List<Order> buyOrders = service.getBuyOrders();
		List<Order> sellOrders = service.getSellOrders();
		if (buyOrders.isEmpty() || sellOrders.isEmpty()) {
			view.flush("Buy/Sell Orders are empty!");
		} else {
			view.flush("Buy/Sell Orders are not empty!");
		}
	}

	private void displayOrders() throws NoOrderFoundException {
		List<Order> buyOrders = service.getBuyOrders();
		List<Order> sellOrders = service.getSellOrders();
		if (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
			view.displayAllOrders(buyOrders, sellOrders);
		} else {
			throw new NoOrderFoundException("No order found");
		}
	}

	private void exit() {
		service.exit();
		view.exit();
	}
}
