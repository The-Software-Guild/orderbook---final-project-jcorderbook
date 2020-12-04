/**
 * 
 */
package MainFolder.UI;

/**
 * @author
 *
 */
import java.util.Scanner;
import org.springframework.stereotype.Component;
@Component
public class UserIOConsoleImpl implements UserIO {
	
	private static Scanner input = new Scanner(System.in); 
        private static Scanner scanner = new Scanner(System.in); 
	
	public void print(String message) {
		System.out.println(message);
	}
	
	public String readString(String prompt) {
		print(prompt);
		String ans = input.nextLine();
		return ans;
	}
	
	public int readInt(String prompt) {
		int ans;
		while(true) {
			try {
				print(prompt);
				ans = Integer.parseInt(input.nextLine());
				break;
			}catch(Exception e) {
				print("That was not an Integer.");
			}
		}
		return ans;
	}
	
	public int readInt(String prompt, int min, int max) {
		int ans = min-1;
		while(!(ans>=min && ans<=max)) {
			try {
				print(prompt);
				ans = Integer.parseInt(input.nextLine());
			}catch(Exception e) {
				print("That was not an Integer within the correct range.");
			}
		}
		return ans;
	}
	
	public double readDouble(String prompt) {
		double ans;
		while(true) {
			try {
				print(prompt);
				ans = Double.parseDouble(input.nextLine());
				break;
			}catch(Exception e) {
				print("That was not a Double.");
			}
		}
		return ans;
	}
	
	public double readDouble(String prompt, double min, double max) {
		double ans = min-1;
		while(!(ans>=min && ans<=max)) {
			try {
				print(prompt);
				ans = Double.parseDouble(input.nextLine());
			}catch(Exception e) {
				print("That was not a Double within range.");
			}
		}
		return ans;
	}
	
	public float readFloat(String prompt) {
		float ans;
		while(true) {
			try {
				print(prompt);
				ans = Float.parseFloat(input.nextLine());
				break;
			}catch(Exception e) {
				print("That was not a Float.");
			}
		}
		return ans;
	}
	
	public float readFloat(String prompt, float min, float max) {
		float ans = min - 1;
		while(!(ans>=min && ans<=max)) {
			try {
				print(prompt);
				ans = Float.parseFloat(input.nextLine());
			}catch(Exception e) {
				print("That was not a Float within range.");
			}
		}
		return ans;
	}
	
	public long readLong(String prompt) {
		long ans;
		while(true) {
			try {
				print(prompt);
				ans = Long.parseLong(input.nextLine());
				break;
			}catch(Exception e) {
				print("That was not a Long.");
			}
		}
		return ans;
	}
	
	public long readLong(String prompt, long min, long max) {
		long ans = min - 1;
		while(!(ans>=min && ans<=max)) {
			try {
				print(prompt);
				ans = Long.parseLong(input.nextLine());
			}catch(Exception e) {
				print("That was not a Long within range.");
			}
		}
		return ans;
	}
        
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
				throw new IllegalArgumentException("Invalid selection, '" + input + "' \nTry again!");
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