/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.Service;




import MainFolder.DAO.OrderAuditDao;
import MainFolder.DAO.OrderDaoInterface;
import MainFolder.DAO.OrderPersistenceException;
import MainFolder.DTO.Order;
import MainFolder.DTO.Sort;
import MainFolder.DTO.SortTrades;
import MainFolder.DTO.Trade;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import s.S;

/**
 *
 * @author recyn
 */
@Component
public class OrderServiceLayerImpl implements OrderServiceLayer {
    OrderDaoInterface dao;
    private OrderAuditDao auditDao;
    @Autowired
    public OrderServiceLayerImpl(OrderDaoInterface dao){
        this.dao=dao;
    }
    
    //Adds a order to the DAO and makes sure the added order doesn't already exist and has valid parameters. 
    @Override
    public void createOrder(Order order, boolean buy) throws OrderDuplicateIdException, OrderDataValidationException, OrderPersistenceException {

        
        if (dao.getBuyOrder(order.getOrderID()) != null && dao.getSellOrder(order.getOrderID()) != null){
            throw new OrderDuplicateIdException("Error: Could not Create order. Order Name "
            + order.getOrderID()
            + " already exists");
        }
        
        //Now validate all the fields on the given Order object.
        //This method will throw an exception if any of the validation rtules are violated.
        validateOrderData(order);
        
        //We passed all our business rules checks so go ahead and persist the order object
        if (buy){
            dao.addBuyOrder(order);
        } else {
            dao.addSellOrder(order);
        }

    }

    @Override
    public List<Order> getAllBuyOrders() throws OrderPersistenceException {
        return dao.getAllBuyOrders();
    }
    
    @Override
    public List<Order> getAllSellOrders() throws OrderPersistenceException {
        return dao.getAllSellOrders();
    }

    @Override
    public Order getBuyOrder(String orderID) throws OrderPersistenceException {
        return dao.getBuyOrder(orderID);
    }
    
    @Override
    public Order getSellOrder(String orderID) throws OrderPersistenceException {
        return dao.getSellOrder(orderID);
    }
    //Removes a order and will return the removed order if it exists. If not, it returns null. 
    @Override
    public Order removeBuyOrder(String orderID) throws OrderPersistenceException {
        Order removedOrder=null;
  
        removedOrder=dao.removeBuyOrder(orderID);
        
        //Write to audit log

        
        return removedOrder;
    }
    
    @Override
    public Order removeSellOrder(String orderID) throws OrderPersistenceException {
        Order removedOrder=null;
  
        removedOrder=dao.removeSellOrder(orderID);
        
        //Write to audit log

        
        return removedOrder;
    }
    //Checks to see if a order has values for all its member fields, except for Var2, because Var2 is an int and cannot be null. 
    //Any error with the Var2 would be caught in either the view or controller when converting a String input to an integer. 
    //Ideally this would also check whether or not the fields are also empty Strings. 
    private void validateOrderData(Order order) throws OrderDataValidationException, OrderDuplicateIdException, OrderPersistenceException{
        if (order.getOrderID()==null
                || order.getOrderID()==null
                || order.getPrice()==null
                || order.getQuantity()==0){
            throw new OrderDataValidationException("Error: All fields [OrderID, Price, Quantity] are required. Quantity cannot be 0.");
        }
    }
    
    //Returns a list of orders of a certain input element, which is an enum. 
    /*@Override
    public List<Order> getOrdersOfType(Type5 var5) throws OrderPersistenceException{
        List<Order> allOrders= dao.getAllOrders();
        List<Order> allOrdersOfType= allOrders.stream()
                .filter((c)-> c.getVar5()== var5)
                .collect(Collectors.toList());
        return allOrdersOfType;
    }
    */

    @Override
    public List<Order> sortBuyOrders() throws OrderPersistenceException {
        List<Order> buyOrders= dao.getAllBuyOrders();
        Collections.sort(buyOrders, new Sort());
        return buyOrders;
    }
    
    @Override
    public List<Order> sortSellOrders() throws OrderPersistenceException {
        List<Order> sellOrders= dao.getAllSellOrders();
        Collections.sort(sellOrders, new Sort());
        return sellOrders;
    }

