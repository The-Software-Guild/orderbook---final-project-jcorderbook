/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.service;

import com.sg.VendingMachine.dao.ItemDaoInterface;
import com.sg.VendingMachine.dao.VendingMachinePersistenceException;
import com.sg.VendingMachine.dto.Item;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author recyn
 */
public class ItemDaoStub implements ItemDaoInterface {
    public Item onlyItem;
    public Item item2;
    public Item item3;
    private BigDecimal cash;
    public ItemDaoStub(){
        onlyItem = new Item("Doritos", new BigDecimal("0.49"), (int) 10);
        item2 = new Item("Lays", new BigDecimal("2.49"), (int) 10);
        item3 = new Item("Fridos", new BigDecimal("0.49"), (int) 0);
        cash= new BigDecimal("0"); 
    }
    
    public ItemDaoStub(Item newItem){
        onlyItem= newItem;
        cash = new BigDecimal("0"); 
    }
    
    @Override
    public Item addItem(String name, Item item) throws VendingMachinePersistenceException {
          if (name.equals(onlyItem.getName())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        List<Item> allItems= new ArrayList();
        allItems.add(onlyItem);
        allItems.add(item2);
        allItems.add(item3);
        return allItems;
    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        if (onlyItem.getName().equals(name)){
            return onlyItem;
        } 
        if (item2.getName().equals(name)){
            return item2;
        } 
        if (item3.getName().equals(name)){
            return item3;
        } 
        else{
        return null;
        }
    }

    @Override
    public Item removeItem(String name) throws VendingMachinePersistenceException {
        if (onlyItem.getName().equals(name)){
            return onlyItem;
        } else{
        return null;
        }
    }

    @Override
    public int subtractItem(String name) throws VendingMachinePersistenceException {
        int newCount=onlyItem.getCount()-1;
        onlyItem.setCount(newCount);
        return newCount;
    }

    @Override
    public BigDecimal getCash() {
        return cash;
    }
    


    @Override
    public void setCash(BigDecimal cash2) throws VendingMachinePersistenceException {
        cash= cash2;
    }

    @Override
    public void restock() throws VendingMachinePersistenceException, IOException {
        onlyItem.setCount(10);
        item2.setCount(10);
    }
    
}
