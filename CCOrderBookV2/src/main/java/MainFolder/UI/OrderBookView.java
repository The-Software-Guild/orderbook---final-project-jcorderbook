/**
 * 
 */
package MainFolder.UI;



import MainFolder.DTO.Order;
import MainFolder.DTO.Trade;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import s.S;

/**
 * The type OrderBookView .
 */
@Component
public class OrderBookView {
	private static final String EMPTY = "";
	private final UserIO userIO;

	/**
	 * Instantiates a new Order book view.
	 *
	 * @param userIO the user io
	 */
	@Autowired
	public OrderBookView(UserIO userIO) {
		this.userIO = userIO;
	}

	/**
	 * Gets user selection.
	 *
	 * @return the user selection
	 */
	public int getUserSelection() {
		return userIO.validateAndGetSelection(1, 10);
	}

	/**
	 * Display main menu.
	 */
	public int printMenuAndGetSelection() {
		flush("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		flush("* <<Order Book Simulator>>");
		flush("* 1. List all Orders");
		flush("* 2. Create an Order");
		flush("* 3. View an Order");
		flush("* 4. Remove an Order");
		flush("* 5. Match all Orders");
		flush("* 6. Match an Order");
		flush("* 7. View a Trade");
                flush("* 8. View all Trades");
		flush("* 9. View Trades by Execution Time");
		flush("* 10. View Trades by Quantity");
                flush("* 11. Update an Order");
                flush("* 12. Display Orderbook Stats");
                flush("* 13. Check if OrderBook is empty");
		flush("* 14. Quit");
		flush("*");
		flush("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
                return userIO.readInt("Please make a selection (1-14)", 1, 14);
	}

	/**
	 * Flush.
	 *
	 * @param msg the msg
	 */
	public void flush(String msg) {
		userIO.flush(msg);
	}
        
        public void displayErrorMessage(String message){
            S.print("=== ERROR ===");
            S.print(message);
        }
        
        public void displayCreateOrderBanner(){
            S.print("=== CREATE ORDER ===");

        }
        
        public boolean getBuyOrSell(){
            String buyOrSell=null;
            boolean answer=true;
            while(true) {
                buyOrSell = S.prompt("Buy or Sell Order? (Buy/Sell)");
                if (buyOrSell.equals("Buy")|| buyOrSell.equals("Sell")){
                    break;
                }
                else {
                    S.print("Please enter a valid response");
                }
            }
            
            if (buyOrSell.equals("Buy")){
                answer= true;
            } else if (buyOrSell.equals("Sell")){
                answer=false;
            }
            return answer;
        }
        
        

	/**
	 * Gets input.
	 *
	 * @param msg the msg
	 * @return the input
	 */
	public String getInput(String msg) {
		return userIO.getInputFromUser(msg);
	}

	/**
	 * Exit.
	 */
	public void exit() {
		flush("Thank you for using OrderBook!");
	}
        
        public Order getNewOrderInfo(){
            BigDecimal price=null;
            while (true){
                try{
                    price= new BigDecimal(S.prompt("What is the price?"));
                    
                    if (price.compareTo(new BigDecimal("0"))>0){
                        break;
                    } else {
                        S.print("Please enter a price greater than 0");
                    }
                } catch (Exception e){
                    S.print("ERROR: Please enter a valid price.");
                }
            }
            int quantity=0;
            quantity=userIO.readInt("What is your quantity?", 1, 1000000);
            Order newOrder= new Order("0", price, quantity);
            return newOrder;
        }
        
        public void displayAllOrdersBanner(){
            S.print("=== DISPlAY ALL ORDERS ===");
        }
        public void displayOrderBanner(){
            S.print("=== DISPLAY ORDER ===");
        }
        public String getOrderID(){
            return S.prompt("What is the OrderID?");
        }
	/**
	 * Display all orders.
	 *
	 * @param buyOrders  the buy orders
	 * @param sellOrders the sell orders
	 */
	public void displayAllOrders(List<Order> buyOrders, List<Order> sellOrders) {
		flush("* * * * * * * * * * * * OrderBook for Stock : Apple * * * * * * * * * * * *");

		int loop = Math.max(buyOrders.size(), sellOrders.size());
		int pageSize = 20;
		int divide = loop % 20;
		int totalPages;
		if (divide == 0) {
			totalPages = loop / 20;
		} else {
			totalPages = loop / 20 + 1;
		}
		int pageCount = 1;
		int fromIndex = 0;
		while (pageCount <= totalPages) {
			final boolean flag = printOrders(buyOrders, sellOrders, fromIndex, fromIndex + pageSize);
			if (!flag) {
				flush("* * * * * * * * * * AWAITING INPUT * * * * * * * * * * ");
				flush("Please press 1 to show next page or 2 to exit to main screen");
				final int selection = userIO.validateAndGetSelection(1, 2);
				if (selection == 2) {
					break;
				} else {
					fromIndex = fromIndex + pageSize;
				}
				pageCount++;
			} else {
				break;
			}
		}
	}
        
	private boolean printOrders(
		List<Order> buyOrders, List<Order> sellOrders, int startIndex, int endIndex) {
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>();
		headersList.add("Buy Orders");
		headersList.add("Sell Orders");
		int loop = Math.min(endIndex, buyOrders.size());
		List<List<String>> rowsList = new ArrayList<>();
		for (int i = startIndex; i < loop; i++) {
			List<String> row = new ArrayList<>();
			if (buyOrders.size() > i) {
                            Order buyOrder= buyOrders.get(i);
                            row.add(buyOrder.getOrderID()+" Price: $"+ buyOrder.getPrice().toString()+ " Quantity: "+ buyOrder.getQuantity());
			} else {
				row.add(EMPTY);
			}
			if (sellOrders.size() > i) {
                                Order sellOrder=sellOrders.get(i);
				row.add(sellOrder.getOrderID()+" Price: $"+ sellOrder.getPrice().toString()+ " Quantity: "+ sellOrder.getQuantity());
			} else {
				row.add(EMPTY);
			}
			rowsList.add(row);
		}
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
		return buyOrders.size() < endIndex;
	}

	/**
	 * Order success.
	 */
	public void orderSuccess() {
		flush("Order has been added Successfully!");
	}
        
        public void displayOrder(Order order){
            S.print("OrderID: "+order.getOrderID());
            S.print("Price: $"+ order.getPrice().toString());
            S.print("Quantity: "+ order.getQuantity());
        }
        
        public void displayTradeBanner(){
            S.print("=== DISPLAY TRADE ===");
        }
        
        public String getTradeID(){
            return S.prompt("What is the TradeID?");
        }
        
        public void displayTrade(Trade trade){
            flush(trade.getTradeID()+" Executed Price: $"+ trade.getExecutedPrice().toString()+ " Quantity: "+ trade.getQuantity()+" Time:"+ trade.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        
        public void displayRemoveOrderBanner(){
            S.print("=== REMOVE ORDER ===");
        }
        
        public void displayRemovedOrder(Order order){
            S.print("OrderID: "+order.getOrderID());
            S.print("Price: "+ order.getPrice().toString());
            S.print("Quantity: "+ order.getQuantity());
            S.print("Order successfully removed");
        }

	/**
	 * Display stats.
	 *
	 * @param buyOrders  the buy orders
	 * @param sellOrders the sell orders
	 */
	public void displayStats(List<Order> buyOrders, List<Order> sellOrders) {
		flush("* * * * * * * * * * * * OrderBook Stats * * * * * * * * * * * *");
		flush("Number of Sell Orders : " + sellOrders.size());
		flush("Number of Buy Orders : " + buyOrders.size());
		flush("Overall Sell Quantity : " + sellOrders.stream().mapToInt(Order::getQuantity).sum());
		flush("Overall Buy Quantity : " + buyOrders.stream().mapToInt(Order::getQuantity).sum());
                if (!sellOrders.isEmpty()){
                    BigDecimal averageSellPrice= new BigDecimal(sellOrders.stream().mapToInt(Order::getQuantity).average().getAsDouble());
                    BigDecimal roundedAverageSellPrice= averageSellPrice.setScale(2, RoundingMode.HALF_UP);
                    flush("Average Sell Price : $" + roundedAverageSellPrice);
                }
		if (!buyOrders.isEmpty()){
                    BigDecimal averageBuyPrice= new BigDecimal(buyOrders.stream().mapToInt(Order::getQuantity).average().getAsDouble());
                    BigDecimal roundedAverageBuyPrice= averageBuyPrice.setScale(2, RoundingMode.HALF_UP);
                    flush("Average Buy Price : $" + roundedAverageBuyPrice);
                }
		
	}
        

	/**
	 * Trade.
	 *
	 * @param trade the trade
	 */
	public void trade(Trade trade) {
		flush("* * * * * * * * * * * * Trade Added * * * * * * * * * * * *");
		flush(trade.getTradeID()+" Executed Price: $"+ trade.getExecutedPrice().toString()+ " Quantity: "+ trade.getQuantity()+" Time:"+ trade.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}

	/**
	 * Display trades.
	 *
	 * @param allTrades the all trades
	 */
	public void displayTrades(List<Trade> allTrades) {
		flush("* * * * * * * * * * * * Trades So Far * * * * * * * * * * * *");
		allTrades.forEach((trade)-> flush(trade.getTradeID()+" Executed Price: $"+ trade.getExecutedPrice().toString()+ " Quantity: "+ trade.getQuantity()+" Time:"+ trade.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
	}
        
        public void displayMatchAllOrdersBanner(){
            S.print("=== MATCH ALL ORDERS ====");
        }
        
        public void displayMatchOrderBanner(){
            S.print("=== MATCH ORDERS ====");
        }
        
        public void displayTradesBanner(){
            S.print("=== DISPLAY ALL TRADES ===");
        }
        
        public LocalDate getDate(){
            while (true) {
                try {
                    String inputDay= S.prompt("What is the date? MM/DD/YYYY");
                    LocalDate date= LocalDate.parse(inputDay, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    return date;
                } catch (Exception e){
                    S.print("ERROR: Incorrect date or date format. Please try again.");
                }
            }
        }
        
        public int getHours(){
            return userIO.readInt("Please enter the hour or enter -1 if not specified", -1, 24);
        }
        
        public int getMinutes(){
            return userIO.readInt("Please enter the minute or enter -1 if not specified", -1, 60);
        }
        
        public int getSeconds(){
            return userIO.readInt("Please enter the seconds or enter -1 if not specified", -1, 60);
        }
        
        public int getQuantity(){
            return userIO.readInt("Please enter the quantity(1-1000)", 1, 1000);
        }
        
        public int getTradeQuantity(){
            return userIO.readInt("Please enter the Trade quantity (1-50)", 1, 50);
        }
        
        public String[] getUpdatedParams(){
            String[] updatedParams = new String[3];
            updatedParams[0]="0";
            BigDecimal price=null;
            while (true){
                try{
                    String input= S.prompt("What is the new price? Or Press enter if no change");
                    if (!input.equals("")){
                        price= new BigDecimal(input);
                        if (price.compareTo(new BigDecimal("0"))>0){
                            updatedParams[1]=input;
                            break;
                        } else {
                            S.print("Please enter a price greater than 0");
                        } 
                    } else {
                        updatedParams[1]=input;
                        break;
                    }
                    
                    
                    
                } catch (Exception e){
                    S.print("ERROR: Please enter a valid price.");
                }
            }
            int quantity=0;
            while (true){
                try{
                    String inputQuantity= S.prompt("What is the new quantity? Or press enter if no change");
                    if (!inputQuantity.equals("")){
                        quantity= S.integize(inputQuantity);
                        if (quantity>0){
                            updatedParams[2]=inputQuantity;
                            break;
                        } else {
                            S.print("Please enter a quantity greater than 0");
                        }
                    } else{
                        updatedParams[2]=inputQuantity;
                        break;
                    }
                    
                    
                } catch (Exception e){
                    S.print("ERROR: Please enter an integer.");
                }
                
            }
            return updatedParams;
        }
        public void isEmpty(boolean empty){
            if (empty){
                S.print("The OrderBook is empty (either the buy or sell list is empty)");
            } else{
                S.print("The Orderbook has both buy and sell orders");
            }
        }
}