    @Override
    public List<Trade> sortTrades() throws OrderPersistenceException {
        List<Trade> allTrades=dao.getAllTrades();
        Collections.sort(allTrades, new SortTrades());
        return allTrades;
    }
    @Override
    public Trade matchTopOrder() throws Exception {
        List<Order> buyOrdersList= sortBuyOrders();
        List<Order> sellOrdersList= sortSellOrders();
        if (buyOrdersList.isEmpty() || sellOrdersList.isEmpty()){
            throw new Exception("Error: No orders to match.");
        }
        Order buyOrder= buyOrdersList.get(0);
        Order sellOrder= sellOrdersList.get(0);
        int buyShares= buyOrder.getQuantity();
        int sellShares= sellOrder.getQuantity();
        int remaining=0;
        if (buyShares<sellShares){
            remaining= sellShares-buyShares;
            sellOrder.setQuantity(remaining);
            dao.removeBuyOrder(buyOrder.getOrderID());
            dao.editSellOrder(sellOrder);
            BigDecimal sum= buyOrder.getPrice().add(sellOrder.getPrice());
            BigDecimal soldPrice= sum.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
            Trade trade= new Trade("0", soldPrice, buyOrder.getQuantity());
            dao.addTrade(trade);
            return trade;
        } else if (sellShares<buyShares){
            remaining=buyShares-sellShares;
            buyOrder.setQuantity(remaining);
            dao.removeSellOrder(sellOrder.getOrderID());
            dao.editBuyOrder(buyOrder);
            BigDecimal sum= buyOrder.getPrice().add(sellOrder.getPrice());
            BigDecimal soldPrice= sum.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
            Trade trade= new Trade("0", soldPrice, sellOrder.getQuantity());
            dao.addTrade(trade);
            return trade;
        }
        
        else if (sellShares==buyShares) {
            dao.removeBuyOrder(buyOrder.getOrderID());
            dao.removeSellOrder(sellOrder.getOrderID());
            BigDecimal sum= buyOrder.getPrice().add(sellOrder.getPrice());
            BigDecimal soldPrice= sum.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
            Trade trade= new Trade("0", soldPrice, sellOrder.getQuantity());
            dao.addTrade(trade);
            return trade;
        }
        throw new Exception("Error: unable to match top trade");
    }
    
    @Override
    public void matchAllOrders() throws Exception{
        List<Order> buyOrdersList= sortBuyOrders();
        List<Order> sellOrdersList= sortSellOrders();
      while (!buyOrdersList.isEmpty() && !sellOrdersList.isEmpty()){

        Order buyOrder= buyOrdersList.get(0);
        Order sellOrder= sellOrdersList.get(0);
        int buyShares= buyOrder.getQuantity();
        int sellShares= sellOrder.getQuantity();
        int remaining=0;
        if (buyShares<sellShares){
            remaining= sellShares-buyShares;
            sellOrder.setQuantity(remaining);
            dao.removeBuyOrder(buyOrder.getOrderID());
            buyOrdersList.remove(0);
            dao.editSellOrder(sellOrder);
            sellOrdersList.set(0, sellOrder);
            BigDecimal sum= buyOrder.getPrice().add(sellOrder.getPrice());
            BigDecimal soldPrice= sum.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
            Trade trade= new Trade("0", soldPrice, buyOrder.getQuantity());
            dao.addTrade(trade);
        } else if (sellShares<buyShares){
            remaining=buyShares-sellShares;
            buyOrder.setQuantity(remaining);
            dao.removeSellOrder(sellOrder.getOrderID());
            sellOrdersList.remove(0);
            dao.editBuyOrder(buyOrder);
            buyOrdersList.set(0, buyOrder);
            BigDecimal sum= buyOrder.getPrice().add(sellOrder.getPrice());
            BigDecimal soldPrice= sum.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
            Trade trade= new Trade("0", soldPrice, sellOrder.getQuantity());
            dao.addTrade(trade);
        }
        
        else if (sellShares==buyShares) {
            dao.removeBuyOrder(buyOrder.getOrderID());
            buyOrdersList.remove(0);
            dao.removeSellOrder(sellOrder.getOrderID());
            sellOrdersList.remove(0);
            BigDecimal sum= buyOrder.getPrice().add(sellOrder.getPrice());
            BigDecimal soldPrice= sum.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
            Trade trade= new Trade("0", soldPrice, sellOrder.getQuantity());
            dao.addTrade(trade);
        }
        else {
            throw new Exception("Error: unable to match top trade");
        }
        }
    }
    
    //still need to consider the trade class
    
    //generate a random order
    public Order addBuyRandomOrder() throws OrderPersistenceException{
        int randomQuantity= S.randomint(20, 50);
        BigDecimal randomPrice= new BigDecimal(S.randomfloat(190, 191)).setScale(2, RoundingMode.HALF_UP);
        Order randomOrder= new Order("0", randomPrice, randomQuantity);
        dao.addBuyOrder(randomOrder);
        return randomOrder;
    }
    
