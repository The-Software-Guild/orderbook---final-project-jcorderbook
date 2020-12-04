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
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author recyn
 */
public class OrderServiceLayerImplTest {
    OrderDaoInterface dao= new OrderDaoStub();
    OrderServiceLayer service= new OrderServiceLayerImpl(dao);
    public OrderServiceLayerImplTest() throws Exception {
        
    
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }


    
    
    @Test
    public void testCreateOrder(){
      //ARRANGE
      Order buyTest1 = new Order("BORD3", new BigDecimal("190.99"), 25);
       
    //ACT
        try{
            service.createOrder(buyTest1, true);
        } catch(OrderPersistenceException | OrderDataValidationException e ){
            fail("Order was valid. No exception should have been thrown.");
    }
    }
        
    @Test
    public void testCreateInvalidOrder(){
        Order buyTest1 = new Order(null, null, 0);
        try{
            service.createOrder(buyTest1, true);
            fail("Order was invalid, an exception should've been thrown");
        } catch(OrderDataValidationException e ){
            return;
        } catch(OrderPersistenceException e){
            fail("Wrong exception thrown");
        }
        Order buyTest2 = new Order(null, new BigDecimal("190.99"), 25);
        try{
            service.createOrder(buyTest2, true);
            fail("Order was invalid, an exception should've been thrown");
        } catch(OrderDataValidationException e ){
            return;
        } catch(OrderPersistenceException e){
            fail("Wrong exception thrown");
        }
        Order buyTest3 = new Order("BORD3", null, 25);
        try{
            service.createOrder(buyTest2, true);
            fail("Order was invalid, an exception should've been thrown");
        } catch(OrderDataValidationException e ){
            return;
        } catch(OrderPersistenceException e){
            fail("Wrong exception thrown");
        }
        Order buyTest4= new Order("BORD3", new BigDecimal("190.99"), 0);
        try{
            service.createOrder(buyTest2, true);
            fail("Order was invalid, an exception should've been thrown");
        } catch(OrderDataValidationException e ){
            return;
        } catch(OrderPersistenceException e){
            fail("Wrong exception thrown");
        }
    }

    @Test
    public void testGetAllBuyOrders() throws Exception {
        Order buyTest1 = new Order("BORD1", new BigDecimal("190.99"), 25);
        assertEquals(4, service.getAllBuyOrders().size(), "Should only have four Buy Orders");
        assertTrue(service.getAllBuyOrders().contains(buyTest1), "The list should contain BORD1");
    }

    @Test
    public void testGetAllSellOrders() throws OrderPersistenceException {
        Order sellTest1 = new Order("SORD2", new BigDecimal("190.99"), 30);
        assertEquals(4, service.getAllSellOrders().size(), "Should only have four Sell Orders");
        assertTrue(service.getAllSellOrders().contains(sellTest1), "The should contain SORD2");
    }

    @Test
    public void testGetBuyOrder() throws OrderPersistenceException {
        Order buyTest1 = new Order("BORD1", new BigDecimal("190.99"), 25);
        assertEquals(buyTest1, service.getBuyOrder("BORD1"), "The gotten order should be BORD1");
        
        assertNull(service.getBuyOrder("BORD6"), "Null should be returned for a nonexistent orderID");
    }

    @Test
    public void testGetSellOrder() throws OrderPersistenceException {
        Order sellTest1 = new Order("SORD2", new BigDecimal("190.99"), 30);
        assertEquals(sellTest1, service.getSellOrder("SORD2"), "The gotten order should be SORD2");
        
        assertNull(service.getBuyOrder("SORD6"), "Null should be returned for a nonexistent orderID");
    }

    @Test
    public void testRemoveBuyOrder() throws OrderPersistenceException {
        Order buyTest1 = new Order("BORD1", new BigDecimal("190.99"), 25);
        Order removedBuyOrder= service.removeBuyOrder("BORD1");
        assertEquals(buyTest1, removedBuyOrder, "The removed order should be buyTest1");
        assertNull(service.removeBuyOrder("BORD2"), "Removing a nonexistent order should return null");
    }

    @Test
    public void testRemoveSellOrder() throws OrderPersistenceException {
        Order sellTest1 = new Order("SORD2", new BigDecimal("190.99"), 30);
        Order removedSellOrder= service.removeSellOrder("SORD2");
        assertEquals(sellTest1, removedSellOrder, "The removed order should be sellTest1");
        assertNull(service.removeSellOrder("SORD3"), "Removing a nonexistent order should return null");
    }
    
