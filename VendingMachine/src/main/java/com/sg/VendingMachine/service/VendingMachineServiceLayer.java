/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.service;


import com.sg.VendingMachine.dao.VendingMachinePersistenceException;
import com.sg.VendingMachine.dto.Item;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author recyn
 */
public interface VendingMachineServiceLayer {
    
    
    void createItem(Item item) throws
            VendingMachinePersistenceException;
    
    List<Item> getAllItems() throws
            VendingMachinePersistenceException;
    
    BigDecimal BuyItem(String name) throws
            AllOutException, VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException;
    void setCash(BigDecimal money) throws VendingMachinePersistenceException;
    
    void restock() throws Exception;
    
    List<Item> getAvailableItems() throws VendingMachinePersistenceException;
    
    BigDecimal refund() throws VendingMachinePersistenceException;
    
    String[] changeInCoins(BigDecimal change) throws Exception;
}
