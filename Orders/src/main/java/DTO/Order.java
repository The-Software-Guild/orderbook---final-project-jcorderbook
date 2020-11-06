/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;


import DAO.OrderPersistenceException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author recyn
 */
public class Order {
/*
OrderNumber – Integer
CustomerCustomerName – String
State – String
TaxRate – BigDecimal
ProductType – String
Area – BigDecimal
CostPerSquareFoot – BigDecimal
LaborCostPerSquareFoot – BigDecimal
MaterialCost – BigDecimal
LaborCost – BigDecimal
Tax – BigDecimal
Total – BigDecimal
*/

    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;
    
    private String TAXES_FILE;
    private String DELIMITER= ",";
    private String PRODUCTS_FILE;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.orderNumber;
        hash = 59 * hash + Objects.hashCode(this.customerName);
        hash = 59 * hash + Objects.hashCode(this.state);
        hash = 59 * hash + Objects.hashCode(this.taxRate);
        hash = 59 * hash + Objects.hashCode(this.productType);
        hash = 59 * hash + Objects.hashCode(this.area);
        hash = 59 * hash + Objects.hashCode(this.costPerSquareFoot);
        hash = 59 * hash + Objects.hashCode(this.laborCostPerSquareFoot);
        hash = 59 * hash + Objects.hashCode(this.materialCost);
        hash = 59 * hash + Objects.hashCode(this.laborCost);
        hash = 59 * hash + Objects.hashCode(this.tax);
        hash = 59 * hash + Objects.hashCode(this.total);
        hash = 59 * hash + Objects.hashCode(this.TAXES_FILE);
        hash = 59 * hash + Objects.hashCode(this.DELIMITER);
        hash = 59 * hash + Objects.hashCode(this.PRODUCTS_FILE);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.TAXES_FILE, other.TAXES_FILE)) {
            return false;
        }
        if (!Objects.equals(this.DELIMITER, other.DELIMITER)) {
            return false;
        }
        if (!Objects.equals(this.PRODUCTS_FILE, other.PRODUCTS_FILE)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSquareFoot, other.laborCostPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order{" + "orderNumber=" + orderNumber + ", customerName=" + customerName + ", state=" + state + ", taxRate=" + taxRate + ", productType=" + productType + ", area=" + area + ", costPerSquareFoot=" + costPerSquareFoot + ", laborCostPerSquareFoot=" + laborCostPerSquareFoot + ", materialCost=" + materialCost + ", laborCost=" + laborCost + ", tax=" + tax + ", total=" + total + ", TAXES_FILE=" + TAXES_FILE + ", DELIMITER=" + DELIMITER + ", PRODUCTS_FILE=" + PRODUCTS_FILE + '}';
    }
    
    
    
    public Order(int orderNumber, String customerName, String state, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costPerSquareFoot, 
                    BigDecimal laborCostPerSquareFoot, BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax, BigDecimal total){
        this.orderNumber=orderNumber;
        this.customerName= customerName;
        this.state=state;
        this.taxRate=taxRate;
        this.productType=productType;
        this.area=area;
        this.costPerSquareFoot= costPerSquareFoot;
        this.laborCostPerSquareFoot= laborCostPerSquareFoot;
        this.materialCost= materialCost;
        this.laborCost= laborCost;
        this.tax= tax;
        this.total= total;

    }
    //Tax rates are stored as whole numbers
    
    public Order(int orderNumber, String customerName, String state, String productType, BigDecimal area) throws OrderPersistenceException{
        this.orderNumber=orderNumber;
        this.customerName= customerName;
        this.state= state;
        this.productType=productType;
        this.area=area;
        /*
        this.TAXES_FILE= "taxes.txt";
        this.taxRate= findTaxRate(state);
        this.PRODUCTS_FILE="products.txt";
        this.costPerSquareFoot= getCostPerSquareFoot(productType);
        this.laborCostPerSquareFoot= getLaborCostPerSquareFoot(productType);
        this.materialCost= area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        this.laborCost= area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        BigDecimal bg1= materialCost.add(laborCost).setScale(2, RoundingMode.HALF_UP);
        BigDecimal bg2= taxRate.divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
        this.tax= bg1.multiply(bg2).setScale(2, RoundingMode.HALF_UP);
        this.total= materialCost.add(laborCost).add(tax).setScale(2, RoundingMode.HALF_UP);
        */
    }
    
    
    public int getOrderNumber(){
        return orderNumber;
    }
    public void setOrderNumber(int orderNumber){
        this.orderNumber= orderNumber;
    }
    public void setCustomerName(String customerName){
        this.customerName=customerName;
    }
    
    public String getCustomerName(){
        return customerName;
    }

    public void setState(String state){
        this.state=state;
    }
    
    public String getState(){
        return state;
    }
    
    public void setTaxRate(BigDecimal taxRate){
        this.taxRate= taxRate;
    }
    
    public BigDecimal getTaxRate(){
        return taxRate;
    }
    
    public void setProductType(String productType){
        this.productType= productType;
    }
    
    public String getProductType(){
        return productType;
    }
    
    public void setArea(BigDecimal area){
        this.area= area;
    }
    
    public BigDecimal getArea(){
        return area;
    }
    
    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot){
        this.costPerSquareFoot= costPerSquareFoot;
    }
    
    public BigDecimal getCostPerSquareFoot(){
        return costPerSquareFoot;
    }
    
    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot){
        this.laborCostPerSquareFoot= laborCostPerSquareFoot;
    }
    
    public BigDecimal getLaborCostPerSquareFoot(){
        return laborCostPerSquareFoot;
    }
    /*
    public void setTheRest() throws OrderPersistenceException{
        this.TAXES_FILE= "taxes.txt";
        this.taxRate= findTaxRate(state);
        this.PRODUCTS_FILE="products.txt";
        this.costPerSquareFoot= getCostPerSquareFoot(productType);
        this.laborCostPerSquareFoot= getLaborCostPerSquareFoot(productType);
        this.materialCost= area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        this.laborCost= area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        BigDecimal bg1= materialCost.add(laborCost).setScale(2, RoundingMode.HALF_UP);
        BigDecimal bg2= taxRate.divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
        this.tax= bg1.multiply(bg2).setScale(2, RoundingMode.HALF_UP);
        this.total= materialCost.add(laborCost).add(tax).setScale(2, RoundingMode.HALF_UP);
    }
    */
    public BigDecimal getMaterialCost(){
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost){
        this.materialCost= materialCost;
    }
    public BigDecimal getLaborCost(){
        return laborCost;
    }
    
    public void setLaborCost(BigDecimal laborCost){
        this.laborCost=laborCost;
    }
    
    public BigDecimal getTax(){
        return tax;
    }
    
    public void setTax(BigDecimal tax){
        this.tax=tax;
    }
    
    public BigDecimal getTotal(){
        return total;
    }
    
    public void setTotal(BigDecimal total){
        this.total=total;
    }
    /*
    private BigDecimal findTaxRate(String state) throws OrderPersistenceException {
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
    
    private String[] unmarshallOrder(String OrderAsText){
        String[] orderTokens= OrderAsText.split(DELIMITER);
        

        return orderTokens;
    }
    
    private String getAbbreviation(String state) throws OrderPersistenceException {
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
    */
}
