/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.Service;


import Order.DAO.OrderDaoInterface;
import Order.DAO.OrderPersistenceException;
import Order.DTO.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import s.S;

/**
 *
 * @author recyn
 */
@Component
public class OrderServiceLayerImpl implements OrderServiceLayer {
    OrderDaoInterface dao;
    String TAXES_FILE;
    String PRODUCTS_FILE;

    private String DELIMITER = ",";
    @Autowired
    public OrderServiceLayerImpl(OrderDaoInterface dao){
        this.dao=dao;

        this.TAXES_FILE= "taxes.txt";
        this.PRODUCTS_FILE="products.txt";
    }
    
    
    //Adds a order to the DAO and makes sure the added order doesn't already exist and has valid parameters. 
    @Override
    public void createOrder(String date, Order order) throws OrderDataValidationException, OrderPersistenceException {

        
        /*if (dao.getOrder(order.getOrderNumber()) != null){
            throw new OrderDuplicateIdException("Error: Could not Create order. Order Number "
            + order.getOrderNumber()
            + " already exists");
        }*/
        
        //Now validate all the fields on the given Order object.
        //This method will throw an exception if any of the validation rules are violated.
        validateOrderData(order);
        setTheRest(order);
        //We passed all our business rules checks so go ahead and persist the order object
        dao.addOrder(date, order);
    }

    @Override
    public List<Order> getAllOrders(String date) throws OrderPersistenceException {
        return dao.getAllOrders(date);
    }
    
    @Override
    public Order getOrder(String date, int orderNumber) throws OrderPersistenceException {
        return dao.getOrder(date, orderNumber);
    }
    //Removes a order and will return the removed order if it exists. If not, it returns null. 
    @Override
    public Order removeOrder(String date, int orderNumber) throws OrderPersistenceException {
        Order removedOrder=null;
        removedOrder=dao.removeOrder(date, orderNumber);
        
        
        return removedOrder;
    }
    //Checks to see if a order has values for Customer Name, State, Product Type, and area. The string values must not be empty. 
    //Any error with the Hp would be caught in either the view or controller when converting a String input to an integer. 
    //Ideally this would also check whether or not the fields are also empty Strings. 
    private void validateOrderData(Order order) throws OrderDataValidationException{
        if (order.getCustomerName()==null || order.getCustomerName().equals("") 
                || order.getState()==null || order.getState().equals("")
                || order.getProductType()==null || order.getProductType().equals("")
                || order.getArea()==null
                ){
            throw new OrderDataValidationException("Error: All fields [Customer Name, State, ProductType, Area] are required.");
        }
    }
    

    //Given a orderName that definitely exists in the dao already, this extracts that order and updates the parameters if the updated parameter is not an empty String.
    //If the name has to be updated, then the original order is removed before the updatedOrder is added to the dao. 
    /*@Override
    public Order updateOrder(String orderName, String[] updatedParam) throws OrderPersistenceException {
            Order updatedOrder= dao.getOrder(orderName);
        if (!updatedParam[0].equals("")){
            updatedOrder.setName(updatedParam[0]);
            dao.removeOrder(orderName);
        }
        if (!updatedParam[1].equals("")){
            updatedOrder.setHp(Integer.parseInt(updatedParam[1]));
        }
        if (!updatedParam[2].equals("")){
            updatedOrder.setStage(updatedParam[2]);
        }
        if (!updatedParam[3].equals("")){
            updatedOrder.setAttack(updatedParam[3]);
        }
        if (!updatedParam[4].equals("")){
            updatedOrder.setElement(Element.valueOf(updatedParam[4]));
        }
        dao.addOrder(updatedOrder);
        return updatedOrder;
    }

*/
    //Gets an existing order and if the updated parameter is not an empty string, it changes it. It updates the order if the user decides to proceed.
    @Override
    public Order updateOrder(String date, int orderNumber, String[] updatedParam, boolean goAhead) throws OrderPersistenceException {
        Order updatedOrder=dao.getOrder(date, orderNumber);

        if (updatedOrder==null){
            return updatedOrder;
        }
        if (!updatedParam[0].equals("")){
            updatedOrder.setCustomerName(updatedParam[0]);
        }
        if (!updatedParam[1].equals("")){
            updatedOrder.setState(updatedParam[1]);
        }
        if (!updatedParam[2].equals("")){
            updatedOrder.setProductType(updatedParam[2]);
        }
        if (!updatedParam[3].equals("")){
            updatedOrder.setArea(new BigDecimal(updatedParam[3]));
        }
        setTheRest(updatedOrder);
        if (goAhead){
            dao.removeOrder(date, orderNumber);
            dao.addOrder(date, updatedOrder, true);
        }
        return updatedOrder;
    }

