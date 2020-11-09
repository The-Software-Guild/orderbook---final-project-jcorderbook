/**
 * 
 */
package Order.Controller;


import Order.DAO.OrderPersistenceException;
import Order.DTO.Order;
import Order.Service.OrderDataValidationException;
import Order.Service.OrderDuplicateIdException;
import Order.Service.OrderServiceLayer;
import Order.UI.OrderView;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * @author 
 *
 */
@Component
public class OrderController {
	
	private OrderView view;
	private OrderServiceLayer service;
	@Autowired
	public OrderController(OrderServiceLayer service, OrderView view) {
		this.service = service;
		this.view = view;
	}
        //Keeps running the program and displaying the menu by try-catching any errors in any of the 6 functions within a while loop. 
	public void run() throws IOException, OrderDuplicateIdException, OrderDataValidationException {
		boolean keepGoing = true;
		int menuSelection = 0;
		while(keepGoing) {
                try {
			
				
				menuSelection = getMenuSelection();
				
				switch(menuSelection) {
				case 1:
					listOrders();
					break;
				case 2:
					createOrder();
					break;
				case 3:
					removeOrder();
					break;
                                case 4:
                                        exportAllData();
                                        break;
                                case 5:
                                        updateOrder();
                                        break;
				case 6:
					keepGoing = false;
					break;
				default:
					unknownCommand();
				}
			
			
		}catch(OrderPersistenceException | IllegalArgumentException e) {
			view.displayErrorMessage(e.getMessage());
		}
                }
                exitMessage();
	}
	
	private int getMenuSelection() {
		// UserIO myIo = new UserIOConsoleImpl();
		// OrderView view = new OrderView(myIo);
		return view.printMenuAndGetSelection();
	}
	
	private void createOrder() throws OrderPersistenceException, IOException, OrderDuplicateIdException, OrderDataValidationException{
		view.displayCreateOrderBanner();
                boolean hasErrors= false;
                //Starts a loop that catches any errors upon user input and repeats after catching. 
                do{
                    try {
                        String date= view.getDate(); //The view function automatically checks to see if the input date is in the future. 
                        String customerName= null;
                        //Keeps asking for the customer name until the user inputs a valid name. Checks validity with a service layer function. 
                        while (true){

                            customerName= view.getCustomerName(null);
                            if (service.validateName(customerName)== true){
                                break;
                            } else {
                                view.displayErrorMessage("Error: Invalid name. Only [a-z][0-9], periods, and commas are allowed. Please try again.");
                            }
                        }
                               
                        String state=null;
                        //Keeps asking for state until a state on file is inputed. 
                        while (true){
                            state= view.getState(null);
                            try {
                                service.getAbbreviation(state);
                                break;
                            } catch (OrderPersistenceException e){
                                view.displayErrorMessage("Error: Input State is not on file. Please try again.");
                            }
                        }
                        
                        String productType=null;
                        //Keeps asking for productType until one that's on file is inputed. 
                        while (true){
                            productType=view.getProductType(null);
                            try {
                                service.getLaborCostPerSquareFoot(productType);
                                break;
                            } catch (OrderPersistenceException e){
                                view.displayErrorMessage("Error: Input Product Type is not on file. Please try again.");
                            }
                        }
                        String areaString=null;
                        BigDecimal area=null;
                        //Keeps asking for area until an area bigger than 100 is inputed. The view handles any inputs that can't be converted to BigDecimal. 
                        while (true){
                            areaString=view.getArea(null, 100);
                            area= new BigDecimal(areaString);
                            if (area.compareTo(new BigDecimal(100))>=0){
                                 break;
                            } else {
                                view.displayErrorMessage("Error: Please enter an area greater than or equal to 100 sq ft.");
                            }
                        }
                        Order currentOrder= new Order(-1, customerName, state, productType, area);
                        //Calculates the rest of the parameters for the new order. 
                        service.setTheRest(currentOrder);
                        view.displayOrder(currentOrder, false); //Display the parameters of the potential order.
                        //Asks the user if they want to add this order. 
                        boolean goAhead= view.askGoAhead();
                        if (goAhead){
                            service.createOrder(date, currentOrder);
                            view.displayCreateSuccessBanner();
                        }
                        hasErrors= false;
                    } catch (OrderPersistenceException | OrderDataValidationException | InputMismatchException | IllegalArgumentException | NullPointerException e){
                        hasErrors=true;
                        view.displayErrorMessage(e.getMessage());
                    }
                } while (hasErrors);
	}
	
