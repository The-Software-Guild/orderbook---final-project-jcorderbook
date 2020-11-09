/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order.DAO;


import Order.DTO.Order;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import s.S;

/**
 *
 * @author recyn
 */
public class DAOtest {
    public static void main(String[] args) throws OrderPersistenceException, IOException{
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
        OrderDaoInterface dao= new OrderDaoImpl();
        Order newOrder2= new Order(orderNumber, "Albert", "California", taxRate, "Tile", area, CostPerSquareFoot, LaborCostPerSquareFoot, materialCost, laborCost, tax, total);
        //dao.addOrder("12252020", newOrder2);
        //dao.removeOrder("12252020", 6);
        //List<Order> listOrder= dao.getAllOrders("12252020");
        //listOrder.stream()
                //.forEach((p) -> S.print(p.getCustomerName()));
                /*
        Order aOrder= dao.getOrder("12252020", 6);
        S.print(aOrder+"");
        OrderAuditDao auditDao= new OrderAuditDaoFileImpl();
        OrderServiceLayer service= new OrderServiceLayerImpl(dao, auditDao);
        String[] updatedParam= {"NewCustomer", "Texas", "", "250"}; 
        Order updatedOrder= service.updateOrder("12252020", 7, updatedParam);
        S.print(updatedOrder+"");
        aOrder= dao.getOrder("12252020", 7);
        S.print(aOrder.getCustomerName());
        S.print(aOrder.getState());
        S.print(aOrder.getArea().toString());
        S.print(aOrder.getTotal().toString());
        
        */
        dao.exportData();
        
    }
}
