package com.jessica.orderbook.controller;

import com.jessica.orderbook.exceptions.NoOrderFoundException;
import com.jessica.orderbook.model.Order;
import com.jessica.orderbook.service.OrderBookService;
import com.jessica.orderbook.view.OrderBookView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * The type Order Book controller.
 */
@Component
public class OrderBookController {
	private final OrderBookView view;
	private final OrderBookService service;

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
						//addOrder();
						break;
					case 3:
						//editOrder();
						break;
					case 4:
						//removeOrder();
						break;
					case 5:
						//exportAllOrders();
						break;
					case 6:
						exit();
						isContinue = false;
						break;
				}

			} catch (Exception e) {
				System.out.println("Error occurred: " + e.getMessage());
			}
		}
	}

	private void exportAllOrders() throws IOException {
		//service.backupOrders();
		view.flush("Date Exported Successfully!");
	}

	/*private void removeOrder() throws NoOrderFoundException, IOException, URISyntaxException {
		LocalDate userDate = view.getDateFromUser();
		int orderNumber = Integer.parseInt(view.getInput("Please enter the order Number"));
		Map<Integer, Order> allOrders = service.getAllOrders(userDate);
		Order order = allOrders.get(orderNumber);
		if (order != null) {
			view.flush(ORDER_SUMMARY);
			view.flush(order.toString());
			String response = view.askToDelete();
			if (response.equalsIgnoreCase("Y")) {
				service.remove(order);
				view.orderSuccessDeleted();
			}
		} else {
			throw new NoOrderFoundException("No order found for given date or" +
				                                " Order Number: " + userDate + " " + orderNumber);
		}
	}*/

	/*private void editOrder() throws Exception {
		try {
			LocalDate userDate = view.getDateFromUser();
			int orderNumber = Integer.parseInt(view.getInput("Please enter the order Number"));
			Map<Integer, Order> allOrders = service.getAllOrders(userDate);
			Order order = allOrders.get(orderNumber);
			if (order != null) {
				order = view.getEditedOrder(order);
				order = service.calculateAndUpdateOrder(order, true);
				view.flush(ORDER_SUMMARY);
				view.flush(order.toString());
				String response = view.askToSave();
				if (response.equalsIgnoreCase("Y")) {
					service.updateOrder(order, userDate);
					view.orderSuccess();
				}
			} else {
				throw new NoOrderFoundException("No order found for given date or" +
					                                " Order Number: " + userDate + " " + orderNumber);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Please enter a valid order number", e);
		}
	}
*/
	/*private void addOrder() throws Exception {
		Order order = view.getOrder();
		order = service.calculateAndUpdateOrder(order, false);
		view.flush(ORDER_SUMMARY);
		view.flush(order.toString());
		String response = view.askToSave();
		if (response.equalsIgnoreCase("Y")) {
			service.addToMemory(order);
			view.orderSuccess();
		}
	}*/

	private void displayOrders() throws NoOrderFoundException {
		List<Order> buyOrders = service.getBuyOrders();
		List<Order> sellOrders = service.getSellOrders();
		buyOrders.sort(Comparator.comparing(Order::getPrice));
		sellOrders.sort(Comparator.comparing(Order::getPrice));
		if (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
			view.displayAllOrders(buyOrders, sellOrders);
		} else {
			throw new NoOrderFoundException("No order found");
		}
	}

	private void exit() {
		view.exit();
	}
}
