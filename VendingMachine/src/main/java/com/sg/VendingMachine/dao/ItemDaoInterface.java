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
import java.util.Map;

/**
 *
 * @author recyn
 */
public interface ItemDaoInterface {

    Item addItem(String name, Item item) throws VendingMachinePersistenceException;
    
    List<Item> getAllItems() throws VendingMachinePersistenceException;
    
    Item getItem (String name) throws VendingMachinePersistenceException;
    
    Item removeItem(String name) throws VendingMachinePersistenceException;
    
    int subtractItem(String name) throws VendingMachinePersistenceException;
   
    BigDecimal getCash();

    void setCash(BigDecimal cash) throws VendingMachinePersistenceException;
    
    void restock() throws VendingMachinePersistenceException, IOException;  


}
