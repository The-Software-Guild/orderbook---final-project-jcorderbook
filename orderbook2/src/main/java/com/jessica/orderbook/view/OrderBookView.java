package com.jessica.orderbook.view;

import com.jessica.orderbook.model.Order;
import com.jessica.orderbook.model.Trade;
import com.jessica.orderbook.util.TableGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The type OrderBookView .
 */
@Component
public class OrderBookView {
	private static final String EMPTY = "";
	private final UserIO userIO;

	/**
	 * Instantiates a new Order book view.
	 *
	 * @param userIO the user io
	 */
	@Autowired
	public OrderBookView(UserIO userIO) {
		this.userIO = userIO;
	}

	/**
	 * Gets user selection.
	 *
	 * @return the user selection
	 */
	public int getUserSelection() {
		return userIO.validateAndGetSelection(1, 10);
	}

	/**
	 * Display main menu.
	 */
	public void displayMainMenu() {
		flush("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		flush("* <<Order Book Simulator>>");
		flush("* 1. Display");
		flush("* 2. Empty");
		flush("* 3. DisplayStats");
		flush("* 4. Match One");
		flush("* 5. Match All");
		flush("* 6. Add Buy order");
		flush("* 7. Add Sell order");
		flush("* 8. Find Trade");
		flush("* 9. Get All Trades");
		flush("* 10. Quit");
		flush("*");
		flush("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		flush("Please make a selection (1-10)");
	}

	/**
	 * Flush.
	 *
	 * @param msg the msg
	 */
	public void flush(String msg) {
		userIO.flush(msg);
	}

	/**
	 * Gets input.
	 *
	 * @param msg the msg
	 * @return the input
	 */
	public String getInput(String msg) {
		return userIO.getInputFromUser(msg);
	}

	/**
	 * Exit.
	 */
	public void exit() {
		flush("Thank you for using BookTracker!");
	}

	/**
	 * Display all orders.
	 *
	 * @param buyOrders  the buy orders
	 * @param sellOrders the sell orders
	 */
	public void displayAllOrders(List<Order> buyOrders, List<Order> sellOrders) {
		flush("* * * * * * * * * * * * OrderBook for Stock : Apple * * * * * * * * * * * *");

		int loop = Math.max(buyOrders.size(), sellOrders.size());
		int pageSize = 20;
		int divide = loop % 20;
		int totalPages;
		if (divide == 0) {
			totalPages = loop / 20;
		} else {
			totalPages = loop / 20 + 1;
		}
		int pageCount = 1;
		int fromIndex = 0;
		while (pageCount <= totalPages) {
			final boolean flag = printOrders(buyOrders, sellOrders, fromIndex, fromIndex + pageSize);
			if (!flag) {
				flush("* * * * * * * * * * AWAITING INPUT * * * * * * * * * * ");
				flush("Please press 1 to show next page or 2 to exit to main screen");
				final int selection = userIO.validateAndGetSelection(1, 2);
				if (selection == 2) {
					break;
				} else {
					fromIndex = fromIndex + pageSize;
				}
				pageCount++;
			} else {
				break;
			}
		}
	}

	private boolean printOrders(
		List<Order> buyOrders, List<Order> sellOrders, int startIndex, int endIndex) {
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>();
		headersList.add("Buy Orders");
		headersList.add("Sell Orders");
		int loop = Math.min(endIndex, buyOrders.size());
		List<List<String>> rowsList = new ArrayList<>();
		for (int i = startIndex; i < loop; i++) {
			List<String> row = new ArrayList<>();
			if (buyOrders.size() > i) {
				row.add(buyOrders.get(i).toString());
			} else {
				row.add(EMPTY);
			}
			if (sellOrders.size() > i) {
				row.add(sellOrders.get(i).toString());
			} else {
				row.add(EMPTY);
			}
			rowsList.add(row);
		}
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
		return buyOrders.size() < endIndex;
	}

	/**
	 * Order success.
	 */
	public void orderSuccess() {
		flush("Order has been added Successfully!");
	}

	/**
	 * Display stats.
	 *
	 * @param buyOrders  the buy orders
	 * @param sellOrders the sell orders
	 */
	public void displayStats(List<Order> buyOrders, List<Order> sellOrders) {
		flush("* * * * * * * * * * * * OrderBook Stats * * * * * * * * * * * *");
		flush("Number of Sell Orders : " + sellOrders.size());
		flush("Number of Buy Orders : " + buyOrders.size());
		flush("Overall Sell Quantity : " + sellOrders.stream().mapToInt(Order::getQuantity).sum());
		flush("Overall Buy Quantity : " + buyOrders.stream().mapToInt(Order::getQuantity).sum());
		flush("Average Sell Price : " + sellOrders.stream().mapToInt(Order::getQuantity).average().getAsDouble());
		flush("Average Buy Price : " + buyOrders.stream().mapToInt(Order::getQuantity).average().getAsDouble());
	}

	/**
	 * Trade.
	 *
	 * @param trade the trade
	 */
	public void trade(Trade trade) {
		flush("* * * * * * * * * * * * Trade Added * * * * * * * * * * * *");
		flush(trade.toString());
	}

	/**
	 * Display trades.
	 *
	 * @param allTrades the all trades
	 */
	public void displayTrades(List<Trade> allTrades) {
		flush("* * * * * * * * * * * * Trades So Far * * * * * * * * * * * *");
		allTrades.forEach(System.out::println);
	}
}
