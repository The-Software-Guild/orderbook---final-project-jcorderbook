/**
 * 
 */
package com.sg.VendingMachine.ui;

import com.sg.VendingMachine.dao.ItemDao;
import com.sg.VendingMachine.dto.Item;
import java.util.List;

import java.math.BigDecimal;
import s.S;

/**
 * @author Flavio Silva
 *
 */
public class VendingMachineView {
	
	private UserIO io;
	
	public VendingMachineView(UserIO io) {
		this.io = io;
	}
	public BigDecimal getCash() throws InvalidSelectionException{
            while (true) 
            {try {
                String cashString= io.readString("Enter your cash into the vending machine ($$.$$)");
                return new BigDecimal(cashString);
                }
            catch (NumberFormatException e){
                io.print("Invalid input. Please enter your money in the correct format.");
            }
            }

        }
	public int printMenuAndGetSelection() throws InvalidSelectionException {
		io.print("Main Menu");
		io.print("1. Buy a Item");
		io.print("2. Exit and return money");
		try{
		int output = io.readInt("Please select from the above choice.", 1, 2);
                return output;}
                catch (NumberFormatException e){
                    throw new InvalidSelectionException("Invalid Input");
                }
                
	}
	
	public Item getNewItemInfo() {
		String itemName = io.readString("Please enter a Item Name");
		String cost = io.readString("Please enter cost");
		String count = io.readString("Please enter Count.");
		
		Item currentItem = new Item(itemName);
                currentItem.setCost(new BigDecimal(cost));
		currentItem.setCount(S.integize(count));

		
		return currentItem;
	}
	
	public void displayCreateItemBanner() {
		io.print("=== Create Item ===");
	}
	
	
	public void displayItemList(List<Item> itemList) {
		for(Item currentItem : itemList) {
			String itemInfo = String.format("Item:%s  Price: $%s Stock: %s\n\n", 
					currentItem.getName(),
					currentItem.getStringCost(),
					currentItem.getStringCount());
			io.print(itemInfo);
		}
	}
	
	public void displayDisplayAllBanner() {
		io.print("=== Display All Items ===");
	}
	
	/*public void displayDisplayItemBanner() {
		io.print("=== Display Item ===");
	}*/
	
	public String getItemNameChoice() {
		return io.readString("Please enter the item name");
	}

	
	public void displayBuyItemBanner() {
		io.print("=== Buy Item ===");
	}
	
	public void displayBuyResult() {

            io.print("=== Item successfully bought ===");
	
	}
	
	public void displayExitBanner() {
		io.print("Good Bye!!!");
	}
        
        public void displayRefund(BigDecimal refund) {
            io.print("$"+refund.toString()+" is refunded.");
        }
	
	public void displayUnknownCommandBanner() {
		io.print("Unknown Command!!!");
	}
	
	public void displayErrorMessage(String errorMsg) {
		io.print("=== ERROR ===");
		io.print(errorMsg);
	}
        public void displayChange(BigDecimal change){
            io.print("Your change is $"+change.toString());
        }
        
        public String askUse(){
            return io.readString("Use the vending machine? Yes/No");
        }
        
        public void displayChangeInCoins(String[] coins){
            io.print(coins[0]+ " quarters, "+ coins[1]+" dimes, "+coins[2]+" nickels "+coins[3]+" pennies");
        }
}
