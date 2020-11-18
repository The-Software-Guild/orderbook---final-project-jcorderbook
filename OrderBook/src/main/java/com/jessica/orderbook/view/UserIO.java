package com.jessica.orderbook.view;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * The type Console manager.
 */
@Component
public class UserIO {
	private static final String MM_DD_YYYY = "MM-dd-yyyy";
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
	public int validateAndGetSelection() {
		int input = 0;
		while (true) {
			try {
				input = Integer.parseInt(scanner.nextLine());
				if (input < 1 || input > 12) {
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
	 * Validate and get selection string.
	 *
	 * @param msg the msg
	 * @return the string
	 */
	public String validateAndGetSelection(String msg) {
		flush(msg);
		String input;
		while (true) {
			input = scanner.nextLine();
			if (input.charAt(0) != 'Y' && input.charAt(0) != 'N') {
				flush("Invalid selection, Try again!");
			} else {
				break;
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

	/**
	 * Validate and get date local date.
	 *
	 * @param msg the msg
	 * @return the local date
	 */
	public LocalDate validateAndGetDate(String msg) {
		boolean valid = false;
		LocalDate result = null;
		do {
			String value = null;
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MM_DD_YYYY);
				value = getInputFromUser(msg);
				result = LocalDate.parse(value, formatter);
				valid = true;
			} catch (DateTimeParseException ex) {
				System.out.printf("The value '%s' is not a valid date. (MM-DD-YYYY)\n", value);
			}
		} while (!valid);
		return result;
	}
}
