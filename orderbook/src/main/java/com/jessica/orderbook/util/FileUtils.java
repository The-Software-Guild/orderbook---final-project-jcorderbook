package com.jessica.orderbook.util;

import com.jessica.orderbook.model.Order;
import com.jessica.orderbook.model.OrderType;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type File Utils.
 */
public final class FileUtils {
	private static final String DELIMITER = "::";
	/**
	 * The constant MMDDYYYY.
	 */
	public static final String MMDDYYYY = "MMddyyyy";
	/**
	 * The constant MM_DD_YYYY.
	 */
	public static final String MM_DD_YYYY = "MM-dd-yyyy";
	private static final String ORDERS_FOLDER = "orders";
	private static final String ORDER_HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType," +
		                                           "Area,CostPerSquareFoot,LaborCostPerSquareFoot," +
		                                           "MaterialCost,LaborCost,Tax,Total";
	private static final String ORDER_HEADER_WITH_DATE = ORDER_HEADER + ",OrderDate";

	/**
	 * Load orders from file.
	 *
	 * @return the orders
	 * @throws IOException the io exception
	 */
	public static List<Order> loadOrders() throws IOException {
		List<Order> orders = new ArrayList<>();
		//read all lines except the header
		final List<String> lines = readValues(Paths.get("orders.txt"));
		lines.forEach(line -> {
			final Order order = readOrder(line);
			if (order != null) {
				orders.add(order);
			}
		});
		return orders;
	}

	/**
	 * Save to file.
	 */
	/*public static void updateOrder(Order order, String fileName) {
		String orderLine = getOrderLine(order);
		try {
			final String URI = ORDERS_FOLDER + File.separator + "Orders_" + fileName + ".txt";
			final List<String> allOrders =
				Files.readAllLines(Paths.get(URI), Charset.defaultCharset());
			int index = -1;
			for (int i = 0; i < allOrders.size(); i++) {
				if (i > 0) {
					final String orderNum = allOrders.get(i).split(",")[0];
					if (orderNum != null && Integer.parseInt(orderNum) == order.getOrderNumber()) {
						index = i;
						break;
					}
				}
			}
			allOrders.set(index, orderLine);
			writeToFileWithUpdate(URI, allOrders);
		} catch (IOException e) {
			System.out.println("Could not save into the file " + e.getMessage());
		}
	}*/
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

	/**
	 * Is order file present boolean.
	 *
	 * @param fileName the file name
	 * @return the boolean
	 */
	public static boolean isOrderFilePresent(String fileName) {
		boolean isValid = false;
		final File[] files = new File(ORDERS_FOLDER).listFiles();
		if (files != null) {
			isValid = Arrays.stream(files)
				          .filter(File::isFile)
				          .anyMatch(file -> file.getName().equals("Orders_" + fileName + ".txt"));
		}
		return isValid;
	}

	/**
	 * Load Tax Info from file.
	 *
	 * @return the map
	 * @throws IOException        the io exception
	 * @throws URISyntaxException the uri syntax exception
	 */
	/*public static Map<String, Tax> loadTaxInfo() throws IOException, URISyntaxException {
		Map<String, Tax> result = new HashMap<>();
		final URL URL = FileUtils.class.getClassLoader().getResource("taxes.txt");
		if (URL != null) {
			final List<String> lines = readValues(Paths.get(URL.toURI()));
			lines.forEach(line -> {
				final Tax tax = readTaxInfo(line);
				if (tax != null) {
					result.put(tax.getStateAbbr(), tax);
				}
			});
		} else {
			throw new FileNotFoundException("Could not load state and tax information");
		}
		return result;
	}*/

	/*private static Tax readTaxInfo(String line) {
		String[] values = line.split(DELIMITER);
		if (values.length == 2) {
			Tax tax = new Tax();
			tax.setStateAbbr(values[0]);
			tax.setTaxRate(new BigDecimal(values[1]));
			return tax;
		}
		return null;
	}*/

	/**
	 * Load Product Info from file.
	 *
	 * @return the map
	 * @throws IOException        the io exception
	 * @throws URISyntaxException the uri syntax exception
	 */
	/*public static Map<String, Product> loadProductInfo() throws IOException, URISyntaxException {
		Map<String, Product> result = new HashMap<>();
		final URL URL = FileUtils.class.getClassLoader().getResource("products.txt");
		if (URL != null) {
			final List<String> lines = readValues(Paths.get(URL.toURI()));
			lines.forEach(line -> {
				final Product product = readProductInfo(line);
				if (product != null) {
					result.put(product.getProductType(), product);
				}
			});
		}
		return result;
	}*/

	/*private static Product readProductInfo(String line) {
		String[] values = line.split(DELIMITER);
		if (values.length == 3) {
			Product product = new Product();
			product.setProductType(values[0]);
			product.setCostPerSquareFoot(new BigDecimal(values[1]));
			product.setLaborCostPerSquareFoot(new BigDecimal(values[2]));
			return product;
		}
		return null;
	}*/