    @Override
    public void exportData() throws IOException, OrderPersistenceException {
        dao.exportData();
    }
    //Calculates the rest of the parameters given an order with State, Area, and Product type defined. 
    public void setTheRest(Order order) throws OrderPersistenceException{

        order.setTaxRate(findTaxRate(order.getState()));
        order.setCostPerSquareFoot(getCostPerSquareFoot(order.getProductType()));
        order.setLaborCostPerSquareFoot(getLaborCostPerSquareFoot(order.getProductType()));
        order.setMaterialCost(order.getArea().multiply(order.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP));
        order.setLaborCost(order.getArea().multiply(order.getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP));
        BigDecimal bg1 =order.getMaterialCost().add(order.getLaborCost()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal bg2= order.getTaxRate().divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
        order.setTax(bg1.multiply(bg2).setScale(2, RoundingMode.HALF_UP));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost()).add(order.getTax()).setScale(2, RoundingMode.HALF_UP));
    }
    //Checks the tax rate of a state. If no such state is on file, it throws an exception. 
    public BigDecimal findTaxRate(String state) throws OrderPersistenceException {
        Scanner scanner;
        //Opens up the taxes file. 
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load taxes data into memory", e);
        }
        String currentLine;
        String[] orderTokens;
        //Scans the second string of each line to see if the state matches the parameter. If it does, it returns the tax rate as a BigDecimal. 
        while (scanner.hasNextLine()){
            currentLine=scanner.nextLine();
            orderTokens= unmarshallOrder(currentLine);
            if (orderTokens[1].equals(state)){
                scanner.close();
                return new BigDecimal(orderTokens[2]);
            }                     
        }
        throw new OrderPersistenceException("Error: The input state is not on file.");
    }
    
    public String[] unmarshallOrder(String OrderAsText){
        String[] orderTokens= OrderAsText.split(DELIMITER);
        
        return orderTokens;
    }
    //Checks what the abbreviation is for a state name. If there isn't such a state on file, it throws an exception
    public String getAbbreviation(String state) throws OrderPersistenceException {
        Scanner scanner;
        //Opens the taxes file. 
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load taxes data into memory", e);
        }
        String currentLine;
        String[] orderTokens;
        //Scans the file for the second string on each line to see if it matches the state name and if it does, gets the abbreviation for it. 
        while (scanner.hasNextLine()){
            currentLine=scanner.nextLine();
            orderTokens= unmarshallOrder(currentLine);
            if (orderTokens[1].equals(state)){
                scanner.close();
                return orderTokens[0];
            }                     
        }
        throw new OrderPersistenceException("Error: The input state is not on file.");
    }
    //Checks what the full state name is for an abbreviation. If there isn't such a state on file, it throws an exception.
    public String getFullStateName(String abbrev) throws OrderPersistenceException {
        Scanner scanner;
        //Opens the taxes file. 
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load taxes data into memory", e);
        }
        String currentLine;
        String[] orderTokens;
        //Scans the file for the first string on each line to see if it matches the abbreviation and if it does, gets the full state name for it. 
        while (scanner.hasNextLine()){
            currentLine=scanner.nextLine();
            orderTokens= unmarshallOrder(currentLine);
            if (orderTokens[0].equals(abbrev)){
                scanner.close();
                return orderTokens[1];
            }                     
        }
        throw new OrderPersistenceException("Error: The input state is not on file.");
    }
    //Checks what the CostPerSquareFoot is for an input Product Type. If there isn't, it throws an exception. 
    public BigDecimal getCostPerSquareFoot(String productType) throws OrderPersistenceException {
        Scanner scanner;
        //Opens the product file with all the data.
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load taxes data into memory", e);
        }
        String currentLine;
        String[] orderTokens;
        //Scans the file for the first string on each line to see if it matches the product type and if it does, gets the data value for it. 
        while (scanner.hasNextLine()){
            currentLine=scanner.nextLine();
            orderTokens= unmarshallOrder(currentLine);
            if (orderTokens[0].equals(productType)){
                scanner.close();
                return new BigDecimal(orderTokens[1]);
            }                     
        }
        throw new OrderPersistenceException("Error: The input product is not on file.");
    }
    //Checks what the LaborCostPerSquareFoot is for an input Product Type. If there isn't, it throws an exception. 
    public BigDecimal getLaborCostPerSquareFoot(String productType) throws OrderPersistenceException {
        Scanner scanner;
        //Opens the product file with all the data.
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load taxes data into memory", e);
        }
        String currentLine;
        String[] orderTokens;
        //Scans the file for the first string on each line to see if it matches the product type and if it does, gets the data value for it. 
        while (scanner.hasNextLine()){
            currentLine=scanner.nextLine();
            orderTokens= unmarshallOrder(currentLine);
            if (orderTokens[0].equals(productType)){
                scanner.close();
                return new BigDecimal(orderTokens[2]);
            }                     
        }
        throw new OrderPersistenceException("Error: The input product is not on file.");
    }
    //Checks to see if the customer name only contains a-z and 0-9 and commas and periods
    @Override
    public boolean validateName(String name){
        List<String> characters= new ArrayList<>(Arrays.asList(name.split("")));
        if (characters.contains("!")
            || characters.contains("@")
            || characters.contains("#")    
            || characters.contains("$")
            || characters.contains("%")
            || characters.contains("^")
            || characters.contains("&")
            || characters.contains("*")
            || characters.contains("(")
            || characters.contains(")")
            || characters.contains("-")
            || characters.contains("_")
            || characters.contains("+")
            || characters.contains("=")
            || characters.contains("{")
            || characters.contains("}")
            || characters.contains("[")
            || characters.contains("]")
            || characters.contains("|")
            //|| characters.contains("\" )
            || characters.contains(":")
            || characters.contains(";")
            || characters.contains("")
            || characters.contains("<")
            || characters.contains(">")
            || characters.contains("/")
            || characters.contains("?")
            || characters.contains("`")
            || characters.contains("~")
                ){
            return false;
        }
       return true; 
    }
}
