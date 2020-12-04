package com.jessica.orderbook.util;

import com.jessica.orderbook.model.Order;
import com.jessica.orderbook.model.OrderType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * The type File Utils.
 */
public final class FileUtils {
	private static final String DELIMITER = "::";
	private static final String ORDERS_TXT = "orderss.txt";

	/**
	 * Load orders from file.
	 *
	 * @return the orders
	 * @throws IOException the io exception
	 */
	public static Map<String, Order> loadOrders() throws IOException {
		Map<String, Order> orders = new HashMap<>();
		//read all lines except the header
		final List<String> lines = readValues(Paths.get(ORDERS_TXT));
		lines.forEach(line -> {
			final Order order = readOrder(line);
			if (order != null) {
				orders.put(order.getOrderID(), order);
			}
		});
		return orders;
	}

	private static Order readOrder(String line) {
		String[] values = line.split(DELIMITER);
		if (values.length == 4) {
			Order order = new Order();
			order.setOrderType(OrderType.valueOf(values[0]));
			order.setOrderID(values[1]);
			order.setPrice(Double.parseDouble(values[2]));
			order.setQuantity(Integer.parseInt(values[3]));
			return order;
		}
		return null;
	}

	private static List<String> readValues(Path path) throws IOException {
		return Files.readAllLines(path, Charset.defaultCharset());
	}

	/**
	 * Save.
	 *
	 * @param allOrders the all orders
	 */
	public static void save(List<Order> allOrders) {
		List<String> lines = new LinkedList<>();
		allOrders.sort(Comparator.comparing(Order::getOrderID));
		for (Order order : allOrders) {
			String orderLine = getOrderLine(order);
			lines.add(orderLine);
		}
		try {
			writeToFileWithUpdate(lines);
		} catch (IOException e) {
			System.out.println("Could not save into the file " + e.getMessage());
		}
	}

	private static String getOrderLine(Order order) {
		return order.getOrderType() + DELIMITER
			       + order.getOrderID() + DELIMITER
			       + order.getPrice() + DELIMITER
			       + order.getQuantity();
	}

	private static void writeToFileWithUpdate(List<String> lines) throws IOException {
		Files.write(Paths.get(ORDERS_TXT), lines, Charset.defaultCharset(),
			StandardOpenOption.CREATE,
			StandardOpenOption.TRUNCATE_EXISTING);
	}
}