	/**
	 * Gets max order number.
	 *
	 * @return the max order number
	 * @throws IOException the io exception
	 */
	public static int getMaxOrderNumber() throws IOException {
		int maxOrderNumber = -1;
		try {
			final File[] files = new File(ORDERS_FOLDER).listFiles();
			if (files != null) {
				for (File file : files) {
					final List<String> allLines = readValues(Paths.get(file.getPath()));
					for (String line : allLines) {
						final String[] split = line.split(",");
						maxOrderNumber = Math.max(maxOrderNumber, Integer.parseInt(split[0]));
					}
				}
			}
		} catch (Exception e) {
			throw new IOException("Could not find max order number", e);
		}
		return maxOrderNumber;
	}

	private static List<String> readValues(Path path) throws IOException {
		return Files.readAllLines(path, Charset.defaultCharset());
	}

	/**
	 * Backup orders.
	 *
	 * @throws IOException the io exception
	 */
	/*public static void exportOrders() throws IOException {
		List<Order> allOrders = new LinkedList<>();
		try {
			final File[] files = new File(ORDERS_FOLDER).listFiles();
			if (files != null) {
				for (File file : files) {
					final List<String> allLines = readValues(Paths.get(file.getPath()));
					allLines.forEach(line -> {
						final Order order = readOrder(line);
						if (order != null) {
							final String name = file.getName();
							String date = name.substring(name.indexOf("_") + 1, name.indexOf("."));
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MMDDYYYY);
							order.setOrderDate(LocalDate.parse(date, formatter));
							allOrders.add(order);
						}
					});
				}
			}
			dumpToFile(allOrders);
		} catch (Exception e) {
			throw new IOException("Could not find max order number", e);
		}
	}*/

	/*private static void dumpToFile(List<Order> allOrders) {
		List<String> lines = new LinkedList<>();
		allOrders.sort(Comparator.comparing(Order::getOrderNumber));
		lines.add(ORDER_HEADER_WITH_DATE);
		for (Order order : allOrders) {
			String orderLine = getOrderLine(order) + DELIMITER
				                   + orderDateToFileName(order.getOrderDate(), MM_DD_YYYY);
			lines.add(orderLine);
		}
		try {
			final String URI = "Backup" + File.separator + "DataExport.txt";
			writeToFileWithUpdate(URI, lines);
		} catch (IOException e) {
			System.out.println("Could not save into the file " + e.getMessage());
		}
	}*/

	/**
	 * Add.
	 *
	 * @throws IOException the io exception
	 */
	/*public static void add(Order order) throws IOException {
		final LocalDate orderDate = order.getOrderDate();
		final String format = orderDateToFileName(orderDate, MMDDYYYY);
		final String URI = renderFileName(order.getOrderDate());
		final boolean orderFilePresent = isOrderFilePresent(format);
		String orderLine = getOrderLine(order);
		if (orderFilePresent) {
			Files.write(Paths.get(URI), Collections.singletonList(orderLine), StandardOpenOption.APPEND);
		} else {
			List<String> lines = new LinkedList<>();
			lines.add(ORDER_HEADER);
			lines.add(orderLine);
			writeToFileWithUpdate(URI, lines);
		}
	}*/
	private static String orderDateToFileName(LocalDate orderDate, String format) {
		return orderDate.format(DateTimeFormatter.ofPattern(format));
	}

	/*private static String getOrderLine(Order order) {
		return order.getOrderNumber() + DELIMITER
			       + order.getCustomerName() + DELIMITER
			       + order.getState() + DELIMITER
			       + order.getTaxRate() + DELIMITER
			       + order.getProductType() + DELIMITER
			       + order.getArea() + DELIMITER
			       + order.getCostPerSquareFoot() + DELIMITER
			       + order.getLaborCostPerSquareFoot() + DELIMITER
			       + order.getMaterialCost() + DELIMITER
			       + order.getLaborCost() + DELIMITER
			       + order.getTax() + DELIMITER
			       + order.getTotal();
	}*/

	private static void writeToFileWithUpdate(String URI, List<String> lines) throws IOException {
		Files.write(Paths.get(URI), lines, Charset.defaultCharset(),
			StandardOpenOption.CREATE,
			StandardOpenOption.TRUNCATE_EXISTING);
	}

	/**
	 * Remove.
	 *
	 * @throws IOException the io exception
	 */
	/*public static void remove(Order order) throws IOException {
		final String URI = renderFileName(order.getOrderDate());
		final List<String> allLines = readValues(Paths.get(URI));
		List<String> newLines = new LinkedList<>();
		newLines.add(ORDER_HEADER);
		for (String allLine : allLines) {
			final String[] split = allLine.split(",");
			if (Integer.parseInt(split[0]) != order.getOrderNumber()) {
				newLines.add(allLine);
			}
		}
		writeToFileWithUpdate(URI, newLines);
	}*/
	private static String renderFileName(LocalDate orderDate) {
		final String format = orderDateToFileName(orderDate, MMDDYYYY);
		return ORDERS_FOLDER + File.separator + "Orders_" + format + ".txt";
	}
}
