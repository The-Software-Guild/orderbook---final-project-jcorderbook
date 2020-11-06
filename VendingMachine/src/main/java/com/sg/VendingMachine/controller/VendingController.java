/**
 * 
 */
package com.sg.VendingMachine.controller;

import com.sg.VendingMachine.service.InsufficientFundsException;
import com.sg.VendingMachine.dao.VendingMachinePersistenceException;
import com.sg.VendingMachine.service.AllOutException;
import com.sg.VendingMachine.dto.Item;
import com.sg.VendingMachine.service.NoItemInventoryException;
import com.sg.VendingMachine.service.VendingMachineServiceLayer;
import com.sg.VendingMachine.ui.InvalidSelectionException;
import com.sg.VendingMachine.ui.VendingMachineView;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author Recynon
 *
 */
public class VendingController {
	
	private VendingMachineView view;
	private VendingMachineServiceLayer service;
	//Constructor takes in service and view layers
	public VendingController(VendingMachineServiceLayer service, VendingMachineView view) {
		this.service = service;
		this.view = view;
	}
        //Main run function. Loops the Use Vending Machine question. Any errors are caught and the money is refunded, and the program asks the Use Vending Machine question again. 
	public void run() throws IOException, VendingMachinePersistenceException, Exception {
                
		boolean keepGoing = true;
		int menuSelection = 0;
 
		while(keepGoing) {
                    try {
                        String yn=view.askUse();
                        if (yn.equals("No")){
                            break;
                        }
                        listItems();
                        BigDecimal cash=view.getCash();
                        service.setCash(cash);
                        int menuChoice= view.printMenuAndGetSelection();
                                switch (menuChoice){
                                        case 1:
                                            buyItem();
                                            break;
                                        case 2:
                                            refund();
                                            keepGoing= false;
                                            break;
                                        default:
                                            view.displayErrorMessage("Invalid Input. Try Again");
                                }
			   
			
                        }catch(VendingMachinePersistenceException | InsufficientFundsException | NoItemInventoryException | AllOutException | InvalidSelectionException e) {
                            
                            view.displayErrorMessage(e.getMessage());
                            refund();
                        }
                
                
                }
                exitMessage();
        }
	
	private int getMenuSelection() throws InvalidSelectionException {
		// UserIO myIo = new UserIOConsoleImpl();
		// VendingMachineView view = new VendingMachineView(myIo);
		return view.printMenuAndGetSelection();
	}
	//For testing purposes only. 
	private void createItem() throws VendingMachinePersistenceException, IOException, Exception{
		view.displayCreateItemBanner();
                Item currentItem= view.getNewItemInfo();
                service.createItem(currentItem);
                view.displayCreateItemBanner();


	}
	
	private void listItems() throws VendingMachinePersistenceException{
		view.displayDisplayAllBanner();
		List<Item> itemList = service.getAvailableItems();
		view.displayItemList(itemList);
	}
	
	
	
	private void unknownCommand() {
		view.displayUnknownCommandBanner();
	}
	
	private void exitMessage() {
		view.displayExitBanner();
	}
        //Buys an item by updating the Item count in the inventory and returning change in coins.
        private void buyItem() throws Exception{
            view.displayBuyItemBanner();
            String name =view.getItemNameChoice();
            BigDecimal change= service.BuyItem(name);
            view.displayBuyResult();
            view.displayChange(change);
            String[] coins= service.changeInCoins(change);
            view.displayChangeInCoins(coins);
        }
        //Refunds the money that the user put in. 
        public void refund() throws VendingMachinePersistenceException{
            BigDecimal refund=service.refund();
            view.displayRefund(refund);
            
        }
        //Makes all item counts 10. 
        public void restock() throws Exception{
            
            service.restock();
        }
	
}
