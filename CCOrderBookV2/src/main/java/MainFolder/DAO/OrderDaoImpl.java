/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DAO;


import MainFolder.DTO.Order;
import MainFolder.DTO.Trade;
import MainFolder.Service.TradePersistenceException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import s.S;

/**
 *
 * @author recyn
 */
@Component
public class OrderDaoImpl implements OrderDaoInterface {
    public final String BUYCOLLECTION_FILE;
    public final String SELLCOLLECTION_FILE;
    public final String TRADELOG_FILE;
    public static final String DELIMITER = "::";
    private String ORDERNUMBERS_FILE;
    private String TRADENUMBERS_FILE;
    
    @Autowired
    public OrderDaoImpl(){
        BUYCOLLECTION_FILE="buyCollection.txt";
        SELLCOLLECTION_FILE= "sellCollection.txt";
        ORDERNUMBERS_FILE= "orderNumbers.txt";
        TRADENUMBERS_FILE= "tradeNumbers.txt";
        TRADELOG_FILE="tradeLog.txt";
    }
    public OrderDaoImpl(String textFile1, String textFile2, String textFile3){
        BUYCOLLECTION_FILE=textFile1;
        SELLCOLLECTION_FILE=textFile2;
        TRADELOG_FILE=textFile3;
        ORDERNUMBERS_FILE= "orderNumbers.txt";
        TRADENUMBERS_FILE= "tradeNumbers.txt";
    }
    
    public OrderDaoImpl(String textFile1, String textFile2, String textFile3, String textFile4, String textFile5){
        BUYCOLLECTION_FILE=textFile1;
        SELLCOLLECTION_FILE=textFile2;
        ORDERNUMBERS_FILE= textFile3;
        TRADENUMBERS_FILE=textFile4;
        TRADELOG_FILE=textFile5;
    }
    public Map<String, Order> BuyOrders = new HashMap<>();
    public Map<String, Order> SellOrders= new HashMap<>();
    public Map<String, Trade> TradeLog= new HashMap<>();
    
    @Override
    public Order addBuyOrder(Order order) throws OrderPersistenceException {
        loadCollection();
        order.setOrderID("BORD"+getOrderNumber());
        Order newOrder= BuyOrders.put(order.getOrderID(), order);

        writeCollection();

        return newOrder;
    }
    
    @Override
    public Order addSellOrder(Order order) throws OrderPersistenceException {
        loadCollection();
        order.setOrderID("SORD"+getOrderNumber());
        Order newOrder= SellOrders.put(order.getOrderID(), order);

        writeCollection();

        return newOrder;
    }

    @Override
    public Order getBuyOrder(String orderID) throws OrderPersistenceException {
        loadCollection();
       return BuyOrders.get(orderID);
    }
    
    @Override
    public Order getSellOrder(String orderID) throws OrderPersistenceException {
        loadCollection();
       return SellOrders.get(orderID);
    }
    