    @Test
    public void testSortBuyOrders() throws OrderPersistenceException {
        Order buyTest1 = new Order("BORD1", new BigDecimal("190.99"), 25);
        Order buyTest2 = new Order("BORD2", new BigDecimal("190.99"), 25);
        Order buyTest3 = new Order("BORD3", new BigDecimal("190.96"), 25);
        Order buyTest4 = new Order("BORD4", new BigDecimal("191.00"), 25);
        List<Order> buyList= service.sortBuyOrders();
        //buyList.stream().forEach((p)-> System.out.println(p.getOrderID()));
        assertEquals(buyTest4,buyList.get(0), "First should be buyTest4");
        assertEquals(buyTest1,buyList.get(1), "Second should be buyTest1");
        assertEquals(buyTest2,buyList.get(2), "Third should be buyTest2");
        assertEquals(buyTest3,buyList.get(3), "Fourth should be buyTest3");
    }

    @Test
    public void testSortSellOrders() throws OrderPersistenceException {
        Order sellTest1 = new Order("SORD2", new BigDecimal("190.99"), 30);
        Order sellTest2 = new Order("SORD3", new BigDecimal("190.99"), 25);
        Order sellTest3 = new Order("SORD4", new BigDecimal("190.96"), 25);
        Order sellTest4 = new Order("SORD5", new BigDecimal("191.00"), 25);
        List<Order> sellList= service.sortSellOrders();
        //sellList.stream().forEach((p)-> System.out.println(p.getOrderID()));
        assertEquals(sellTest4,sellList.get(0), "First should be sellTest5");
        assertEquals(sellTest1,sellList.get(1), "Second should be sellTest2");
        assertEquals(sellTest2,sellList.get(2), "Third should be sellTest3");
        assertEquals(sellTest3,sellList.get(3), "Fourth should be sellTest4");
    }
    
    @Test
    public void matchTopOrder() throws OrderMatchException, OrderPersistenceException, TradePersistenceException {
        Trade trade= service.matchTopOrder();
        Trade expected= new Trade("TRADE1", new BigDecimal("191.00"), 25, LocalDateTime.now());
        assertEquals(trade.getExecutedPrice(), expected.getExecutedPrice(), "Executed Price should be the same");
        assertEquals(trade.getQuantity(), expected.getQuantity(), "Quantity should be the same");
    }
    

    @Test
    public void testMatchAnOrder() throws OrderMatchException, OrderPersistenceException, TradePersistenceException {
        Trade trade= service.matchAnOrder("BORD1", true);
        Trade expected= new Trade("TRADE1", new BigDecimal("191.00"), 25, LocalDateTime.now());
        assertEquals(trade.getExecutedPrice(), expected.getExecutedPrice(), "Executed Price should be the same");
        assertEquals(trade.getQuantity(), expected.getQuantity(), "Quantity should be the same");
    }
    
    @Test
    public void testSortTrades() throws TradePersistenceException {
        Trade tradeTest1 = new Trade("TRADE1", new BigDecimal("190.99"), 25, LocalDateTime.now());
        Trade tradeTest2 = new Trade("TRADE2", new BigDecimal("190.98"), 25, LocalDateTime.of(2020, Month.MARCH, 18, 14, 4, 4));
        Trade tradeTest3 = new Trade("TRADE3", new BigDecimal("190.96"), 30, LocalDateTime.now());
        Trade tradeTest4 = new Trade("TRADE4", new BigDecimal("190.95"), 30, LocalDateTime.of(2020, Month.MARCH, 18, 14, 4, 3));

        List<Trade> trades=service.sortTrades();


        assertEquals(tradeTest1.getTradeID(),trades.get(0).getTradeID(), "First should be TRADE1");
        assertEquals(tradeTest2.getTradeID(),trades.get(1).getTradeID(), "Second should be TRADE2");
        assertEquals(tradeTest3.getTradeID(),trades.get(2).getTradeID(), "Third should be TRADE3");
        assertEquals(tradeTest4.getTradeID(),trades.get(3).getTradeID(), "Fourth should be TRADE4");

    }
    /*
    @Override
    public void clearTradeLog() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */
    @Test
    public void testGetTrade() throws TradePersistenceException {
        Trade trade=service.getTrade("TRADE1");
        Trade expected= new Trade("TRADE1", new BigDecimal("190.99"), 25, LocalDateTime.now());
        assertEquals(trade.getExecutedPrice(), expected.getExecutedPrice(), "Executed Price should be the same");
        assertEquals(trade.getQuantity(), expected.getQuantity(), "Quantity should be the same");
    }