	private void listOrders() throws OrderPersistenceException{
                String date=view.getDate();
		view.displayDisplayAllBanner();
		List<Order> orderList = service.getAllOrders(date);
		view.displayOrderList(orderList);
	}
	
	
	private void removeOrder() throws OrderPersistenceException, IOException{
		view.displayRemoveOrderBanner();
		String date = view.getDate();
                int orderNumber= view.getOrderNumber();
		Order removedOrder = service.removeOrder(date, orderNumber);
		view.displayRemoveResult(removedOrder);
	}
	
	private void unknownCommand() {
		view.displayUnknownCommandBanner();
	}
	
	private void exitMessage() {
		view.displayExitBanner();
	}
        
        //Similar to the create order function, only this time it checks to see if the input string is "" before proceeding with validation. 
        private void updateOrder() throws OrderPersistenceException{
            String date= view.getDate();
            int orderNumber= view.getOrderNumber();
            Order existingOrder= service.getOrder(date, orderNumber);
            while (existingOrder==null){
                view.displayNoSuchOrder();
                view.displayOrderList(service.getAllOrders(date));
                date= view.getDate();
                orderNumber=view.getOrderNumber();
                existingOrder= service.getOrder(date, orderNumber);
            }
            String[] params= new String[4]; //Initializes the array of parameters that will be fed to the updateOrder function.
            //Keeps asking for Customer Name until a valid input is entered. The input is only validated if it's not equal to "". 
            while (true){
                params[0]= view.getCustomerName(existingOrder);
                if (!params[0].equals("")){
                    if (service.validateName(params[0])== true){
                        break;
                    } else {
                        view.displayErrorMessage("Error: Invalid name. Only [a-z][0-9], periods, and commas are allowed. Please try again.");
                    }
                } else {
                    break;
                }
            }
            //Displays the state of the order being updated.
            //Keeps asking for State until a valid input is entered. The input is only validated if it's not equal to "". 
            while (true){
                params[1]= view.getState(existingOrder);
                if (!params[1].equals("")){
                    try {
                        service.getAbbreviation(params[1]);
                        break;
                    } catch (OrderPersistenceException e){
                        view.displayErrorMessage("Error: Input State is not on file. Please try again.");
                    }
                } else {
                    break;
                }
            }
            //Displays the productType of the order being updated.
            //Keeps asking for ProductType until a valid input is entered. The input is only validated if it's not equal to "". 
            while (true){
                params[2]=view.getProductType(existingOrder);
                if (!params[2].equals("")){
                    try {
                        service.getLaborCostPerSquareFoot(params[2]);
                        break;
                    } catch (OrderPersistenceException e){
                        view.displayErrorMessage("Error: Input Product Type is not on file. Please try again.");
                    }
                } else {
                    break;
                }
            }
            //Displays the area of the order being updated.
            //Keeps asking for Area until a valid input is entered. The input is only validated if it's not equal to "". 
             while (true){
                params[3]=view.getArea(existingOrder, 100);
                if (!params[3].equals("")){
                    BigDecimal area= new BigDecimal(params[3]);
                    if (area.compareTo(new BigDecimal(100))>=0){
                        break;
                    } else {
                        view.displayErrorMessage("Error: Please enter an area greater than or equal to 100 sq ft.");
                    }
                } else {
                    break;
                }
            }

            Order updatedOrder= service.updateOrder(date, orderNumber, params, false);
            view.displayOrder(updatedOrder, true); //Displays the resulting updated order.
            boolean goAhead= view.askGoAhead(); //Asks user if they want to proceed with the update. 
            if (goAhead){
                service.updateOrder(date, orderNumber, params, goAhead);
                view.displayUpdateSuccessfulBanner();
            }
            
        }
        
        private void exportAllData() throws IOException, OrderPersistenceException{
            service.exportData();
            view.displayExportSuccessfulBanner();
        }
	
}