    @Override
    public Trade getTrade(String tradeID) throws TradePersistenceException {
        try {
            loadCollection();
        } catch (OrderPersistenceException ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
       return TradeLog.get(tradeID);
    }

    @Override
    public List<Order> getAllBuyOrders()  throws OrderPersistenceException{
        loadCollection();
        return new ArrayList<>(BuyOrders.values());
    }
    @Override
    public List<Trade> getAllTrades() throws TradePersistenceException{
        try {
            loadCollection();
        } catch (OrderPersistenceException ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>(TradeLog.values());
    }
    @Override
    public List<Order> getAllSellOrders()  throws OrderPersistenceException{
        loadCollection();
        return new ArrayList<>(SellOrders.values());
    }
    
    @Override
    public Order editBuyOrder(Order updatedOrder) throws OrderPersistenceException {
        loadCollection();
        Order output= BuyOrders.replace(updatedOrder.getOrderID(), updatedOrder);
        writeCollection();
        return output;
    }
    
    @Override
    public Order editSellOrder(Order updatedOrder) throws OrderPersistenceException {
        loadCollection();
        Order output= SellOrders.replace(updatedOrder.getOrderID(), updatedOrder);
        writeCollection();
        return output;
    }

    @Override
    public Order removeBuyOrder(String orderID)  throws OrderPersistenceException{
        loadCollection();
        Order removed= BuyOrders.remove(orderID);


        writeCollection();


        return removed;
    }
    
    @Override
    public Order removeSellOrder(String orderID)  throws OrderPersistenceException{
        loadCollection();
        Order removed= SellOrders.remove(orderID);

        writeCollection();


   
        return removed;
    }
    private Order unmarshallOrder(String OrderAsText){
        String[] OrderTokens= OrderAsText.split(DELIMITER);
        String orderID=OrderTokens[0];
        Order OrderFromFile= new Order(orderID, new BigDecimal(OrderTokens[1]), S.integize(OrderTokens[2]));

        return OrderFromFile;
    }
    
    private Trade unmarshallTrade(String TradeAsText){
        String[] TradeTokens= TradeAsText.split(DELIMITER);
        String tradeID=TradeTokens[0];
        LocalDateTime now= LocalDateTime.parse(TradeTokens[3], DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        Trade TradeFromFile= new Trade(tradeID, new BigDecimal(TradeTokens[1]), S.integize(TradeTokens[2]), now);

        return TradeFromFile;
    }
    
    private String marshallOrder(Order aOrder){
        
        String OrderAsText=aOrder.getOrderID()+DELIMITER;
        
        OrderAsText += aOrder.getPrice().toString()+DELIMITER;
        
        OrderAsText += Integer.toString(aOrder.getQuantity());
        
        
        return OrderAsText;
    }
    
    private String marshallTrade(Trade aTrade){
        
        String TradeAsText=aTrade.getTradeID()+DELIMITER;
        
        TradeAsText += aTrade.getExecutedPrice().toString()+DELIMITER;
        
        TradeAsText += Integer.toString(aTrade.getQuantity())+DELIMITER;
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        
        TradeAsText += dtf.format(aTrade.getTime());
        
        return TradeAsText;
    }
    //writes to the text file using information from the map
    private void writeCollection() throws OrderPersistenceException{
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(BUYCOLLECTION_FILE));
        } catch (Exception e){
            throw new OrderPersistenceException("Could not load collection from memory.", e);
        }
        
        Set<String> keys= BuyOrders.keySet();
        for (String k:keys){
            String OrderAsText= marshallOrder(BuyOrders.get(k));
            out.println(OrderAsText);
        }
        
        out.flush();
        out.close();
        try {
            out = new PrintWriter(new FileWriter(SELLCOLLECTION_FILE));
        } catch (Exception e){
            throw new OrderPersistenceException("Could not load collection from memory.", e);
        }
        
        Set<String> keys2= SellOrders.keySet();
        for (String k:keys2){
            String OrderAsText= marshallOrder(SellOrders.get(k));
            out.println(OrderAsText);
        }
        
        out.flush();
        out.close();
        
        try {
            out = new PrintWriter(new FileWriter(TRADELOG_FILE));
        } catch (Exception e){
            throw new OrderPersistenceException("Could not load collection from memory.", e);
        }
        
        Set<String> keys3= TradeLog.keySet();
        for (String k:keys3){
            String TradeAsText= marshallTrade(TradeLog.get(k));
            out.println(TradeAsText);
        }
        
        out.flush();
        out.close();
    }
    
    //creates a new map using information from the text file
    private void loadCollection() throws OrderPersistenceException {
        Scanner scanner;
        
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(BUYCOLLECTION_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load collection data into memory", e);
        }
        String currentLine;
        Order currentOrder;
        while (scanner.hasNextLine()){
            currentLine=scanner.nextLine();
            currentOrder=unmarshallOrder(currentLine);
            BuyOrders.put(currentOrder.getOrderID(), currentOrder);
        }
        scanner.close();
       
        
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(SELLCOLLECTION_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load collection data into memory", e);
        }
        String currentLine2;
        Order currentOrder2;
        while (scanner.hasNextLine()){
            currentLine2=scanner.nextLine();
            currentOrder2=unmarshallOrder(currentLine2);
            SellOrders.put(currentOrder2.getOrderID(), currentOrder2);
        }
        scanner.close();
        
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(TRADELOG_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load collection data into memory", e);
        }
        String currentLine3;
        Trade currentTrade;
        while (scanner.hasNextLine()){
            currentLine3=scanner.nextLine();
            currentTrade =unmarshallTrade(currentLine3);
            TradeLog.put(currentTrade.getTradeID(), currentTrade);
        }
        scanner.close();
    }
    //for testing purposes
    @Override
    public Map<String, Order> getMap(){
        return BuyOrders;
    }

     public String getOrderNumber() throws OrderPersistenceException{
        Scanner scanner;
        try{
            scanner=new Scanner(new BufferedReader(new FileReader(ORDERNUMBERS_FILE)));
        } catch (Exception e){
            throw new OrderPersistenceException("OrderNumbers file not found.");
        }
        int orderNumber;
        orderNumber= S.integize(scanner.nextLine())+1;
        scanner.close();
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(ORDERNUMBERS_FILE));
        } catch (Exception e){
            throw new OrderPersistenceException("Could not load collection from memory.", e);
        }
        out.println(Integer.toString(orderNumber));
        out.flush();
        out.close();
        return Integer.toString(orderNumber);
    }
     @Override
     public String getTradeNumber() throws TradePersistenceException{
        Scanner scanner;
        try{
            scanner=new Scanner(new BufferedReader(new FileReader(TRADENUMBERS_FILE)));
        } catch (Exception e){
            throw new TradePersistenceException("TradeNumbers file not found.");
        }
        int orderNumber;
        orderNumber= S.integize(scanner.nextLine())+1;
        scanner.close();
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(TRADENUMBERS_FILE));
        } catch (Exception e){
            throw new TradePersistenceException("Could not load collection from memory.", e);
        }
        out.println(Integer.toString(orderNumber));
        out.flush();
        out.close();
        return Integer.toString(orderNumber);
    }
     
    @Override
    public Trade addTrade(Trade trade) throws TradePersistenceException {
        try {
            loadCollection();
        } catch (OrderPersistenceException ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        trade.setTradeID("TRADE"+getTradeNumber());
        Trade newTrade= TradeLog.put(trade.getTradeID(), trade);

        try {
            writeCollection();
        } catch (OrderPersistenceException ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return newTrade;
    }
    
    @Override
    public void clearTables(boolean clearBuy, boolean clearSell, boolean clearTrades) throws OrderPersistenceException{
        if (clearBuy){
            BuyOrders.clear();
        }
        if (clearSell){
            SellOrders.clear();
        }
        if (clearTrades){
            TradeLog.clear();
        }
        writeCollection(); 
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(TRADENUMBERS_FILE));
        } catch (Exception e){
            throw new OrderPersistenceException("Could not load collection from memory.", e);
        }
        out.println("0");
        out.flush();
        out.close();
        
        try {
            out = new PrintWriter(new FileWriter(ORDERNUMBERS_FILE));
        } catch (Exception e){
            throw new OrderPersistenceException("Could not load collection from memory.", e);
        }
        out.println("0");
        out.flush();
        out.close();
        
        writeCollection();
    }
    
}
