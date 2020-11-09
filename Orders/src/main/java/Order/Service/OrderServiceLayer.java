/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Service;




import Order.DAO.OrderPersistenceException;
import Order.DTO.Order;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author recyn
*/
public interface OrderServiceLayer {
    void createOrder(String date, Order order) throws
            
            OrderDataValidationException,
            OrderPersistenceException;
    
    List<Order> getAllOrders(String date) throws
            OrderPersistenceException;
    
    Order getOrder(String date, int orderNumber) throws
            OrderPersistenceException;
    Order removeOrder(String date, int orderNumber) throws
            OrderPersistenceException;
    
    Order updateOrder(String date, int orderNumber, String[] updatedParam, boolean goAhead) throws OrderPersistenceException;
    
    void exportData() throws IOException, OrderPersistenceException;
    
    BigDecimal findTaxRate(String state) throws OrderPersistenceException;
    
    void setTheRest(Order order) throws OrderPersistenceException;
    
    String getAbbreviation(String state) throws OrderPersistenceException;
    
    String getFullStateName(String abbrev) throws OrderPersistenceException;
    
    BigDecimal getCostPerSquareFoot(String productType) throws OrderPersistenceException;
    
    BigDecimal getLaborCostPerSquareFoot(String productType) throws OrderPersistenceException;
    
    boolean validateName(String name);
}
