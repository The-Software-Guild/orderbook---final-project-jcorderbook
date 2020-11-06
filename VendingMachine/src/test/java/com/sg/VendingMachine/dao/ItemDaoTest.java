/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.dao;

import com.sg.VendingMachine.dto.Item;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
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
public class ItemDaoTest {
    ItemDao dao= new ItemDao();
    public ItemDaoTest() {
    }
    
    @BeforeAll
    public void setUpClass() throws VendingMachinePersistenceException, IOException {

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
    
    //Tests if the subtract function doesn't update count when count is already 0 and also returns the correct count. Also tests the restock and removed functions by checking count. 
    @Test 
    public void testSubtractandRemove() throws VendingMachinePersistenceException, IOException{
        Item updatedMD= new Item("Mountain Dew2", new BigDecimal("1.00"), (int) 0);
        dao.addItem("Mountain Dew2", updatedMD);
        int newCount=dao.subtractItem("Mountain Dew2");
        int count=dao.getItem("Mountain Dew2").getCount();
        assertEquals(0, count, "Mountain Dew2 should now have 0 count and there should still be Mountain Dew in inventory");
        assertEquals(0, newCount, "SubtractItem should give newCount as 0");
        Item removedItem=dao.removeItem("Mountain Dew2");
        assertEquals(updatedMD, removedItem, "Removed Item and added item should be the same");
       
        
        dao.restock();
        newCount= dao.subtractItem("Doritos");
        count=dao.getItem("Doritos").getCount();
        assertEquals(9, count, "Doritos should now have 9 count and there should still be doritos in inventory");
        assertEquals(9, newCount, "SubtractItem should give newCount as 9");
        
       
        
    }
    
    //Test if the get function does anything to the inventory.
    @Test
    public void testGet() throws VendingMachinePersistenceException, IOException {
        dao.restock();
        S.print("TEST3");
        Item test= new Item("Doritos", new BigDecimal("1.25"), (int) 10);
        Item test2= new Item("Onion Rings", new BigDecimal("1.25"), (int) 10);
        Item test3= new Item("Spicey Doritos", new BigDecimal("1.25"), (int) 10);
        List<Item> itemList;
       
        itemList = dao.getAllItems();
        assertEquals(test, itemList.get(5),"The get function should not do anything to the list");
        assertEquals(test2, itemList.get(6),"The get function should not do anything to the list");
        assertEquals(test3, itemList.get(3),"The get function should not do anything to the list");
        
    }
    // Tests if the initial amount of cash is 0 and if setCash changes the cash to the correct amount. 
    @Test
    public void testCash() throws VendingMachinePersistenceException{
        BigDecimal cash= dao.getCash();
        assertEquals(new BigDecimal("0"), cash, "Cash should be 0 at first");
        dao.setCash(new BigDecimal("2.00"));
        assertEquals(new BigDecimal("2.00"), dao.getCash(), "Cash should now be 2.00");
        
    }
    
    //Test to see if the keys in the dao map are correct. 
     @Test
  public void testKeySet() throws VendingMachinePersistenceException{
      S.print("TEST0");
      dao.loadCollection();
      Set<String> keySet= dao.items.keySet();
      assertEquals(7, keySet.size(),"Size of keySet should be 7");
      assertTrue(keySet.contains("Doritos"), "There should be Doritos in the keySet");
      assertTrue(keySet.contains("Onion Rings"), "There should be Onion Rings in the keySet");
      assertTrue(keySet.contains("Spicey Doritos"), "There should be Spicey Doritos in the keySet");
      
  }
  
  //Test to see if the get function does anything to the keys, which it shouldn't.
  @Test
   public void testKeySetAfterGet() throws Exception{
       S.print("TEST1");
      Item test= new Item("Doritos", new BigDecimal("1.25"), (int) 10);
      Item result= dao.getItem("Doritos");
      Set<String> keySet= dao.items.keySet();
      assertEquals(7, keySet.size(),"Size of keySet should be 1");
      assertTrue(keySet.contains("Doritos"), "There should be Doritos in the keySet");
      assertTrue(keySet.contains("Onion Rings"), "There should be Onion Rings in the keySet");
      assertTrue(keySet.contains("Spicey Doritos"), "There should be Spicey Doritos in the keySet");
      
  }
   
   //Test if get function returns null for a nonexistent item and returns the correct item if existent.
   //Also tests that the get function doesn't change the size of the inventory. 
   @Test
   public void testGet2() throws VendingMachinePersistenceException, IOException{
       dao.restock();
       S.print("TEST2");
       Item test= new Item("Doritos", new BigDecimal("1.25"), (int) 10);

        Item result;

        result = dao.getItem("NOSUCHNAME");
        assertNull(result, "Supposed to be null");
        
        Item getItem;

        getItem = dao.getItem("Doritos");
        assertEquals(test, getItem, "Gotten item should be the same");

        
        
        
        //S.print("CHECK: Get gives the item that I expected given the same name");
        
        List<Item> itemList;

            itemList = dao.getAllItems();
            assertEquals(7, itemList.size(),"The get function should not do anything to the size of the list");

        
        
        //S.print("CHECK: getAllItems gives the expected number of items");
   }
   
    
}
