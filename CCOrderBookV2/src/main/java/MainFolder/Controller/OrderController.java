/**
 * 
 */
package MainFolder.Controller;


import MainFolder.DAO.OrderPersistenceException;
import MainFolder.DTO.Order;
import MainFolder.DTO.Trade;
import MainFolder.Service.OrderDataValidationException;
import MainFolder.Service.OrderMatchException;
import MainFolder.Service.OrderServiceLayer;
import MainFolder.Service.TradePersistenceException;
import MainFolder.UI.OrderBookView;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
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
	
	private OrderBookView view;
	private OrderServiceLayer service;
	@Autowired
	public OrderController(OrderServiceLayer service, OrderBookView view) {
		this.service = service;
		this.view = view;
	}
        //Keeps running the program and displaying the menu by try-catching any errors in any of the 6 functions within a while loop. 
	public void run() throws IOException, OrderDataValidationException, Exception {
                service.clearTradeLog();
		boolean keepGoing = true;
		int menuSelection = 0;
                service.clearTables();
                service.generateNRandomOrders(1000);
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
                                        viewTrade();
                                        break;
                                case 8:
                                        viewAllTrades();
                                        break;
                                case 9: 
                                        viewTradesByExecutionTime();
                                        break;
                                case 10:
                                        viewTradesByQuantity();
                                        break;
                                case 11: 
                                        updateOrder();
                                        break;
                                case 12: 
                                        displayStats();
                                        break;
				case 13:
					keepGoing = false;
					break;
				default:
					unknownCommand();
				}
			
			
		}catch(OrderPersistenceException | IllegalArgumentException | OrderMatchException | TradePersistenceException | OrderDataValidationException e ) {
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
	
	private void createOrder() throws OrderPersistenceException, IOException, OrderDataValidationException{
		view.displayCreateOrderBanner();
                boolean hasErrors= false;
                do{
                    try {
                        boolean buy= view.getBuyOrSell();
                        Order currentOrder=view.getNewOrderInfo();
                        service.createOrder(currentOrder, buy);
                        view.displayOrder(currentOrder);
                        hasErrors= false;
                    } catch (OrderDataValidationException | InputMismatchException | IllegalArgumentException | NullPointerException e){
                        hasErrors=true;
                        view.displayErrorMessage(e.getMessage());
                    }
                } while (hasErrors);
	}
	
	private void listOrders() throws OrderPersistenceException{
		view.displayAllOrdersBanner();
                if (service.isEmpty()){
                    view.displayErrorMessage("Error: Orders are empty");
                    return;
                }
		List<Order> buyOrderList = service.sortBuyOrders();
                List<Order> sellOrderList=service.sortSellOrders();
		view.displayAllOrders(buyOrderList, sellOrderList);
	}
        
        //need to catch exception here d
	
	private void viewOrder() throws OrderPersistenceException, OrderDataValidationException{
		view.displayOrderBanner();
		String orderID = view.getOrderID();
                boolean buy = service.getBuyOrSell(orderID);
                if (buy){
                    Order order = service.getBuyOrder(orderID);
                    if (order !=null){
                        view.displayOrder(order);
                    } else {
                        view.displayErrorMessage("ERROR: No such order exists.");
                    }
                } else {
                    Order order= service.getSellOrder(orderID);
                    if (order != null){
                        view.displayOrder(order);
                    } else {
                        view.displayErrorMessage("ERROR: No such order exists.");
                    }
                }    
		
	}
        
        private void viewTrade() throws TradePersistenceException{
		view.displayTradeBanner();
		String tradeID = view.getTradeID();
                Trade trade = service.getTrade(tradeID);
                if (trade !=null){
                    view.displayTrade(trade);
                } else {
                    view.displayErrorMessage("ERROR: No such trade exists.");
                }
		
	}
	
	private void removeOrder() throws OrderPersistenceException, OrderDataValidationException, IOException{
		view.displayRemoveOrderBanner();
		String orderID = view.getOrderID();
                boolean buy = service.getBuyOrSell(orderID);
                if (buy){
                    Order removedOrder = service.removeBuyOrder(orderID);
                    if (removedOrder== null){
                        view.displayErrorMessage("ERROR: No such order.");
                    } else {
                        view.displayRemovedOrder(removedOrder);
                    }
                } else {
                    Order removedOrder = service.removeSellOrder(orderID);
                    if (removedOrder== null){
                        view.displayErrorMessage("ERROR: No such order.");
                    } else {
                        view.displayRemovedOrder(removedOrder);
                    }
                }
	}
	
	private void unknownCommand() {
		view.displayErrorMessage("ERROR: Unknown Command");
	}
	
	private void exitMessage() {
		view.exit();
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
            List<Order> buyOrdersList= service.getAllBuyOrders();
            List<Order> sellOrdersList= service.getAllSellOrders();
            while (!buyOrdersList.isEmpty() && !sellOrdersList.isEmpty()){
                Trade trade=service.matchTopOrder();
                view.displayTrade(trade);
                buyOrdersList= service.getAllBuyOrders();
                sellOrdersList= service.getAllSellOrders();
            }
        }
        
        private void matchAnOrder() throws Exception {
            view.displayMatchOrderBanner();
            String orderID= view.getOrderID();
            boolean buy = service.getBuyOrSell(orderID);
            Trade trade = service.matchAnOrder(orderID, buy);
            view.displayTrade(trade);
        }
        
        private void viewAllTrades() throws TradePersistenceException {
            List<Trade> trades= service.sortTrades();
            if (trades.isEmpty()){
                    throw new TradePersistenceException ("Error: No trades exist");
                }
            view.displayTrades(trades);
            
        }
        
        private void viewTradesByExecutionTime() throws TradePersistenceException {
            view.displayTradesBanner();
            LocalDate date= view.getDate();
            List<Trade> trades;
            int hours= view.getHours();
            if (hours!=-1){
                int minutes=view.getMinutes();
                if (minutes != -1){
                    int seconds=view.getSeconds();
                    if (seconds != -1){
                        LocalTime time= LocalTime.of(hours, minutes, seconds);
                        trades=service.tradesByTime(date, time, true, true);
                    } else {
                        LocalTime time= LocalTime.of(hours, minutes);
                        trades=service.tradesByTime(date, time, true, false);
                    }
                } else {
                    LocalTime time= LocalTime.of(hours, 0);
                    trades=service.tradesByTime(date, time, false, false);
                }
            } else {
                trades=service.tradesByDate(date);
                
                }
            
            if (trades.isEmpty()){
                    throw new TradePersistenceException ("Error: No trades exist at the given date");
                }
            view.displayTrades(trades);
        }
        
        public void viewTradesByQuantity() throws TradePersistenceException{
            view.displayTradesBanner();
            int quantity= view.getTradeQuantity();
            List<Trade> trades= service.tradesByQuantity(quantity);
            if (trades.isEmpty()){
                    throw new TradePersistenceException ("Error: No trades exist with this quantity");
                }
            view.displayTrades(trades);
        }
        
        public void updateOrder() throws Exception {
            
            String orderID= view.getOrderID();
            boolean buy = service.getBuyOrSell(orderID);
            String[] updatedParams= view.getUpdatedParams();
            updatedParams[0]= orderID;
            Order updatedOrder=null;
            if (buy){
                updatedOrder= service.editBuyOrder(updatedParams);
            } else {
                updatedOrder=service.editSellOrder(updatedParams);
            }
            view.displayOrder(updatedOrder);
        }
        
        public void displayStats() throws Exception{
            List<Order> buyOrdersList= service.getAllBuyOrders();
            List<Order> sellOrdersList= service.getAllSellOrders();
            view.displayStats(buyOrdersList, sellOrdersList);
        }
}
