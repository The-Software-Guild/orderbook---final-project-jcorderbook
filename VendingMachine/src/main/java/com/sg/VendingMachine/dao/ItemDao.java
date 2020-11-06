/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.dao;


import com.sg.VendingMachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import s.S;

/**
 *
 * @author recyn
 */
public class ItemDao implements ItemDaoInterface {

    public final String VENDING_FILE;
    public static final String DELIMITER = "::";
    public Map<String, Item> items= new HashMap<>();
    private BigDecimal cash= new BigDecimal("0");
    
    public ItemDao(){
        VENDING_FILE="vending.txt";
    }
    public ItemDao (String textFile){
        VENDING_FILE=textFile;
    }
    
    //for testing purposes only
    @Override
    public Item addItem(String name, Item item) throws VendingMachinePersistenceException {
       loadCollection();
       Item newItem= items.put(name, item);
        try {
            writeCollection();
        } catch (IOException ex) {
            Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
       return newItem;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadCollection();
        return new ArrayList<Item>(items.values());
    }
    //gives null if no such name is in the keyset
    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        loadCollection();
        return items.get(name);
    }
    // for testing purposes only 
    @Override
    public Item removeItem(String name) throws VendingMachinePersistenceException {
        loadCollection();
        Item removedItem=items.remove(name);
        try {
            writeCollection();
        } catch (IOException ex) {
            Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return removedItem;
    }
    // decreases the count of the input item unless the count is 0. 
    @Override
    public int subtractItem(String name) throws VendingMachinePersistenceException{
        loadCollection();
        Item boughtItem= items.get(name);
        int newCount= boughtItem.getCount()-1;
        if (newCount>=0){
            boughtItem.setCount(newCount);
            items.put(name, boughtItem);
        } else {
            newCount=0;
        }
        try {
            writeCollection();
        } catch (IOException ex) {
            Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newCount;
    }
    //Returns the cash amount for this Dao. 
    @Override
    public BigDecimal getCash(){
        return cash;
    }
    
    @Override
    public void setCash(BigDecimal cash) throws VendingMachinePersistenceException {
        this.cash=cash;
    }
   
  
    
    private String marshallItem(Item aItem){
        
        String ItemAsText=aItem.getName()+DELIMITER;
        ItemAsText += aItem.getCost().toString()+DELIMITER;
        
        ItemAsText += Integer.toString(aItem.getCount())+DELIMITER;
       
        
        return ItemAsText;
    }
    
    private Item unmarshallItem(String ItemAsText){
        String[] ItemTokens= ItemAsText.split(DELIMITER);
        String name=ItemTokens[0];
        Item ItemFromFile= new Item(name);
        ItemFromFile.setCost(new BigDecimal(ItemTokens[1]));
        ItemFromFile.setCount(S.integize(ItemTokens[2]));
        return ItemFromFile;
    }
    //writes to the text file using information from the map
    private void writeCollection() throws VendingMachinePersistenceException, IOException{
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(VENDING_FILE));
        } catch (Exception e){
            throw new VendingMachinePersistenceException("Could not load collection from memory.", e);
        }
        
        Set<String> keys= items.keySet();
        for (String k:keys){
            String ItemAsText= marshallItem(items.get(k));
            out.println(ItemAsText);
        }
        
        out.flush();
        out.close();
        
    }
    
    //creates a new map using information from the text file
    public void loadCollection() throws VendingMachinePersistenceException {
        Scanner scanner;
        
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(VENDING_FILE)));
        } catch (FileNotFoundException e){
            throw new VendingMachinePersistenceException("Could not load collection data into memory", e);
        }
        String currentLine;
        Item currentItem;
        while (scanner.hasNextLine()){
            currentLine=scanner.nextLine();
            currentItem=unmarshallItem(currentLine);
            items.put(currentItem.getName(), currentItem);
        }
        scanner.close();
    }
    
    
    public void restock() throws VendingMachinePersistenceException, IOException{
        loadCollection();
        List<Item> allItems= new ArrayList(items.values());
        allItems.stream()
                .forEach((item)-> item.setCount(10));
        writeCollection();
    }
    

}
