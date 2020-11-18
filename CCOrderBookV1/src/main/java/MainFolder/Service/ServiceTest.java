/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.Service;

import MainFolder.DAO.OrderDaoImpl;
import MainFolder.DAO.OrderDaoInterface;
import MainFolder.DAO.OrderPersistenceException;
import MainFolder.DTO.Order;
import MainFolder.DTO.Trade;
import java.math.BigDecimal;
import java.util.List;
import s.S;

/**
 *
 * @author recyn
 */
public class ServiceTest {
    public static void main(String[] args) throws Exception{
        OrderDaoInterface dao= new OrderDaoImpl();
        OrderServiceLayerImpl service= new OrderServiceLayerImpl(dao);
        //Order gottenOrder= service.getBuyOrder("1");
        //S.print(gottenOrder.getOrderID());
        //service.removeBuyOrder("7");
        //service.removeSellOrder("8");
        //service.addBuyRandomOrder();
        //service.addSellRandomOrder();
        service.clearTables();
        
        service.generateNRandomOrders(20);
        Order order1= new Order("0", new BigDecimal ("190.67"), 35);
        //service.createOrder(order1, true);
        
        List<Order> AllBuyOrders= service.sortBuyOrders();
        S.print("BuyOrders");
        AllBuyOrders.stream()
                .forEach((p)-> S.print(p.getOrderID()+","+p.getPrice().toString()+","+p.getQuantity()));
        List<Order> AllSellOrders= service.sortSellOrders();
        S.print("SellOrders");
        AllSellOrders.stream()
                .forEach((p)-> S.print(p.getOrderID()+","+p.getPrice().toString()+","+p.getQuantity()));
        
        service.matchAllOrders();
        AllBuyOrders= service.sortBuyOrders();
        S.print("BuyOrders");
        AllBuyOrders.stream()
                .forEach((p)-> S.print(p.getOrderID()+","+p.getPrice().toString()+","+p.getQuantity()));
        AllSellOrders= service.sortSellOrders();
        S.print("SellOrders");
        AllSellOrders.stream()
                .forEach((p)-> S.print(p.getOrderID()+","+p.getPrice().toString()+","+p.getQuantity()));
        //S.print("All Trades");
        
        /*List<Trade> allTrades= service.sortTrades();
        allTrades.stream()
                .forEach((p)-> S.print(p.getTradeID()+","+p.getExecutedPrice().toString()+","+p.getQuantity()));
                */
    }
}
