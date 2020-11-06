/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import DAO.OrderPersistenceException;
import java.math.BigDecimal;
import s.S;

/**
 *
 * @author recyn
 */
public class DTOtest {
    //Test initialize with all parameters given
    //Test initialize with only a few parameters given
    //Test get and set functions
    public static void main(String[] args) throws OrderPersistenceException{
        Order newOrder;
        int orderNumber=1;
        BigDecimal taxRate= new BigDecimal("25");
        BigDecimal area= new BigDecimal("249");
        BigDecimal CostPerSquareFoot= new BigDecimal("3.50");
        BigDecimal LaborCostPerSquareFoot= new BigDecimal("4.15");
        BigDecimal materialCost= new BigDecimal("871.50");
        BigDecimal laborCost= new BigDecimal("1033.35");
        BigDecimal tax= new BigDecimal("476.21");
        BigDecimal total= new BigDecimal("2381.06");
        newOrder= new Order(orderNumber, "Ada LoveLace", "California", taxRate, "Tile", area, CostPerSquareFoot, LaborCostPerSquareFoot, materialCost, laborCost, tax, total);
        
        S.print(newOrder.getOrderNumber()+"");
        S.print(newOrder.getArea().toString());
        S.print(newOrder.getCustomerName());
        
        Order newOrder2= new Order(orderNumber, "Ada LoveLace", "California", "Tile", area);
        S.print(newOrder.getTaxRate().toString());
        S.print(newOrder.getCostPerSquareFoot().toString());
        S.print(newOrder.getLaborCostPerSquareFoot().toString());
        S.print(newOrder.getMaterialCost().toString());
        S.print(newOrder.getLaborCost().toString());
        S.print(newOrder.getTax().toString());
        S.print(newOrder.getTotal().toString());
        //Order (int orderNumber, String customerName, String state, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costPerSquareFoot, 
          //          BigDecimal laborCostPerSquareFoot, BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax, BigDecimal total)
    }

}
