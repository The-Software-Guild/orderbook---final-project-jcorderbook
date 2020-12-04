/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DAO;

import MainFolder.DTO.Order;
import MainFolder.DTO.Trade;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
public class OrderDaoImplTest {
    OrderDaoInterface dao = new OrderDaoImpl();
    public OrderDaoImplTest() {
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
    public void addGetOrderTest() throws Exception{
        dao.clearTables(true, true, true);
        //test BuyOrder first
        Order buyTest1 = new Order("BORD1", new BigDecimal("190.99"), 25);
        Order buyResult= dao.addBuyOrder(buyTest1);
        
        assertNull(buyResult, "Supposed to be null");
        String buyOrderID= "BORD1";
        Order getBuyOrder1= dao.getBuyOrder(buyOrderID);
        
        assertEquals(buyTest1, getBuyOrder1, "Gotten Order should be the same");
        List<Order> buyList= dao.getAllBuyOrders();
        assertEquals(1, buyList.size(),"Buy List size should be 1 at this point.");
        //test SellOrder 
        Order sellTest1 = new Order("SORD2", new BigDecimal("190.99"), 30);
        Order sellResult= dao.addSellOrder(sellTest1);
        
        assertNull(sellResult, "Supposed to be null");
        String sellOrderID1= "SORD2";
        Order getSellOrder1= dao.getSellOrder(sellOrderID1);
        
        assertEquals(sellTest1, getSellOrder1, "Gotten Order should be the same");
        List<Order> sellList= dao.getAllSellOrders();
        assertEquals(1, sellList.size(),"Sell List size should be 1 at this point.");
        
        LocalDateTime time= LocalDateTime.now();
        LocalDateTime truncated= time.truncatedTo(ChronoUnit.SECONDS);
        Trade tradeTest1 = new Trade("TRADE1", new BigDecimal("190.95"), 25, truncated);
        Trade Result= dao.addTrade(tradeTest1);
        
        assertNull(Result, "Supposed to be null");
        String TradeID1= "TRADE1";
        Trade getTrade1= dao.getTrade(TradeID1);
        
        assertEquals(tradeTest1, getTrade1, "Gotten Trade should be the same");
        List<Trade> tradeList= dao.getAllTrades();
        assertEquals(1, tradeList.size()," List size should be 1 at this point.");
        
    }
    
    
    @Test
    public void GetTest2()throws Exception{
        dao.clearTables(true, true, true);
        //test BuyOrder first
        Order buyTest1 = new Order("BORD1", new BigDecimal("190.99"), 25);
        Order buyResult= dao.addBuyOrder(buyTest1);
        
        assertNull(buyResult, "Supposed to be null");
        String buyOrderID= "BORD1";
        Order getBuyOrder1= dao.getBuyOrder(buyOrderID);
        
        assertEquals(buyTest1, getBuyOrder1, "Gotten Order should be the same");
        List<Order> buyList= dao.getAllBuyOrders();
        
        assertEquals(buyTest1, buyList.get(0),"The get function should not do anything to the list");
       
        //test SellOrder
        Order sellTest1 = new Order("SORD2", new BigDecimal("190.99"), 25);
        Order sellResult= dao.addSellOrder(sellTest1);
        
        assertNull(sellResult, "Supposed to be null");
        String sellOrderID= "SORD2";
        Order getsellOrder1= dao.getSellOrder(sellOrderID);
        
        assertEquals(sellTest1, getsellOrder1, "Gotten Order should be the same");
        List<Order> sellList= dao.getAllSellOrders();
        
        assertEquals(sellTest1, sellList.get(0),"The get function should not do anything to the list");
        
        LocalDateTime time= LocalDateTime.now();
        LocalDateTime truncated= time.truncatedTo(ChronoUnit.SECONDS);
        Trade tradeTest1 = new Trade("TRADE1", new BigDecimal("190.95"), 25, truncated);
        Trade tradeResult= dao.addTrade(tradeTest1);
        
        assertNull(tradeResult, "Supposed to be null");
        String TradeID= "TRADE1";
        Trade getTrade1= dao.getTrade(TradeID);
        
        assertEquals(tradeTest1, getTrade1, "Gotten Trade should be the same");
        List<Trade> tradeList= dao.getAllTrades();
        
        assertEquals(tradeTest1, tradeList.get(0),"The get function should not do anything to the list");
    };
    
    
 

      @Test  
      public void getAllOrdersTest()throws Exception {
      dao.clearTables(true, true, true);
        //test BuyOrder first
      Order buyTest1 = new Order("BORD1", new BigDecimal("190.99"), 25);
      Order buyResult= dao.addBuyOrder(buyTest1);
      
      List<Order> buyList= dao.getAllBuyOrders();
      
      assertEquals(1, buyList.size(), "Size should be 1");
      assertEquals(buyTest1, buyList.get(0), "Only entry should be test");
      
      Order buyTest2 = new Order("BORD2", new BigDecimal("190.96"), 40);
      Order buyResult2= dao.addBuyOrder(buyTest2);
      buyList= dao.getAllBuyOrders();
      assertEquals(2, buyList.size(), "Size at first was 1, as expected, now size should be 2");
      assertTrue(buyList.contains(buyTest1), "Entry should be test");
      assertTrue(buyList.contains(buyTest2), "Entry should be test2");
      
      //sell test
      Order sellTest1 = new Order("SORD3", new BigDecimal("190.99"), 25);
      Order sellResult= dao.addSellOrder(sellTest1);
      
      List<Order> sellList= dao.getAllSellOrders();
      
      assertEquals(1, sellList.size(), "Size should be 1");
      assertEquals(sellTest1, sellList.get(0), "Only entry should be test");
      
      Order sellTest2 = new Order("SORD4", new BigDecimal("190.96"), 40);
      Order sellResult2= dao.addSellOrder(sellTest2);
      sellList= dao.getAllSellOrders();
      assertEquals(2, sellList.size(), "Size at first was 1, as expected, now size should be 2");
      assertTrue(sellList.contains(sellTest1), "Entry should be test");
      assertTrue(sellList.contains(sellTest2), "Entry should be test2");
      
      //trade test
      LocalDateTime time= LocalDateTime.now();
      LocalDateTime truncated= time.truncatedTo(ChronoUnit.SECONDS);
      Trade tradeTest1 = new Trade("TRADE1", new BigDecimal("190.95"), 25, truncated);
      Trade tradeResult= dao.addTrade(tradeTest1);
      
      List<Trade> tradeList= dao.getAllTrades();
      assertEquals(1, tradeList.size()," List size should be 1 at this point.");
      assertEquals(tradeTest1, tradeList.get(0),"Only entry should be tradeTest1");
      
      LocalDateTime time2= LocalDateTime.now();
      LocalDateTime truncated2= time.truncatedTo(ChronoUnit.SECONDS);
      Trade tradeTest2 = new Trade("TRADE2", new BigDecimal("190.95"), 25, truncated);
      Trade tradeResult2= dao.addTrade(tradeTest2);
      tradeList= dao.getAllTrades();
      
      assertEquals(2, tradeList.size(), "Size at first was 1, as expected, now size should be 2");
      assertTrue(tradeList.contains(tradeTest1), "Entry should be test");
      assertTrue(tradeList.contains(tradeTest2), "Entry should be test2");
      
  }
  
  @Test
  public void updateOrderWithGet()throws Exception {
      dao.clearTables(true, true, true);
      Order buyTest1 = new Order("BORD1", new BigDecimal("190.99"), 25);
      Order buyResult= dao.addBuyOrder(buyTest1);
      
      Order buyTest2 = new Order("BORD1", new BigDecimal("190.96"), 40);
      //Order buyResult2= dao.addBuyOrder(buyTest2);
      
      Order buyTest3 = new Order("BORD2", new BigDecimal("190.96"), 40);
      
      dao.editBuyOrder(buyTest2);
      
      assertEquals(buyTest2, dao.getBuyOrder("BORD1"), "Entry should be buyTest2");
      
      dao.editBuyOrder(buyTest3);
      
      assertNull(dao.getBuyOrder("BORD2"), "Should be no such thing");
      
      //Sell order
      
      Order sellTest1 = new Order("SORD2", new BigDecimal("190.99"), 25);
      Order sellResult= dao.addSellOrder(sellTest1);
      
      Order sellTest2 = new Order("SORD2", new BigDecimal("190.96"), 40);
      //Order sellResult2= dao.addsellOrder(sellTest2);
      
      Order sellTest3 = new Order("SORD3", new BigDecimal("190.96"), 40);
      
      dao.editSellOrder(sellTest2);
      
      assertEquals(sellTest2, dao.getSellOrder("SORD2"), "Entry should be sellTest2");
      
      dao.editSellOrder(sellTest3);
      
      assertNull(dao.getSellOrder("SORD3"), "Should be no such thing");
  }
  
  @Test
  public void updateOrderWithList() throws Exception{
      dao.clearTables(true, true, true);
      Order buyTest1 = new Order("BORD1", new BigDecimal("190.99"), 25);
      Order buyResult= dao.addBuyOrder(buyTest1);
      
      Order buyTest2 = new Order("BORD2", new BigDecimal("190.96"), 40);
      dao.addBuyOrder(buyTest2);
      
      Order buyTest3 = new Order("BORD1", new BigDecimal("190.96"), 40);
      
      dao.editBuyOrder(buyTest3);
      List<Order> buyList= dao.getAllBuyOrders();
      
      assertEquals(2, buyList.size(), "OrderList size should be 2");
      
      assertTrue(buyList.contains(buyTest3), "Entry should be test3");
     
      Order SellTest1 = new Order("SORD3", new BigDecimal("190.99"), 25);
      Order SellResult= dao.addSellOrder(SellTest1);
      
      Order SellTest2 = new Order("SORD4", new BigDecimal("190.96"), 40);
      dao.addSellOrder(SellTest2);
      
      Order SellTest3 = new Order("SORD3", new BigDecimal("190.96"), 40);
      
      dao.editSellOrder(SellTest3);
      List<Order> SellList= dao.getAllSellOrders();
      
      assertEquals(2, SellList.size(), "OrderList size should be 2");
      
      assertTrue(SellList.contains(SellTest3), "Entry should be test3");
      
  }
  
  
  @Test
  public void removeOrder() throws Exception{
      dao.clearTables(true, true, true);
      Order buyTest1 = new Order("BORD1", new BigDecimal("190.99"), 25);
      Order buyResult= dao.addBuyOrder(buyTest1);
      Order removed= dao.removeBuyOrder("BORD1");
      assertEquals(buyTest1, removed, "Entry should be buyTest1");
      assertNull(dao.getBuyOrder("BORD1"), "The removed entry should no longer be there");
      
      Order removed2= dao.removeBuyOrder("BORD1");
      assertNull(removed2, "You can't remove something that theoretically we already removed");
      
      Order SellTest1 = new Order("SORD2", new BigDecimal("190.99"), 25);
      Order SellResult= dao.addSellOrder(SellTest1);
      removed= dao.removeSellOrder("SORD2");
      assertEquals(SellTest1, removed, "Entry should be SellTest1");
      assertNull(dao.getSellOrder("SORD2"), "The removed entry should no longer be there");
      
      removed2= dao.removeSellOrder("SORD2");
      assertNull(removed2, "You can't remove something that theoretically we already removed");
      
  }
  /*
  @Test
  public void testKeySet(){
      Order test= new Order("Sullivan", YETI, 0, "Cheerios");
      dao.addOrder(1, test);
      Set<Integer> keySet= dao.monsters.keySet();
      int id=1;
      for (int i:keySet){
          System.out.print(i+"\nHELLOOOOOOOOOOOOOOOOOOOOOOOOOO\n");
      }
      assertEquals(1, keySet.size(),"Size of keySet should be 1");
      assertTrue(keySet.contains(id), "The only key should be id 1");
      
  }
   public void testKeySetAfterGet(){
      Order test= new Order("Sullivan", YETI, 0, "Cheerios");
      dao.addOrder(1, test);
      dao.getOrder(1);
      Set<Integer> keySet= dao.monsters.keySet();
      int id=1;
      for (int i:keySet){
          System.out.print(i+"\nHELLOOOOOOOOOOOOOOOOOOOOOOOOOO\n");
      }
      assertEquals(1, keySet.size(),"Size of keySet should be 1");
      assertTrue(keySet.contains(id), "The only key should be id 1");
      
  }
    */
    /*
    Test
    */
}
