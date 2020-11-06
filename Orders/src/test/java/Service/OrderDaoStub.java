/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;


import DAO.OrderDaoInterface;
import DAO.OrderPersistenceException;
import DTO.Order;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author recyn
 */
public class OrderDaoStub implements OrderDaoInterface {
    Map<Integer, Order> Orders= new HashMap<>();
    String date = "01012021";
    
    int orderNumber=1;
    BigDecimal taxRate= new BigDecimal("25.00");
    BigDecimal area= new BigDecimal("249.00");
    BigDecimal CostPerSquareFoot= new BigDecimal("3.50");
    BigDecimal LaborCostPerSquareFoot= new BigDecimal("4.15");
    BigDecimal materialCost= new BigDecimal("871.50");
    BigDecimal laborCost= new BigDecimal("1033.35");
    BigDecimal tax= new BigDecimal("476.21");
    BigDecimal total= new BigDecimal("2381.06");
    Order test= new Order(orderNumber, "Ada Lovelace", "California", taxRate, "Tile", area, CostPerSquareFoot, LaborCostPerSquareFoot, materialCost, laborCost, tax, total);    
    public OrderDaoStub(){
        Orders.put(orderNumber, test);
    }
    @Override
    public Order addOrder(String inputDate, Order order) throws OrderPersistenceException {
        if (order.getOrderNumber()== 1 && inputDate.equals(date)){
            return Orders.get(1);
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders(String inputDate) throws OrderPersistenceException {
        if (inputDate.equals(date)){
             List<Order> output= new ArrayList(Orders.values());
             return output;
        }
        throw new OrderPersistenceException("Could not load collection from memory.");
    }

    @Override
    public Order getOrder(String inputDate, int orderNumber) throws OrderPersistenceException {
        if (orderNumber== 1 && inputDate.equals(date)){
            return Orders.get(1);
        }
        if (!inputDate.equals(date)){
            throw new OrderPersistenceException("No such file exists on that date");
        }
        return null;
    }

    @Override
    public Order removeOrder(String inputDate, int orderNumber) throws OrderPersistenceException {
        if (orderNumber== 1&& inputDate.equals(date)){
            return Orders.get(1);
        }
        if (!inputDate.equals(date)){
            throw new OrderPersistenceException("No such file exists on that date");
        }
        return null;
    }

    @Override
    public Map<Integer, Order> getMap() {
        return Orders;
    }

    @Override
    public Order addOrder(String inputDate, Order order, boolean y) throws OrderPersistenceException {
        return null;
    }

    @Override
    public void exportData() throws IOException, OrderPersistenceException {
        
    }

    @Override
    public String convertDate(String fileName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
