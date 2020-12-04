/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.Service;



import MainFolder.DAO.OrderDaoInterface;
import MainFolder.DAO.OrderPersistenceException;
import MainFolder.DTO.Order;
import MainFolder.DTO.Trade;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author recyn
 */
public class OrderDaoStub implements OrderDaoInterface {
    Map<String, Order> buyOrders= new HashMap<>();
    Map<String, Order> sellOrders= new HashMap<>();
    Map<String, Trade> trades= new HashMap<>();
    
    
    public OrderDaoStub(){
        //test BuyOrder first
        Order buyTest1 = new Order("BORD1", new BigDecimal("190.99"), 25);
        Order buyTest2 = new Order("BORD2", new BigDecimal("190.99"), 25);
        Order buyTest3 = new Order("BORD3", new BigDecimal("190.96"), 25);
        Order buyTest4 = new Order("BORD4", new BigDecimal("191.00"), 25);
        buyOrders.put(buyTest1.getOrderID(), buyTest1);
        buyOrders.put(buyTest2.getOrderID(), buyTest2);
        buyOrders.put(buyTest3.getOrderID(), buyTest3);
        buyOrders.put(buyTest4.getOrderID(), buyTest4);
        Order sellTest1 = new Order("SORD2", new BigDecimal("190.99"), 30);
        Order sellTest2 = new Order("SORD3", new BigDecimal("190.99"), 25);
        Order sellTest3 = new Order("SORD4", new BigDecimal("190.96"), 25);
        Order sellTest4 = new Order("SORD5", new BigDecimal("191.00"), 25);
        sellOrders.put(sellTest1.getOrderID(), sellTest1);
        sellOrders.put(sellTest2.getOrderID(), sellTest2);
        sellOrders.put(sellTest3.getOrderID(), sellTest3);
        sellOrders.put(sellTest4.getOrderID(), sellTest4);
        LocalDateTime time= LocalDateTime.now();
        LocalDateTime truncated= time.truncatedTo(ChronoUnit.SECONDS);
        Trade tradeTest1 = new Trade("TRADE1", new BigDecimal("190.99"), 25, truncated);
        Trade tradeTest2 = new Trade("TRADE2", new BigDecimal("190.98"), 25, LocalDateTime.of(2020, Month.MARCH, 18, 14, 4, 4));
        Trade tradeTest3 = new Trade("TRADE3", new BigDecimal("190.96"), 30, truncated);
        Trade tradeTest4 = new Trade("TRADE4", new BigDecimal("190.95"), 30, LocalDateTime.of(2020, Month.MARCH, 18, 14, 4, 3));
        trades.put(tradeTest1.getTradeID(), tradeTest1);
        trades.put(tradeTest2.getTradeID(), tradeTest2);
        trades.put(tradeTest3.getTradeID(), tradeTest3);
        trades.put(tradeTest4.getTradeID(), tradeTest4);
    }



    @Override
    public Order addBuyOrder(Order order) throws OrderPersistenceException {
        if (!order.getOrderID().equals("BORD1")){
            return null;
        } else{
            return buyOrders.get("BORD1");
        }
    }

    @Override
    public Order addSellOrder(Order order) throws OrderPersistenceException {
        if (!order.getOrderID().equals("SORD2")){
            return null;
        } else{
            return sellOrders.get("SORD2");
        }
    }

    @Override
    public List<Order> getAllBuyOrders() throws OrderPersistenceException {
        List<Order> output= new ArrayList(buyOrders.values());
        return output;
    }

    @Override
    public List<Order> getAllSellOrders() throws OrderPersistenceException {
        List<Order> output= new ArrayList(sellOrders.values());
        return output;
    }

    @Override
    public Order getBuyOrder(String orderID) throws OrderPersistenceException {
        return buyOrders.get(orderID);
    }

    @Override
    public Order getSellOrder(String orderID) throws OrderPersistenceException {
        return sellOrders.get(orderID);
    }

    @Override
    public Order removeBuyOrder(String orderID) throws OrderPersistenceException {
        if (!orderID.equals("BORD1")){
            return null;
        } else{
            return buyOrders.get("BORD1");
        }
    }

    @Override
    public Order removeSellOrder(String orderID) throws OrderPersistenceException {
        if (!orderID.equals("SORD2")){
            return null;
        } else{
            return sellOrders.get("SORD2");
        }
    }

    @Override
    public Order editBuyOrder(Order updatedOrder) throws OrderPersistenceException {
        Order output= buyOrders.replace(updatedOrder.getOrderID(), updatedOrder);

        return output;
    }

    @Override
    public Order editSellOrder(Order updatedOrder) throws OrderPersistenceException {
        Order output= sellOrders.replace(updatedOrder.getOrderID(), updatedOrder);
        return output;
    }

    @Override
    public String getTradeNumber() throws TradePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Trade addTrade(Trade trade) throws TradePersistenceException {
        trades.put(trade.getTradeID(), trade);
        return null;
    }

    @Override
    public void clearTables(boolean clearBuy, boolean clearSell, boolean clearTrades) throws OrderPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Trade> getAllTrades() throws TradePersistenceException {
        List<Trade> allTrades= new ArrayList(trades.values());
        return allTrades;
    }

    @Override
    public Trade getTrade(String tradeID) throws TradePersistenceException {
        return trades.get(tradeID);
    }

    @Override
    public Map<String, Order> getMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
