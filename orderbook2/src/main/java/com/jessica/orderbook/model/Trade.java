package com.jessica.orderbook.model;

import java.time.LocalDateTime;

/**
 * The type Trade.
 */
public class Trade {
	private String tradeId;
	private LocalDateTime executionTime;
	private int quantity;
	private double price;

	/**
	 * Instantiates a new Trade.
	 */
	public Trade() {
	}

	/**
	 * Gets trade id.
	 *
	 * @return the trade id
	 */
	public String getTradeId() {
		return tradeId;
	}

	/**
	 * Sets trade id.
	 *
	 * @param tradeId the trade id
	 */
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	/**
	 * Gets execution time.
	 *
	 * @return the execution time
	 */
	public LocalDateTime getExecutionTime() {
		return executionTime;
	}

	/**
	 * Sets execution time.
	 *
	 * @param executionTime the execution time
	 */
	public void setExecutionTime(LocalDateTime executionTime) {
		this.executionTime = executionTime;
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

	@Override
	public String toString() {
		return "TradeID='" + tradeId + '\'' +
			       ", ExecutionTime=" + executionTime +
			       ", Size=" + quantity +
			       ", Price=" + price;
	}
}
