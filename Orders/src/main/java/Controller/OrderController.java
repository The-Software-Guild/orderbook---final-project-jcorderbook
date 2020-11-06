/**
 * 
 */
package Controller;

import DAO.OrderPersistenceException;
import DTO.Order;
import Service.OrderDataValidationException;
import Service.OrderDuplicateIdException;
import Service.OrderServiceLayer;
import UI.OrderView;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;



/**
 * @author 
 *
 */
public class OrderController {
	
	private OrderView view;
	private OrderServiceLayer service;
	
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
                do{
                    try {
                        String date= view.getDate();
                        String customerName= view.getCustomerName(false);
                        String state=null;
                        while (true){
                            state= view.getState(false);
                            try {
                                service.getAbbreviation(state);
                                break;
                            } catch (OrderPersistenceException e){
                                view.displayErrorMessage("Error: Input State is not on file. Please try again.");
                            }
                        }
                        String productType=null;
                        while (true){
                            productType=view.getProductType(false);
                            try {
                                service.getLaborCostPerSquareFoot(productType);
                                break;
                            } catch (OrderPersistenceException e){
                                view.displayErrorMessage("Error: Input Product Type is not on file. Please try again.");
                            }
                        }
                        String areaString=null;
                        BigDecimal area=null;
                        while (true){
                            areaString=view.getArea(false, 100);
                            area= new BigDecimal(areaString);
                            if (area.compareTo(new BigDecimal(100))>=0){
                                 break;
                            } else {
                                view.displayErrorMessage("Error: Please enter an area greater than or equal to 100 sq ft.");
                            }
                        }
                        Order currentOrder= new Order(-1, customerName, state, productType, area);
                        
                        service.createOrder(date, currentOrder);
                        view.displayCreateSuccessBanner();
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
            String[] params= new String[4];
            params[0]= view.getCustomerName(true);
            while (true){
                params[1]= view.getState(true);
                try {
                    service.getAbbreviation(params[1]);
                    break;
                } catch (OrderPersistenceException e){
                    view.displayErrorMessage("Error: Input State is not on file. Please try again.");
                }
            }
            while (true){
                params[2]=view.getProductType(true);
                try {
                    service.getLaborCostPerSquareFoot(params[2]);
                    break;
                } catch (OrderPersistenceException e){
                    view.displayErrorMessage("Error: Input Product Type is not on file. Please try again.");
                }
            }
             while (true){
                params[3]=view.getArea(true, 100);
                BigDecimal area= new BigDecimal(params[3]);
                if (area.compareTo(new BigDecimal(100))>=0){
                        break;
                } else {
                    view.displayErrorMessage("Error: Please enter an area greater than or equal to 100 sq ft.");
                }
            }

            Order updatedOrder= service.updateOrder(date, orderNumber, params, false);
            view.displayOrder(updatedOrder);
            boolean goAhead= view.askGoAhead();
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
