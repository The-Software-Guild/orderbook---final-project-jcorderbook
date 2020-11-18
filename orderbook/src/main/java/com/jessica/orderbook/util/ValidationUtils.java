/*
package com.jessica.orderbook.util;

import com.jessica.orderbook.service.OrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

*/
/**
 * The type Validation utils.
 * Instantiates a new Validation utils.
 *//*

@Component
public class ValidationUtils {

	private final OrderBookService service;

	*/
/**
 * Instantiates a new Validation utils.
 *
 * @param service the service
 *//*

	@Autowired
	public ValidationUtils(OrderBookService service) {
		this.service = service;
	}

	*/
/**
 * Is valid product type boolean.
 *
 * @param productType the product type
 * @return the boolean
 * @throws IOException        the io exception
 * @throws URISyntaxException the uri syntax exception
 *//*

	public boolean isValidProductType(String productType) throws IOException, URISyntaxException {
		boolean isValid = false;
		final Map<String, Product> products = service.getAllProducts();
		if (productType != null) {
			final Product product = products.get(productType);
			if (product != null) {
				isValid = true;
			}
		}
		return !isValid;
	}

	*/
/**
 * Is valid state name boolean.
 *
 * @param stateName the state name
 * @return the boolean
 * @throws IOException        the io exception
 * @throws URISyntaxException the uri syntax exception
 *//*

	public boolean isValidStateName(String stateName) throws IOException, URISyntaxException {
		boolean isValid = false;
		final Map<String, Tax> stringTaxMap = service.getAllTaxInfo();
		if (stateName != null) {
			final Tax tax = stringTaxMap.get(stateName);
			if (tax != null) {
				isValid = true;
			}
		}
		return !isValid;
	}

	*/
/**
 * Is valid customer name boolean.
 *
 * @param customerName the customer name
 * @return the boolean
 *//*

	public boolean isValidCustomerName(String customerName) {
		boolean valid = false;
		if (customerName != null && customerName.length() > 0) {
			if (customerName.matches("[\\w\\s.,]*")) {
				valid = true;
			}
		}
		return !valid;
	}

	*/
/**
 * Is invalid boolean.
 *
 * @param area the area
 * @return the boolean
 *//*

	public boolean isInvalid(String area) {
		boolean isInvalid;
		try {
			isInvalid = area == null
				            || Double.parseDouble(area) < 0
				            || Double.parseDouble(area) < 100;
		} catch (Exception e) {
			return true;
		}
		return isInvalid;
	}
}
*/
