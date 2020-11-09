/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Order;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import s.S;

/**
 *
 * @author recyn
 */
public class OrderDaoImplTest {
    OrderDaoInterface dao= new OrderDaoImpl("testOrderNumbers.txt");
    String date = "01012021";
    
    int orderNumber=1;
    BigDecimal taxRate= new BigDecimal("25");
    BigDecimal area= new BigDecimal("249");
    BigDecimal CostPerSquareFoot= new BigDecimal("3.50");
    BigDecimal LaborCostPerSquareFoot= new BigDecimal("4.15");
    BigDecimal materialCost= new BigDecimal("871.50");
    BigDecimal laborCost= new BigDecimal("1033.35");
    BigDecimal tax= new BigDecimal("476.21");
    BigDecimal total= new BigDecimal("2381.06");
    Order test= new Order(orderNumber, "Ada LoveLace", "California", taxRate, "Tile", area, CostPerSquareFoot, LaborCostPerSquareFoot, materialCost, laborCost, tax, total);    
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
    public void testGetAdd() throws OrderPersistenceException, IOException {
        boolean deleted= Files.deleteIfExists(Paths.get("src/main/orders/Orders_"+date+".txt"));
        S.print(deleted+"");
        
        Order result= dao.addOrder(date, test, true);
        
        assertNull(result, "Supposed to be null");
        //Scanner scanner;
        //scanner=new Scanner(new BufferedReader(new FileReader("src/main/orders/Orders_"+dateFile)));
        Order getOrder= dao.getOrder(date, orderNumber);
        
        assertEquals(test, getOrder, "Gotten monster should be the same");
        Order result2= dao.addOrder(date, test, true);
        assertEquals(test, result2, "if you add something that's already there it should return the same value");
        List<Order> orderList= dao.getAllOrders(date);
        assertEquals(1, orderList.size(),"After trying to add a duplicate, the list size should still be 1");
    }
    
    
    @Test
    public void testGet() throws Exception{
        boolean deleted= Files.deleteIfExists(Paths.get("src/main/orders/Orders_"+date+".txt"));
        Order result= dao.addOrder(date, test, true);
        int orderNumber= 1;
        Order getOrder= dao.getOrder(date, orderNumber);
        
        assertEquals(test, getOrder, "Gotten monster should be the same");
        List<Order> orderList= dao.getAllOrders(date);
        assertEquals(1, orderList.size(),"The get function should not do anything to the size of the list");
    }
    
    @Test
    public void testGet2() throws Exception {
        boolean deleted= Files.deleteIfExists(Paths.get("src/main/orders/Orders_"+date+".txt"));
        Order result= dao.addOrder(date, test, true);
        Order getOrder= dao.getOrder(date, orderNumber);
        
        assertEquals(test, getOrder, "Gotten monster should be the same");
        List<Order> orderList= dao.getAllOrders(date);
        /*for (Order m:orderList){
            System.out.print(m);
        }*/
        assertEquals(test, orderList.get(0),"The get function should not do anything to the contents of the list");
    } 
    
    @Test
    public void testNoAddAgain() throws Exception{
        
        boolean deleted= Files.deleteIfExists(Paths.get("src/main/orders/Orders_"+date+".txt"));
        Order result= dao.addOrder(date, test, true);
        Order test2= new Order(orderNumber, "Albert", "California", taxRate, "Tile", area, CostPerSquareFoot, LaborCostPerSquareFoot, materialCost, laborCost, tax, total);
        Order result2= dao.addOrder(date, test2, true);
        Map<Integer, Order> orders=dao.getMap();
        List<Order> orderList= dao.getAllOrders(date);
        System.out.print("SIZE OF LIST IS " +orderList.size());
        //System.out.print(dao.getOrder("Gardevoir EX").getName());
        //System.out.print(dao.getOrder("Rayquaza EX").getName());
        assertEquals(1, orderList.size(),"Adding again shouldn't increase the size of the list");
        assertEquals(test2, orderList.get(0), "Adding again should replace the value if the keys are the same");
    }
 
    
    @Test
    public void testGetAllOrders() throws Exception {
        boolean deleted= Files.deleteIfExists(Paths.get("src/main/orders/Orders_"+date+".txt"));
        Order result= dao.addOrder(date, test, true);
        List<Order> orderList= dao.getAllOrders(date);
        assertEquals(1, orderList.size(), "Size should be 1");
        assertEquals(test, orderList.get(0), "Only entry should be test");
        Order test2= new Order(2, "Albert", "California", taxRate, "Tile", area, CostPerSquareFoot, LaborCostPerSquareFoot, materialCost, laborCost, tax, total);
        Order result2= dao.addOrder(date, test2, true);
        orderList= dao.getAllOrders(date);   
        assertEquals(2, orderList.size(), "Size at first was 1, as expected, now size should be 2");     
        assertTrue(orderList.contains(test), "Entry should be test");
        assertTrue(orderList.contains(test2), "Entry should be test2");

      
        }
    
    @Test
  public void testRemoveOrder() throws IOException, OrderPersistenceException{
      boolean deleted= Files.deleteIfExists(Paths.get("src/main/orders/Orders_"+date+".txt"));
      Order result= dao.addOrder(date, test, true);
      Order removed= dao.removeOrder(date, 1);
      assertEquals(test, removed, "Entry should be test");
      assertNull(dao.getOrder(date, 1), "The removed entry should no longer be there");
      
      Order removed2= dao.removeOrder(date,1);
      assertNull(removed2, "You can't remove something that theoretically we already removed");
  }
  
  @Test
  public void testKeySet() throws Exception {
      boolean deleted= Files.deleteIfExists(Paths.get("src/main/orders/Orders_"+date+".txt"));
      Order result= dao.addOrder(date, test, true);
      Set<Integer> keySet= dao.getMap().keySet();
      int id=1;
      /*for (int i:keySet){
          System.out.print(i+"\nHELLOOOOOOOOOOOOOOOOOOOOOOOOOO\n");
      }*/
      assertEquals(1, keySet.size(),"Size of keySet should be 1");
      assertTrue(keySet.contains(id), "The only key should be id 1");
      
  }
    @Test
   public void testKeySetAfterGet() throws Exception {
      boolean deleted= Files.deleteIfExists(Paths.get("src/main/orders/Orders_"+date+".txt"));
      Order result= dao.addOrder(date, test, true);
      dao.getOrder(date, 1);
      Set<Integer> keySet= dao.getMap().keySet();
      int id=1;

      assertEquals(1, keySet.size(),"Size of keySet should be 1 after get function");
      assertTrue(keySet.contains(id), "The only key should be id 1 after get function");
      
  }
   
   @Test
   public void testGetOrderNumber() throws Exception{
   }

}
