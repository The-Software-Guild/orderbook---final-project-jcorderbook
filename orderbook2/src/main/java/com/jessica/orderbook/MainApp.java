package com.jessica.orderbook;

import com.jessica.orderbook.config.AppConfig;
import com.jessica.orderbook.controller.OrderBookController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * The type Main app.Entry point.
 */

public class MainApp {
	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		OrderBookController controller = applicationContext.getBean(OrderBookController.class);
		controller.simulate();
	}
}
