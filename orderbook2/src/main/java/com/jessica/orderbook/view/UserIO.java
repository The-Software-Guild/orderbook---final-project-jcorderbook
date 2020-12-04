package com.jessica.orderbook.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * The type Console manager.
 */
@Component
public class UserIO {
	private final Scanner scanner = new Scanner(System.in);

	/**
	 * Flush.
	 *
	 * @param msg the msg
	 */
	public void flush(String msg) {
		System.out.println(msg);
	}

	/**
	 * Validate and get selection int.
	 *
	 * @return the int
	 */
	public int validateAndGetSelection(int low, int max) {
		int input = 0;
		while (true) {
			try {
				input = Integer.parseInt(scanner.nextLine());
				if (input < low || input > max) {
					flush("Invalid selection, Try again!");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid selection," + input + "Try again!");
			}
		}
		return input;
	}

	/**
	 * Gets input from user.
	 *
	 * @param msg the msg
	 * @return the input from user
	 */
	public String getInputFromUser(String msg) {
		flush(msg);
		return scanner.nextLine();
	}
}