    @Test
    public void testTradesByDateAndTime() throws TradePersistenceException {
        Trade tradeTest1 = new Trade("TRADE1", new BigDecimal("190.99"), 25, LocalDateTime.now());
        Trade tradeTest2 = new Trade("TRADE2", new BigDecimal("190.98"), 25, LocalDateTime.of(2020, Month.MARCH, 18, 14, 4, 4));
        Trade tradeTest3 = new Trade("TRADE3", new BigDecimal("190.96"), 30, LocalDateTime.now());
        Trade tradeTest4 = new Trade("TRADE4", new BigDecimal("190.95"), 30, LocalDateTime.of(2020, Month.MARCH, 18, 14, 4, 3));
        
        List<Trade> trades= service.tradesByDate(LocalDate.of(2020, Month.MARCH, 18));
        assertTrue(trades.contains(tradeTest2));
        assertTrue(trades.contains(tradeTest4));
        assertEquals(2, trades.size(), "Size should be 2");
        trades= service.tradesByTime(LocalDate.of(2020, Month.MARCH, 18), LocalTime.of(14, 4, 4), true, true);
        assertTrue(trades.contains(tradeTest2));
        assertEquals(1, trades.size(), "Size should be 1");
    }
    
    @Test
    public void testTradesByQuantity() throws TradePersistenceException {
        Trade tradeTest1 = new Trade("TRADE1", new BigDecimal("190.99"), 25, LocalDateTime.now());
        Trade tradeTest2 = new Trade("TRADE2", new BigDecimal("190.98"), 25, LocalDateTime.of(2020, Month.MARCH, 18, 14, 4, 4));
        Trade tradeTest3 = new Trade("TRADE3", new BigDecimal("190.96"), 30, LocalDateTime.now());
        Trade tradeTest4 = new Trade("TRADE4", new BigDecimal("190.95"), 30, LocalDateTime.of(2020, Month.MARCH, 18, 14, 4, 3));
        int quantity= 25;
        List<Trade> trades=service.tradesByQuantity(quantity);
        trades.stream().forEach((p)-> System.out.println(p.getTradeID()));
        assertTrue(trades.get(0).getTradeID().equals("TRADE1"));
        assertTrue(trades.get(1).getTradeID().equals("TRADE2"));
        assertEquals(2, trades.size(), "Size should be 2");
    }


    @Test
    public void testEditBuyOrder() throws OrderPersistenceException {
        String[] updatedParam= {"BORD1", "190.60", "50"};
        Order expected= new Order("BORD1", new BigDecimal("190.60"), 50);
        Order updatedOrder= service.editBuyOrder(updatedParam);
        assertEquals(expected, updatedOrder, "Expected and updatedOrder should be the same.");
        
        try{
            String[] wrongParam= {"BORD6", "190.60", "50"};
            service.editBuyOrder(wrongParam);
            fail("Exception should have been thrown");
        } catch (OrderPersistenceException e){
            return;
        }
        String[] updatedParam2= {"BORD1", "", ""};
        Order expected2= new Order("BORD1", new BigDecimal("190.99"), 25);
        Order updatedOrder2= service.editBuyOrder(updatedParam);
        assertEquals(expected2, updatedOrder2, "Expected and updatedOrder should be the same.");
        
        
    }
    
    @Test
    public void editSellOrder() throws OrderPersistenceException {
        String[] updatedParam= {"SORD2", "190.60", "50"};
        Order expected= new Order("SORD2", new BigDecimal("190.60"), 50);
        Order updatedOrder= service.editSellOrder(updatedParam);
        assertEquals(expected, updatedOrder, "Expected and updatedOrder should be the same.");
        
        try{
            String[] wrongParam= {"SORD6", "190.60", "50"};
            service.editSellOrder(wrongParam);
            fail("Exception should have been thrown");
        } catch (OrderPersistenceException e){
            return;
        }
        String[] updatedParam2= {"SORD2", "", ""};
        Order expected2= new Order("SORD2", new BigDecimal("190.99"), 30);
        Order updatedOrder2= service.editSellOrder(updatedParam);
        assertEquals(expected2, updatedOrder2, "Expected and updatedOrder should be the same.");
    }
    
    @Test
    public void testGetBuyOrSell() throws OrderPersistenceException {
        assertTrue(service.getBuyOrSell("BORD1"), "A buy order should return true");
        assertFalse(service.getBuyOrSell("SORD2"), "A sell order should return false");
        try{
            service.getBuyOrSell("_@)#($()_@#");
            fail("Exception should have been thrown");
        } catch (OrderPersistenceException e){
            return;
        }
        try{
            service.getBuyOrSell("_@)#($D_@#");
            fail("Exception should have been thrown");
        } catch (OrderPersistenceException e){
            return;
        }
    }
    
    @Test
    public void isEmpty() throws OrderPersistenceException {
        assertFalse(service.isEmpty(), "Lists should not be empty");
    }
    /*
    @Override
    public void generateNRandomOrders(int N) throws OrderPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clearTables() throws OrderPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    */
    
}