    public Order addSellRandomOrder() throws OrderPersistenceException{
        int randomQuantity= S.randomint(20, 50);
        BigDecimal randomPrice= new BigDecimal(S.randomfloat(190, 191)).setScale(2, RoundingMode.HALF_UP);
        Order randomOrder= new Order("0", randomPrice, randomQuantity);
        dao.addSellOrder(randomOrder);
        return randomOrder;
    }
    
    public void generateNRandomOrders(int N) throws OrderPersistenceException{
        for (int i=0; i< N; i++){
            addBuyRandomOrder();
            addSellRandomOrder();
        }
        
    }
    
    public void clearTables() throws Exception{
        dao.clearTables(true, true, true);
    }

    @Override
    public Trade matchAnOrder(String orderID, boolean buy) throws Exception {
        List<Order> buyOrdersList= sortBuyOrders();
        List<Order> sellOrdersList= sortSellOrders();
        if (buy){
            
            if (buyOrdersList.isEmpty() || sellOrdersList.isEmpty()){
                throw new Exception("Error: No orders to match.");
            }
            Order buyOrder= dao.getBuyOrder(orderID);
            Order sellOrder= sellOrdersList.get(0);
            int buyShares= buyOrder.getQuantity();
            int sellShares= sellOrder.getQuantity();
            int remaining=0;
            if (buyShares<sellShares){
                remaining= sellShares-buyShares;
                sellOrder.setQuantity(remaining);
                dao.removeBuyOrder(buyOrder.getOrderID());
                dao.editSellOrder(sellOrder);
                BigDecimal sum= buyOrder.getPrice().add(sellOrder.getPrice());
                BigDecimal soldPrice= sum.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
                Trade trade= new Trade("0", soldPrice, buyOrder.getQuantity());
                dao.addTrade(trade);
                return trade;
            } else if (sellShares<buyShares){
                remaining=buyShares-sellShares;
                buyOrder.setQuantity(remaining);
                dao.removeSellOrder(sellOrder.getOrderID());
                dao.editBuyOrder(buyOrder);
                BigDecimal sum= buyOrder.getPrice().add(sellOrder.getPrice());
                BigDecimal soldPrice= sum.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
                Trade trade= new Trade("0", soldPrice, sellOrder.getQuantity());
                dao.addTrade(trade);
                return trade;
            }
        
            else if (sellShares==buyShares) {
                dao.removeBuyOrder(buyOrder.getOrderID());
                dao.removeSellOrder(sellOrder.getOrderID());
                BigDecimal sum= buyOrder.getPrice().add(sellOrder.getPrice());
                BigDecimal soldPrice= sum.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
                Trade trade= new Trade("0", soldPrice, sellOrder.getQuantity());
                dao.addTrade(trade);
                return trade;
            }
            
        } else {
            if (buyOrdersList.isEmpty() || sellOrdersList.isEmpty()){
                throw new Exception("Error: No orders to match.");
            }
            Order buyOrder= buyOrdersList.get(0);
            Order sellOrder= dao.getSellOrder(orderID);
            int buyShares= buyOrder.getQuantity();
            int sellShares= sellOrder.getQuantity();
            int remaining=0;
            if (buyShares<sellShares){
                remaining= sellShares-buyShares;
                sellOrder.setQuantity(remaining);
                dao.removeBuyOrder(buyOrder.getOrderID());
                dao.editSellOrder(sellOrder);
                BigDecimal sum= buyOrder.getPrice().add(sellOrder.getPrice());
                BigDecimal soldPrice= sum.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
                Trade trade= new Trade("0", soldPrice, buyOrder.getQuantity());
                dao.addTrade(trade);
                return trade;
            } else if (sellShares<buyShares){
                remaining=buyShares-sellShares;
                buyOrder.setQuantity(remaining);
                dao.removeSellOrder(sellOrder.getOrderID());
                dao.editBuyOrder(buyOrder);
                BigDecimal sum= buyOrder.getPrice().add(sellOrder.getPrice());
                BigDecimal soldPrice= sum.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
                Trade trade= new Trade("0", soldPrice, sellOrder.getQuantity());
                dao.addTrade(trade);
                return trade;
            }
        
            else if (sellShares==buyShares) {
                dao.removeBuyOrder(buyOrder.getOrderID());
                dao.removeSellOrder(sellOrder.getOrderID());
                BigDecimal sum= buyOrder.getPrice().add(sellOrder.getPrice());
                BigDecimal soldPrice= sum.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);
                Trade trade= new Trade("0", soldPrice, sellOrder.getQuantity());
                dao.addTrade(trade);
                return trade;
            }
        }
        throw new Exception("Error: unable to match top trade");
    }

    @Override
    public void clearTradeLog() {
        try {
            dao.clearTables(false, false, true);
        } catch (Exception ex) {
            Logger.getLogger(OrderServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
