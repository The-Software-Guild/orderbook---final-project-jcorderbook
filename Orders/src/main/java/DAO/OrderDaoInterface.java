/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Order;
import java.io.IOException;
import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

/**
 *
 * @author recyn
 */
public interface OrderDaoInterface {

    Order addOrder(String date, Order order) throws OrderPersistenceException;
    
    Order addOrder(String date, Order order, boolean y) throws OrderPersistenceException;
    
    List<Order> getAllOrders(String date) throws OrderPersistenceException;
    
    Order getOrder (String date, int orderNumber) throws OrderPersistenceException;
    
    Order removeOrder(String date, int orderNumber) throws OrderPersistenceException;
    
    //Order editOrder(int orderNumber, String customerName, String state, String productType, BigDecimal taxRate, BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) throws OrderPersistenceException;
    
    Map<Integer, Order> getMap();
    
    void exportData() throws IOException, OrderPersistenceException;
    
    String convertDate(String fileName);
}
