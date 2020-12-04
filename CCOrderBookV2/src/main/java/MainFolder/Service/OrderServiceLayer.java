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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author recyn
 */
public interface OrderServiceLayer {
    void createOrder(Order dto, boolean buy) throws
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
    
    Trade matchTopOrder() throws OrderMatchException, OrderPersistenceException, TradePersistenceException;
    
    void matchAllOrders() throws OrderMatchException, OrderPersistenceException, TradePersistenceException;
    
    Trade matchAnOrder(String orderID, boolean buy) throws OrderMatchException, OrderPersistenceException, TradePersistenceException;
    
    List<Trade> sortTrades() throws TradePersistenceException; 
    
    void clearTradeLog();
    
    Trade getTrade(String tradeID) throws TradePersistenceException;
    
    List<Trade> tradesByDate(LocalDate date) throws TradePersistenceException;
    
    List<Trade> tradesByQuantity (int quantity) throws TradePersistenceException;
    
    List<Trade> tradesByTime(LocalDate date, LocalTime time, boolean minutes, boolean seconds) throws TradePersistenceException;
    
    Order editBuyOrder(String[] updatedParam) throws OrderPersistenceException;
    
    Order editSellOrder(String[] updatedParam) throws OrderPersistenceException;
    
    void generateNRandomOrders(int N) throws OrderPersistenceException;
    
    void clearTables() throws OrderPersistenceException;
    
    boolean getBuyOrSell(String OrderID) throws OrderPersistenceException;
    
    boolean isEmpty() throws OrderPersistenceException;
}
