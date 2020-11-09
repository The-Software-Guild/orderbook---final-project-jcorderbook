/**
 * 
 */
package Order.UI;




import Order.DAO.OrderPersistenceException;
import Order.DTO.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author
 *
 */
@Component
public class OrderView {
	
	private UserIO io;
	@Autowired
	public OrderView(UserIO io) {
		this.io = io;
	}
	
	public int printMenuAndGetSelection() {
		io.print("Main Menu");
		io.print("1. List All Orders");
		io.print("2. Create New Order");
		io.print("3. Remove an Order");
		io.print("4. Export all Data");
                io.print("5. Update an Order");
                io.print("6. Exit");

		
		return io.readInt("Please select from the above choice.", 1, 7);
	}
        //Gets a date from the user, makes sure it's in the correct format to be parsed, and that it's in the future. 
	public String getDate(){
            String date= null;
            while (true){
                //Try catch makes sure the date can be parsed. 
               try{ 
                    date= io.readString("What is the Order Date (MMDDYYY)? (Must be in the future)");
                    LocalDate inputDate =LocalDate.parse(date, DateTimeFormatter.ofPattern("MMddyyyy")); //Converts the input stream to a LocalDate.
                    LocalDate today= LocalDate.now();
                    long days= ChronoUnit.DAYS.between(today, inputDate);
                    //Makes sure the day is in the future. If not, the loop keeps asking for the date. 
                    if (days > 0){
                        break;
                    } else {
                        io.print("Invalid input. Please enter a future date.");
                    }
                } catch (DateTimeParseException e){
                io.print("Error: Please enter a valid date in the correct format.");
                }
            }
            return date;
        }
        /*
	public Order getNewOrderInfo() throws OrderPersistenceException {
            String name = io.readString("Please enter the Customer Name. (Must not be blank).");
            
            String newState;
            newState= io.readString("What is the new State?");
                
            String newProductType;
            newProductType= io.readString("What is the new Product Type?");
            String newAreaString= null;
            BigDecimal newArea= null;
            while (true){
                try{
                    newAreaString = io.readString("What is the new Area (Min. 100 sq ft)?");
                    newArea= new BigDecimal(newAreaString);
                    break;

                } catch (IllegalArgumentException e){
                    io.print("Invalid input. Please enter the correct Element");
                }
            }
		
            Order currentOrder = new Order(-1, name, newState, newProductType, newArea);
            return currentOrder;
	}
        */
	
	public void displayCreateOrderBanner() {
		io.print("=== Create Order ===");
	}
	
	public void displayCreateSuccessBanner() {
		io.readString("Order successfully created. Please hit enter to continue");
	}
	
	public void displayOrderList(List<Order> orderList) {
		for(Order currentOrder : orderList) {
			io.print("\nOrder Number:"+currentOrder.getOrderNumber());
			io.print("Customer: "+currentOrder.getCustomerName());
                        io.print("State: "+currentOrder.getState());
			io.print("TaxRate: " +currentOrder.getTaxRate().toString()+" %");
                        io.print("ProductType: " +currentOrder.getProductType());
                        io.print("Area: " +currentOrder.getArea().toString() +" sq ft");
                        io.print("Cost per Square Foot: $" +currentOrder.getCostPerSquareFoot().toString());
                        io.print("Labor Cost per Square Foot: $" +currentOrder.getLaborCostPerSquareFoot().toString());
                        io.print("Material Cost: $" +currentOrder.getMaterialCost().toString());
                        io.print("Labor Cost: $" +currentOrder.getLaborCost().toString());
                        io.print("Tax: $" +currentOrder.getTax().toString());
                        io.print("Total: $"+currentOrder.getTotal()+"\n");
		}
		io.readString("Please hit enter to continue");
	}
	
	public void displayDisplayAllBanner() {
		io.print("=== Display All Orders ===");
	}
	
	public void displayDisplayOrderBanner() {
		io.print("=== Display Order ===");
	}
	
	public String getNameChoice() {
		return io.readString("Please enter the order name");
	}
	
	public void displayOrder(Order currentOrder, boolean showOrder) {
		if(currentOrder != null) {
                    if (showOrder){
			io.print("\nOrder Number:"+currentOrder.getOrderNumber());
                    }
			io.print("Customer: "+currentOrder.getCustomerName());
                        io.print("State: "+currentOrder.getState());
			io.print("TaxRate: " +currentOrder.getTaxRate().toString()+" %");
                        io.print("ProductType: " +currentOrder.getProductType());
                        io.print("Area: " +currentOrder.getArea().toString() +" sq ft");
                        io.print("Cost per Square Foot: $" +currentOrder.getCostPerSquareFoot().toString());
                        io.print("Labor Cost per Square Foot: $" +currentOrder.getLaborCostPerSquareFoot().toString());
                        io.print("Material Cost: $" +currentOrder.getMaterialCost().toString());
                        io.print("Labor Cost: $" +currentOrder.getLaborCost().toString());
                        io.print("Tax: $" +currentOrder.getTax().toString());
                        io.print("Total: $"+currentOrder.getTotal()+"\n");
		}else {
			io.print("No such order.");
		}

	}
	
