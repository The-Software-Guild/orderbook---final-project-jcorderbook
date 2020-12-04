/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.Service;



import MainFolder.DAO.OrderPersistenceException;
import MainFolder.DTO.Order;
import MainFolder.DTO.Trade;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author recyn
 */
public interface OrderServiceLayer {
    void createOrder(Order dto, boolean buy) throws
            OrderDuplicateIdException,
            OrderDataValidationException,
            OrderPersistenceException;
    
    List<Order> getAllBuyOrders() throws
            OrderPersistenceException;
    List<Order> getAllSellOrders() throws
            OrderPersistenceException;
    Order getBuyOrder(String orderID) throws
            OrderPersistenceException;
    
    Order getSellOrder(String orderID) throws
            OrderPersistenceException; 
    
    Order removeBuyOrder(String orderID) throws
            OrderPersistenceException;
    
    Order removeSellOrder(String orderID) throws
            OrderPersistenceException;
    
    List<Order> sortBuyOrders() throws OrderPersistenceException;
    
    List<Order> sortSellOrders() throws OrderPersistenceException;
    
    Trade matchTopOrder() throws Exception;
    
    void matchAllOrders() throws Exception;
    
    Trade matchAnOrder(String orderID, boolean buy) throws Exception;
    
    List<Trade> sortTrades() throws OrderPersistenceException; 
    
    void clearTradeLog();
}
