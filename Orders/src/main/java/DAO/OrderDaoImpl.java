/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import s.S;

/**
 *
 * @author recyn
 */

public class OrderDaoImpl implements OrderDaoInterface {
    public final String ORDERNUMBERS_FILE;
    public static final String DELIMITER = "::";
  
    
    public OrderDaoImpl(){
        ORDERNUMBERS_FILE="orderNumbers.txt";
        
    }
    public OrderDaoImpl(String textFile){
        ORDERNUMBERS_FILE=textFile;
        
    }
    public Map<Integer, Order> Orders = new HashMap<>();
    
    private List<Integer> orderNumbers= new ArrayList();
   
    @Override
    public Order addOrder(String date, Order order) throws OrderPersistenceException{
        String fileName= "Orders_"+date+".txt";
        try {
            loadCollection(fileName);
        } catch (OrderPersistenceException e) {
            Orders.clear();
        }
        order.setOrderNumber(getOrderNumber());
        Order newOrder= Orders.put(order.getOrderNumber(), order);
        try {
            writeCollection(fileName);
        } catch (IOException ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newOrder;
    }
    
    @Override
    public Order addOrder(String date, Order order, boolean y) throws OrderPersistenceException {
        String fileName= "Orders_"+date+".txt";
        try {
            loadCollection(fileName);
        } catch (OrderPersistenceException e) {
            Orders.clear();
        }
        Order newOrder= Orders.put(order.getOrderNumber(), order);
        try {
            writeCollection(fileName);
        } catch (IOException ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newOrder;
    }

    @Override
    public Order getOrder(String date, int orderNumber) throws OrderPersistenceException {
       String fileName= "Orders_"+date+".txt";
       loadCollection(fileName);
       Order selectedOrder=Orders.get(orderNumber);
       return selectedOrder;
    }

    @Override
    public List<Order> getAllOrders(String date)  throws OrderPersistenceException{
        String fileName= "Orders_"+date+".txt";

        loadCollection(fileName);

        return new ArrayList<>(Orders.values());
    }
    //Ended up not using this. For editing, the program just makes a new order and adds it in the service layer. 


    @Override
    public Order removeOrder(String date, int orderNumber)  throws OrderPersistenceException{
        String fileName= "Orders_"+date+".txt";
        loadCollection(fileName);
        Order removed= Orders.remove(orderNumber);
        try {
            writeCollection(fileName);
        } catch (IOException ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return removed;
    }
    private Order unmarshallOrder(String OrderAsText){
        String[] OrderTokens= OrderAsText.split(DELIMITER);
        int orderNumber=S.integize(OrderTokens[0]);
        String customerName= OrderTokens[1];
        String state= OrderTokens[2];
        BigDecimal taxRate= new BigDecimal(OrderTokens[3]);
        String productType= OrderTokens[4];
        BigDecimal area= new BigDecimal(OrderTokens[5]);
        BigDecimal CostPerSquareFoot= new BigDecimal(OrderTokens[6]);
        BigDecimal LaborCostPerSquareFoot= new BigDecimal(OrderTokens[7]);
        BigDecimal materialCost= new BigDecimal(OrderTokens[8]);
        BigDecimal laborCost= new BigDecimal(OrderTokens[9]);
        BigDecimal tax=new BigDecimal(OrderTokens[10]);
        BigDecimal total= new BigDecimal(OrderTokens[11]);
        Order OrderFromFile= new Order(orderNumber, customerName, state, taxRate, productType, area, CostPerSquareFoot, LaborCostPerSquareFoot, materialCost, laborCost, tax, total);

        return OrderFromFile;
    }
    
    private String marshallOrder(Order aOrder){
        
        String OrderAsText=Integer.toString(aOrder.getOrderNumber())+DELIMITER;
        
        OrderAsText += aOrder.getCustomerName()+DELIMITER;
        
        OrderAsText += aOrder.getState()+DELIMITER;
        
        OrderAsText += aOrder.getTaxRate().toString()+DELIMITER;
        
        OrderAsText += aOrder.getProductType()+DELIMITER;
        
        OrderAsText += aOrder.getArea().toString()+DELIMITER;
        
        OrderAsText += aOrder.getCostPerSquareFoot().toString()+DELIMITER;
        
        OrderAsText += aOrder.getLaborCostPerSquareFoot().toString()+DELIMITER;
        
        OrderAsText += aOrder.getMaterialCost().toString()+DELIMITER;
        
        OrderAsText += aOrder.getLaborCost().toString()+DELIMITER;
        
        OrderAsText += aOrder.getTax().toString()+DELIMITER;
        
        OrderAsText += aOrder.getTotal().toString()+DELIMITER;
        
        return OrderAsText;
    }
    //writes to the text file using information from the map
    private void writeCollection(String fileName) throws OrderPersistenceException, IOException{
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter("src/main/orders/"+fileName));
        } catch (Exception e){
            throw new OrderPersistenceException("Could not load collection from memory.", e);
        }
        out.println("OrderNumber::CustomerName::State:TaxRate::ProductType::Area:CostPerSquareFoot::LaborCost::Tax:Total");
        Set<Integer> keys= Orders.keySet();
        for (int k:keys){
            String OrderAsText= marshallOrder(Orders.get(k));
            out.println(OrderAsText);
        }
        
        out.flush();
        out.close();
        
    }
    
    //creates a new map using information from the text file
    private void loadCollection(String dateFile) throws OrderPersistenceException {
        Scanner scanner;
        try {
            scanner=new Scanner(new BufferedReader(new FileReader("src/main/orders/"+dateFile)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Error: No such order file exists.");
        }
        Orders.clear();
        String currentLine=scanner.nextLine();
        Order currentOrder;
        while (scanner.hasNextLine()){
            currentLine=scanner.nextLine();
            currentOrder=unmarshallOrder(currentLine);
            Orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        scanner.close();
    }
    //for testing purposes

    @Override
    public Map<Integer, Order> getMap() {
        return Orders;
    }
    
    public int getOrderNumber() throws OrderPersistenceException{
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
        return orderNumber;
    }
    
    @Override
    public void exportData() throws IOException, OrderPersistenceException {
        List<File> fileList= Files.walk(Paths.get("src/main/orders"))
                .filter(Files::isRegularFile)
                .map((p) -> p.toFile())
                .collect(Collectors.toList());
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter("src/main/backup/DataExport.txt"));
        } catch (Exception e){
            throw new OrderPersistenceException("Could not load collection from memory.", e);
        }
        out.println("OrderNumber::CustomerName::State:TaxRate::ProductType::Area:CostPerSquareFoot::LaborCost::Tax:Total::Date");
        for (File f: fileList){
            Scanner scanner;
            try {
                scanner=new Scanner(new BufferedReader(new FileReader(f)));
            } catch (FileNotFoundException e){
                throw new OrderPersistenceException("Error: No such order file exists.");
            }
            String currentLine= scanner.nextLine();
        
            while (scanner.hasNextLine()){
                currentLine=scanner.nextLine();
                out.println(currentLine+convertDate(f.getName()));
            }
            scanner.close();
           
        }
        
        out.flush();
        out.close();
 
    }
    @Override
    public String convertDate(String fileName){
        String[] stringList= fileName.split("");
        String output= "";
        for (int i=7; i< 9; i++){
            output += stringList[i];
        }
        output+= "-";
        output+= stringList[9];
        output+= stringList[10];
        output+= "-";
        output+= stringList[11];
        output+= stringList[12];
        output+= stringList[13];
        output+= stringList[14];
        return output;
    }
    
}
