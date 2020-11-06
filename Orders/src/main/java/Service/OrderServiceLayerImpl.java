/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.OrderDaoInterface;
import DAO.OrderPersistenceException;
import DTO.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;
import s.S;

/**
 *
 * @author recyn
 */
public class OrderServiceLayerImpl implements OrderServiceLayer {
    OrderDaoInterface dao;
    String TAXES_FILE;
    String PRODUCTS_FILE;

    private String DELIMITER = ",";
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
    //Checks to see if a order has values for all its member fields, except for Hp, because Hp is an int and cannot be null. 
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
    
    public BigDecimal findTaxRate(String state) throws OrderPersistenceException {
        Scanner scanner;
        
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load taxes data into memory", e);
        }
        String currentLine;
        String[] orderTokens;
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
    
    public String getAbbreviation(String state) throws OrderPersistenceException {
        Scanner scanner;
        
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load taxes data into memory", e);
        }
        String currentLine;
        String[] orderTokens;
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
    
    public String getFullStateName(String abbrev) throws OrderPersistenceException {
        Scanner scanner;
        
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load taxes data into memory", e);
        }
        String currentLine;
        String[] orderTokens;
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
    public BigDecimal getCostPerSquareFoot(String productType) throws OrderPersistenceException {
        Scanner scanner;
        
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load taxes data into memory", e);
        }
        String currentLine;
        String[] orderTokens;
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
    
    public BigDecimal getLaborCostPerSquareFoot(String productType) throws OrderPersistenceException {
        Scanner scanner;
        
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e){
            throw new OrderPersistenceException("Could not load taxes data into memory", e);
        }
        String currentLine;
        String[] orderTokens;
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
    
    
}
