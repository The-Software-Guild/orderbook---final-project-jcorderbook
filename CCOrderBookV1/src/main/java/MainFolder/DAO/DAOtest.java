/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DAO;

import MainFolder.DTO.Order;
import java.math.BigDecimal;
import java.util.List;
import s.S;

/**
 *
 * @author recyn
 */
public class DAOtest {
    public static void main(String[] args) throws OrderPersistenceException{
        OrderDaoInterface dao= new OrderDaoImpl();
        Order order1= new Order("0", new BigDecimal ("190.25"), 45);
        //dao.addBuyOrder(order1);
        Order order2= new Order("0", new BigDecimal("190.10"), 47);
        //dao.addSellOrder(order2);
        //dao.removeBuyOrder("3");
        //dao.removeSellOrder("4");
        List<Order> buyOrders=dao.getAllBuyOrders();
        S.print("BuyOrders");
        S.print(buyOrders.size()+"");
        buyOrders.stream()
                .forEach((p)-> S.print(p.getOrderID()+","+p.getPrice().toString()+","+p.getQuantity()));
        List<Order> sellOrders=dao.getAllSellOrders();
        S.print("SellOrders");
        S.print(sellOrders.size()+"");
        sellOrders.stream()
                .forEach((p)-> S.print(p.getOrderID()+","+p.getPrice().toString()+","+p.getQuantity()));
        Order gottenBuyOrder= dao.getBuyOrder("1");
        Order gottenSellOrder= dao.getSellOrder("2");
        S.print(gottenBuyOrder.getOrderID());
        S.print(gottenSellOrder.getOrderID());
        
    } 
}
