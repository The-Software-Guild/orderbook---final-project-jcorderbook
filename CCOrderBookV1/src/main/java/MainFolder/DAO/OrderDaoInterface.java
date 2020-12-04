/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DAO;

import MainFolder.DTO.Order;
import MainFolder.DTO.Trade;
import java.util.List;
import java.util.Map;

/**
 *
 * @author recyn
 */
public interface OrderDaoInterface {

    Order addBuyOrder(Order order) throws OrderPersistenceException;
    
    Order addSellOrder(Order order) throws OrderPersistenceException;
    
    List<Order> getAllBuyOrders() throws OrderPersistenceException;
    
    List<Order> getAllSellOrders() throws OrderPersistenceException;
    
    Order getBuyOrder (String orderID) throws OrderPersistenceException;
    
    Order getSellOrder (String orderID) throws OrderPersistenceException;
    
    Order removeBuyOrder(String orderID) throws OrderPersistenceException;
    
    Order removeSellOrder(String orderID) throws OrderPersistenceException;
    
    Order editBuyOrder(Order updatedOrder) throws OrderPersistenceException;
    
    Order editSellOrder(Order updatedOrder) throws OrderPersistenceException;
    
    Map<String, Order> getMap();
    
    String getTradeNumber() throws OrderPersistenceException;
    
    Trade addTrade(Trade trade) throws OrderPersistenceException;
    
    public void clearTables(boolean clearBuy, boolean clearSell, boolean clearTrades) throws Exception;
    
    List<Trade> getAllTrades() throws OrderPersistenceException;

}
