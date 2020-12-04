package com.jessica.orderbook.service;

import com.jessica.orderbook.dao.OrderDao;
import com.jessica.orderbook.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

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
	 * Calculate and update order order.
	 *
	 * @param order  the order
	 * @param update the update
	 * @return the order
	 * @throws Exception the exception
	 *//*
	public Order calculateAndUpdateOrder(Order order, boolean update) throws Exception {
		calculateCosts(order, update);
		return order;
	}

	private void calculateCosts(Order order, boolean update) throws Exception {
		try {
			final BigDecimal area = order.getArea();
			final String productType = order.getProductType();
			final String stateAbbreviation = order.getState();
			final Map<String, Tax> taxInfo = FileUtils.loadTaxInfo();
			final Tax tax = taxInfo.get(stateAbbreviation);
			final BigDecimal taxRate = tax.getTaxRate();
			final Map<String, Product> products = FileUtils.loadProductInfo();
			final Product product = products.get(productType);
			final BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
			final BigDecimal laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
			final BigDecimal materialCost = area.multiply(costPerSquareFoot);
			final BigDecimal labourCost = area.multiply(laborCostPerSquareFoot);
			final BigDecimal taxFinal = (materialCost.add(labourCost))
				                            .multiply(taxRate.divide(BigDecimal.valueOf(100), RoundingMode.FLOOR));
			order.setMaterialCost(setScale(materialCost));
			order.setLaborCost(setScale(labourCost));
			order.setTax(setScale(taxFinal));
			order.setTotal(setScale(materialCost.add(taxFinal).add(labourCost)));
			order.setTaxRate(setScale(tax.getTaxRate()));
			order.setCostPerSquareFoot(setScale(product.getCostPerSquareFoot()));
			order.setLaborCostPerSquareFoot(setScale(product.getLaborCostPerSquareFoot()));
			if (!update) {
				order.setOrderNumber(maxOrderNumber + 1);
			}
		} catch (Exception e) {
			throw new Exception("Error updating costs: ", e);
		}
	}

	*//**
	 * Add to memory.
	 *
	 * @param order the order
	 * @throws IOException the io exception
	 *//*
	public void addToMemory(Order order) throws IOException {
		orderDao.addOrder(order);
		maxOrderNumber++;
	}

	*//**
	 * Save back to file.
	 *
	 * @param order    the order
	 * @param userDate the user date
	 * @throws URISyntaxException the uri syntax exception
	 *//*
	public void updateOrder(Order order, LocalDate userDate) throws URISyntaxException {
		orderDao.updateOrder(order, userDate);
	}

	*//**
	 * Remove.
	 *
	 * @param order the order
	 * @throws IOException the io exception
	 *//*
	public void remove(Order order) throws IOException {
		orderDao.removeOrder(order);
	}

	*//**
	 * Backup orders.
	 *
	 * @throws IOException the io exception
	 *//*
	public void backupOrders() throws IOException {
		orderDao.exportOrders();
	}

	*//**
	 * Gets all products.
	 *
	 * @return the all products
	 * @throws IOException        the io exception
	 * @throws URISyntaxException the uri syntax exception
	 *//*
	public Map<String, Product> getAllProducts() throws IOException, URISyntaxException {
		return productDao.getAllProducts();
	}

	*//**
	 * Gets all tax info.
	 *
	 * @return the all tax info
	 * @throws IOException        the io exception
	 * @throws URISyntaxException the uri syntax exception
	 *//*
	public Map<String, Tax> getAllTaxInfo() throws IOException, URISyntaxException {
		return taxDao.getAllTaxInfo();
	}

	private BigDecimal setScale(BigDecimal value) {
		return value.setScale(2, RoundingMode.HALF_UP);
	}*/
}
