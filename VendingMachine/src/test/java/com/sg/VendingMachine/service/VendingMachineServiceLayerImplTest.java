/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.service;

import com.sg.VendingMachine.dao.ItemDao;
import com.sg.VendingMachine.dao.ItemDaoInterface;
import com.sg.VendingMachine.dao.VendingMachineAuditDao;
import com.sg.VendingMachine.dao.VendingMachinePersistenceException;
import com.sg.VendingMachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import s.S;

/**
 *
 * @author recyn
 */
public class VendingMachineServiceLayerImplTest {
    /*ItemDaoInterface daoStub= new ItemDaoStub();
    VendingMachineAuditDao auditDao= new VendingMachineAuditFileStub();
*/
    VendingMachineServiceLayer service;

    
    public VendingMachineServiceLayerImplTest() {
        ApplicationContext ctx= new ClassPathXmlApplicationContext("ApplicationContext.xml");
        service= ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
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


    //Tests whether or not GetAllItems returns a list of the correct size and correct contents. 
    @Test
    public void testGetAllItems() throws Exception {
        Item newItem = new Item("Doritos", new BigDecimal("0.49"), (int) 10);
        Item newItem2 = new Item("Lays", new BigDecimal("2.49"), (int) 10);
        Item newItem3 = new Item("Fridos", new BigDecimal("0.49"), (int) 0);
        
        assertEquals( 3, service.getAllItems().size(), 
                                   "Should only have three items.");
        service.getAllItems().stream()
                .forEach((i) -> S.print(i.getName()));
        assertTrue( service.getAllItems().contains(newItem),
                              "It should have Doritos.");
       
        assertTrue( service.getAllItems().contains(newItem2),
                              "It should have Lays.");
        
        assertTrue( service.getAllItems().contains(newItem3),
                              "It should have Fridos.");
    
}
    //Checks whether or not buying the item decreases the count of the item by 1. Then it checks to see if the cash has been set back to 0. 
    @Test 
       public void testBuyCorrectItemAndNoFunds() throws Exception {
       service.setCash(new BigDecimal("2.00"));
       BigDecimal change= service.BuyItem("Doritos");
       assertEquals("1.51", change.toString(), "Change should be 1.51");
       Item newItem = new Item("Doritos", new BigDecimal("0.49"), (int) 9);
       List<Item> list = service.getAllItems();
       Item updatedItem= list.get(0);
       S.print(updatedItem.getName());
       assertTrue (list.contains(newItem), "New count should be 9");
       try {
               service.BuyItem("Lays");
               fail("There should've been an exception");
           } catch (InsufficientFundsException e){
               return;
           } catch (Exception e){
               fail ("Should've been a InsufficientFundsException");
           }
       
    }
       
       //Checks to see if the Buy function returns the correct exception when the item is not listed in the inventory. 
       @Test
       public void testBuyNullItem() throws Exception {
           service.setCash(new BigDecimal("2.00"));
           try {
               service.BuyItem("alkdjfla");
               fail("There should've been an exception");
           } catch (NoItemInventoryException e){
               return;
           } catch (Exception e){
               fail ("Should've been a NoItemInventoryException");
           }
       }
       //Checks to see if the Buy function returns the correct exception when the user doesn't have enough money. 
       @Test
       public void testBuyInsufficient() throws Exception {
           service.setCash(new BigDecimal("0.20"));
           try {
               service.BuyItem("Lays");
               fail("There should've been an exception");
           } catch (AllOutException | VendingMachinePersistenceException | NoItemInventoryException e){
               fail ("Should've been a InsufficientFundsException");
           }    
            catch (InsufficientFundsException e){
               return;
               }
       }
       ////Checks to see if the Buy function returns the correct exception when the item's coiunt is 0. 
        @Test
       public void testBuyAllOut() throws Exception {
           service.setCash(new BigDecimal("2.00"));
           try {
               service.BuyItem("Fridos");
               fail("There should've been an exception");
           } catch (InsufficientFundsException | VendingMachinePersistenceException | NoItemInventoryException e){
               fail ("Should've been a AllOutException");
           }    
            catch (AllOutException e){
               return;
               }
       }
       //Checks to see if the restock functions sets all item counts back to 10 by first buying an item. 
      @Test
      public void testRestock() throws Exception{
          service.setCash(BigDecimal.ONE);
          service.BuyItem("Doritos");
          service.restock();
          Item newItem = new Item("Doritos", new BigDecimal("0.49"), (int) 10);
            List<Item> list = service.getAllItems();
         Item updatedItem= list.get(0);
            S.print(updatedItem.getName());
         assertTrue (list.contains(newItem), "New count should be 10");
          
      }
      
      //Test to see if the GetAllAvailable function only returns items with count > 0
      @Test
      public void testGetAllAvailable() throws VendingMachinePersistenceException, Exception{
          S.print("HELLOOOOO");
          service.restock();
          List<Item> list= service.getAvailableItems();
          Item newItem = new Item("Doritos", new BigDecimal("0.49"), (int) 10);
          Item newItem2 = new Item("Lays", new BigDecimal("2.49"), (int) 10);
          Item newItem3 = new Item("Fridos", new BigDecimal("0.49"), (int) 0);
          Item updatedItem= list.get(0);
          S.print("TESTALLAVAILABLE "+updatedItem.getCount());
          Item updatedItem2= list.get(1);
          S.print("TESTALLAVAILABLE "+updatedItem2.getName());
          assertTrue( list.contains(newItem),
                             "It should have Doritos.");
       
          assertTrue( service.getAllItems().contains(newItem2),
                              "It should have Lays.");
            
          assertFalse( list.contains(newItem3),
                             "It should not have Fridos.");
      }
      
      //Tests if the changeInCoins function returns expected values for the number of each coin. 
      @Test
      public void testChangeInCoins() throws Exception{
          S.print("HELLOOOOO");
          String[] coins= service.changeInCoins(new BigDecimal("2.47"));
          assertEquals("9", coins[0], "Should be 9 quarters");
          assertEquals("2", coins[1], "Should be 2 dimes");
          assertEquals("0", coins[2], "Should be 0 nickels");
          assertEquals("2", coins[3], "Should be 2 pennies");
      }
    
}
