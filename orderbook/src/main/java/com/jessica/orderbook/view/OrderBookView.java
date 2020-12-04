package com.jessica.orderbook.view;

import com.jessica.orderbook.model.Order;
import com.jessica.orderbook.util.TableGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Flooring Mastery view.
 */
@Component
public class OrderBookView {
	private static final String DATE_MM_DD_YYYY = "Please enter the order date (MM-DD-YYYY)";
	private final UserIO userIO;
	//private final ValidationUtils validation;

	/**
	 * Instantiates a new Flooring mastery view.
	 *
	 * @param userIO the user io
	 */
	/*@Autowired
	public OrderBookView(UserIO userIO, ValidationUtils validation) {
		this.userIO = userIO;
		this.validation = validation;
	}*/
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
		return userIO.validateAndGetSelection();
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
		flush("* 8. Fill Full Buy Order");
		flush("* 9. Fill Full Sell Order");
		flush("* 10. Fill Partial Buy Order");
		flush("* 11. Fill Partial Sell Order");
		flush("* 12. Quit");
		flush("*");
		flush("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		flush("Please make a selection (1-12)");
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
		flush("Thank you for using SRG Flooring Mastery!");
	}

	/**
	 * Gets date from user.
	 *
	 * @return the date from user
	 */
	public LocalDate getDateFromUser() {
		return userIO.validateAndGetDate("Please enter order date (MM-DD-YYYY)");
	}

	/**
	 * Display all orders.
	 *
	 * @param buyOrders
	 */
	public void displayAllOrders(List<Order> buyOrders, List<Order> sellOrders) {
		flush("* * * * * * * * * * * * OrderBook for Stock : Apple * * * * * * * * * * * *");
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>();
		headersList.add("Buy Orders");
		headersList.add("Sell Orders");
		List<List<String>> rowsList = new ArrayList<>();
		for (int i = 0; i < buyOrders.size(); i++) {
			List<String> row = new ArrayList<>();
			row.add(buyOrders.get(i).toString());
			row.add(sellOrders.get(i).toString());
			rowsList.add(row);
		}
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

	/**
	 * Display all products.
	 *
	 * @param products the products
	 *//*
	public void displayAllProducts(List<Product> products) {
		flush("* * * * * * * * * * * * Available Product Types * * * * * * * * * * * *");
		products.forEach(System.out::println);
	}*/

	/**
	 * Gets order.
	 *
	 * @return the order
	 * @throws IOException        the io exception
	 * @throws URISyntaxException the uri syntax exception
	 *//*
	public Order getOrder() throws IOException, URISyntaxException {
		Order order = new Order();
		order.setOrderDate(getAndValidateOrderDate());
		order.setCustomerName(validateAndGetName());
		order.setState(validateAndGetStateName());
		order.setProductType(validateAndGetProductType());
		order.setArea(getAndValidateArea());
		return order;
	}*/

	/*private String validateAndGetProductType() throws IOException, URISyntaxException {
		final Map<String, Product> products = FileUtils.loadProductInfo();
		displayAllProducts(new ArrayList<>(products.values()));
		String productType = userIO.getInputFromUser("Please Choose Product Type");
		while (validation.isValidProductType(productType)) {
			productType = userIO.getInputFromUser("Please Choose Product Type");
		}
		return productType;
	}*/

	/*private String validateAndGetStateName() throws IOException, URISyntaxException {
		String stateName = userIO.getInputFromUser("Please Enter State Name (Abbreviation)");
		while (validation.isValidStateName(stateName)) {
			stateName = userIO.getInputFromUser("Not a valid entry, Please Enter State Name (Abbreviation) again!");
		}
		return stateName;
	}
*/
	/*private String validateAndGetName() {
		String customerName = userIO.getInputFromUser("Please Enter Customer Name");
		while (validation.isValidCustomerName(customerName)) {
			customerName = userIO.getInputFromUser("Not a valid entry, Please Enter Customer Name again!");
		}
		return customerName;
	}*/
	private LocalDate getAndValidateOrderDate() {
		LocalDate date = userIO.validateAndGetDate(DATE_MM_DD_YYYY);
		while (!date.isAfter(LocalDate.now())) {
			date = userIO.validateAndGetDate("Date should be from the future, " + DATE_MM_DD_YYYY);
		}
		return date;
	}

	/*private BigDecimal getAndValidateArea() {
		String area = userIO.getInputFromUser("Please Enter the Area");
		while (validation.isInvalid(area)) {
			area = userIO.getInputFromUser("Not a valid entry, Please Enter the Area again!");
		}
		return new BigDecimal(area);
	}
*/

	/**
	 * Ask to save string.
	 *
	 * @return the string
	 */
	public String askToSave() {
		return userIO.validateAndGetSelection("Do you want to save (Y/N)?");
	}

	/**
	 * Order success.
	 */
	public void orderSuccess() {
		flush("Order has been added Successfully!");
	}

	/**
	 * Gets edited order.
	 *
	 * @param order the order
	 * @return the edited order
	 * @throws IOException        the io exception
	 * @throws URISyntaxException the uri syntax exception
	 */
	/*public Order getEditedOrder(Order order) throws IOException, URISyntaxException {
		String customerName = userIO.getInputFromUser("Enter Customer Name (" + order.getCustomerName() + "): ");
		if (customerName != null && customerName.length() != 0) {
			while (validation.isValidCustomerName(customerName)) {
				customerName = userIO.getInputFromUser("Invalid, Enter Customer Name (" + order.getCustomerName() + "): ");
			}
			order.setCustomerName(customerName);
		}

		String stateName = userIO.getInputFromUser("Enter State Name (" + order.getState() + "): ");
		if (stateName != null && stateName.length() != 0) {
			while (validation.isValidStateName(stateName)) {
				stateName = userIO.getInputFromUser("Invalid, Enter State Name (" + order.getState() + "): ");
			}
			order.setState(stateName);
		}

		String productType = userIO.getInputFromUser("Enter Product Type (" + order.getProductType() + "): ");
		if (productType != null && productType.length() != 0) {
			while (validation.isValidProductType(productType)) {
				productType = userIO.getInputFromUser("Invalid, Enter Product Type  (" + order.getProductType() + "): ");
			}
			order.setProductType(productType);
		}

		String area = userIO.getInputFromUser("Enter Area (" + order.getArea() + "): ");
		if (area != null && area.length() != 0) {
			while (validation.isInvalid(area)) {
				area = userIO.getInputFromUser("Invalid, Enter Area (" + order.getArea() + "): ");
			}
			order.setArea(new BigDecimal(area));
		}
		return order;
	}
*/

	/**
	 * Ask to delete string.
	 *
	 * @return the string
	 */
	public String askToDelete() {
		return userIO.validateAndGetSelection("Are you sure to delete (Y/N)?");
	}

	/**
	 * Order success deleted.
	 */
	public void orderSuccessDeleted() {
		flush("Order has been deleted Successfully!");
	}
}