	public void displayRemoveOrderBanner() {
		io.print("=== Remove Order ===");
	}
	
	public void displayRemoveResult(Order orderRecord) {
		if(orderRecord != null) {
			io.print("Order successfully removed.");
		}else {
			io.print("No such order.");
		}
		io.readString("Please hit enter to continue");
	}
	
        public int getOrderNumber(){
            return io.readInt("What is the order number?");
        }
	public void displayExitBanner() {
		io.print("Good Bye!!!");
	}
	
	public void displayUnknownCommandBanner() {
		io.print("Unknown Command!!!");
	}
	
	public void displayErrorMessage(String errorMsg) {
		io.print("=== ERROR ===");
		io.print(errorMsg);
	}
        
        //The following functions take in new order/updated order information. If an Order is passed in as a parameter, they will display the order information and ask for an update. 
        public String getCustomerName(Order oldOrder){
            if (oldOrder != null){
                io.print("The current customer name is: "+oldOrder.getCustomerName());
                String newName= io.readString("What is the new Customer Name? Or press enter if no change.");
                return newName;
            }
            String name= io.readString("What is the Customer Name? ");
            return name;
        }
        public String getState(Order oldOrder){
            String newState;

            if (oldOrder!=null){
                io.print("The current State is: "+oldOrder.getState());
                newState=newState= io.readString("What is the new State? Or press enter if no change.");
            } else{
                newState= io.readString("What is the State?");
            }

            return newState;
        }
        
        public String getProductType(Order oldOrder){
            String newProductType;

            if (oldOrder != null){
                io.print("The current Product Type is: "+oldOrder.getProductType());
                newProductType= io.readString("What is the new Product Type (Carpet/Tile/Laminate/Wood)? Or press enter if no change.");
            } else{
                newProductType= io.readString("What is the Product Type (Carpet/Tile/Laminate/Wood)? ");
            }

            return newProductType;
        }
        
        public String getArea(Order oldOrder, int min){
            String newAreaString= null;
            while (true){
                try{
                    if (oldOrder !=null){
                        io.print("The current Area is: "+ oldOrder.getArea().toString());
                        newAreaString = io.readString("What is the new Area (Min. "+min+" sq ft)? Or press enter if no change.");
                    } else {
                        newAreaString = io.readString("What is the Area (Min. "+min+" sq ft)?");
                    }
                    
                    if (newAreaString.equals("")) {
                        break;
                    } 
                    BigDecimal newArea= new BigDecimal(newAreaString);
                    break;
                } catch (IllegalArgumentException e){
                    io.print("Invalid input. Please try again.");
                }
            }
            return newAreaString;
        }
        /*
        public String[] getUpdates(){
            
            String newName= io.readString("What is the new Customer Name? Or press enter if no change.");
            String newState;
             while (true){

                newState= io.readString("What is the new State? Or press enter if no change.");
                if (newState.equals("") || newState.equals("Texas") || newState.equals("Washington")|| newState.equals("Kentucky")|| newState.equals("California")){
                    break;
                }

            }

            String newProductType;
            while (true){

                newProductType= io.readString("What is the new Product Type (Carpet/Tile/Laminate/Wood)? Or press enter if no change.");
                if (newProductType.equals("") || newProductType.equals("Carpet") || newProductType.equals("Tile")|| newProductType.equals("Laminate")|| newProductType.equals("Wood")){
                    break;
                }
            }
            
            
            String newAreaString= null;
            while (true){
                try{
                    newAreaString = io.readString("What is the new Area (Min. 100 sq ft)? Or press enter if no change.");
                    
                    if (newAreaString.equals("")) {
                        break;
                    } 
                    BigDecimal newArea= new BigDecimal(newAreaString);
                    if (newArea.compareTo(new BigDecimal(100))>=0){
                        break;
                    }

                } catch (IllegalArgumentException e){
                    io.print("Invalid input. Please try again.");
            }
                
            }

            String[] updatedParam= {newName, newState, newProductType, newAreaString};
            
            return updatedParam;
        }
        */
        public boolean askGoAhead(){
            while (true){
                String yesOrNo= io.readString("Add/Update with this information? (Yes/No)");
                if (yesOrNo.equals("Yes")){
                    return true;
                }
                else if (yesOrNo.equals("No")){
                    return false;
                }
            }
        }
        
        public void displayUpdateSuccessfulBanner(){
            io.print("=== Order Successfully Updated ===");
        }
        
        public void displayNoSuchOrder(){
            io.print("No such order exists. Please select from the list of available orders");
        }
        
        public void displayExportSuccessfulBanner(){
            io.print("=== All Data Successfully Exported ===");
        }
}
