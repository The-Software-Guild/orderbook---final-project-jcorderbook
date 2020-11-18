package com.jessica.orderbook.model;

import java.util.Objects;

/**
 * The type Order.
 */
public class Order {
	private String orderID;
	private double price;
	private int quantity;
	private OrderType orderType;

	/**
	 * Instantiates a new Order.
	 */
	public Order() {
	}

	/**
	 * Instantiates a new Order.
	 *
	 * @param orderID   the order id
	 * @param price     the price
	 * @param quantity  the quantity
	 * @param orderType the order type
	 */
	public Order(String orderID, double price, int quantity, OrderType orderType) {
		this.orderID = orderID;
		this.price = price;
		this.quantity = quantity;
		this.orderType = orderType;
	}

	/**
	 * Gets order id.
	 *
	 * @return the order id
	 */
	public String getOrderID() {
		return orderID;
	}

	/**
	 * Sets order id.
	 *
	 * @param orderID the order id
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	/**
	 * Gets price.
	 *
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets price.
	 *
	 * @param price the price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Gets quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets quantity.
	 *
	 * @param quantity the quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets order type.
	 *
	 * @return the order type
	 */
	public OrderType getOrderType() {
		return orderType;
	}

	/**
	 * Sets order type.
	 *
	 * @param orderType the order type
	 */
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Order order = (Order) o;
		return Double.compare(order.price, price) == 0 &&
			       quantity == order.quantity &&
			       Objects.equals(orderID, order.orderID) &&
			       orderType == order.orderType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderID, price, quantity, orderType);
	}

	@Override
	public String toString() {
		return orderType + " OrderID " + orderID +
			       ": Price:" + price +
			       ", Size:" + quantity;
	}
}
