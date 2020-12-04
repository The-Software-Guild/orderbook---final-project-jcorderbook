/**
 * 
 */
package MainFolder.Controller;


import MainFolder.DAO.OrderPersistenceException;
import MainFolder.DTO.Order;
import MainFolder.DTO.Trade;
import MainFolder.Service.OrderDataValidationException;
import MainFolder.Service.OrderDuplicateIdException;
import MainFolder.Service.OrderServiceLayer;
import java.io.IOException;
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
	public void run() throws IOException, OrderDuplicateIdException, OrderDataValidationException, Exception {
                service.clearTradeLog();
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
					viewOrder();
					break;
				case 4:
					removeOrder();
					break;
                                case 5:
                                        matchAllOrders();
                                        break;
                                case 6:
                                        matchAnOrder();
                                        break;
				case 7:
					keepGoing = false;
					break;
				default:
					unknownCommand();
				}
			
			exitMessage();
		}catch(OrderPersistenceException | IllegalArgumentException e) {
			view.displayErrorMessage(e.getMessage());
		}
                }
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
                        boolean buy= view.getBuyOrSell();
                        Order currentOrder=view.getNewOrderInfo();
                        service.createOrder(currentOrder, buy);
                        view.displayCreateOrderBanner();
                        hasErrors= false;
                    } catch (OrderDuplicateIdException | OrderDataValidationException | InputMismatchException | IllegalArgumentException | NullPointerException e){
                        hasErrors=true;
                        view.displayErrorMessage(e.getMessage());
                    }
                } while (hasErrors);
	}
	
	private void listOrders() throws OrderPersistenceException{
		view.displayDisplayAllBanner();
		List<Order> buyOrderList = service.sortBuyOrders();
                List<Order> sellOrderList=service.sortSellOrders();
		view.displayOrderList(buyOrderList, sellOrderList);
	}
	
	private void viewOrder() throws OrderPersistenceException{
		view.displayDisplayOrderBanner();
                boolean buy = view.getBuyOrSell();
		String orderID = view.getNameChoice();
                if (buy){
                    Order order = service.getBuyOrder(orderID);
                    view.displayOrder(order);
                } else {
                    Order order= service.getSellOrder(orderID);
                    view.displayOrder(order);
                }    
		
	}
	
	private void removeOrder() throws OrderPersistenceException, IOException{
		view.displayRemoveOrderBanner();
                boolean buy = view.getBuyOrSell();
		String orderID = view.getNameChoice();
                if (buy){
                    Order removedOrder = service.removeBuyOrder(orderID);
                    view.displayRemoveResult(removedOrder);
                } else {
                    Order removedOrder = service.removeSellOrder(orderID);
                    view.displayRemoveResult(removedOrder);
                }
	}
	
	private void unknownCommand() {
		view.displayUnknownCommandBanner();
	}
	
	private void exitMessage() {
		view.displayExitBanner();
	}
        /*
        private void displayTypeOrders() throws OrderPersistenceException{
            Element element= view.getType();
            List<Order> orderOfType= service.getOrdersOfType(element);
            view.displayOrderList(orderOfType);
        }
        
        
        private void updateOrder() throws OrderPersistenceException{
            String orderName= view.getNameChoice();
            Order existingOrder= service.getOrder(orderName);
            while (existingOrder==null){
                view.displayNoSuchOrder();
                view.displayOrderList(service.getAllOrders());
                orderName=view.getNameChoice();
                existingOrder= service.getOrder(orderName);
            }
            String[] params= view.getUpdates();
            Order updatedOrder= service.updateOrder(orderName, params);
            view.displayUpdateSuccessfulBanner();
            view.displayOrder(updatedOrder);
            
        }
	*/
        
        private void matchAllOrders() throws Exception{
            view.displayMatchAllOrdersBanner();
            service.matchAllOrders();
        }
        
        private void matchAnOrder() throws Exception {
            view.displayMatchOrderBanner();
            boolean buy=view.getBuyOrSell();
            String orderID= view.getNameChoice();
            Trade trade = service.matchAnOrder(orderID, buy);
            view.displayTrade(trade);
        }
        
}
