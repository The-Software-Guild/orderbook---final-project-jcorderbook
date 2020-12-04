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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        service.clearTables();
        service.generateNRandomOrders(20);
        try {
            Order order1 = new Order("BORD1000", new BigDecimal("190.67"), 0);
            service.createOrder(order1, true);
        }catch (Exception e){
            S.print("Order creation exception : " + e.getMessage());
        }

        List<Order> allBuyOrders= service.sortBuyOrders();

        final Order order = allBuyOrders.get(0);
        final String orderID = order.getOrderID();

        final Order sellOrder = service.getSellOrder(orderID);
        final Order buyOrder = service.getBuyOrder(orderID);

        if(sellOrder == null || buyOrder == null){
            S.print("Not a valid order id : " + orderID);
        }

        final boolean empty = service.isEmpty();
        S.print("Should be false : " + empty);

        final Order order1 = service.removeBuyOrder(orderID);
        final Order order2 = service.removeSellOrder(orderID);

        if(order1 == null || order2 == null){
            S.print("Not a valid order id to delete : " + orderID);
        }

        final Trade trade = service.matchTopOrder();
        S.print("Trade{" +
                "tradeID='" + trade.getTradeID() + '\'' +
                ", executedPrice=" + trade.getExecutedPrice() +
                ", quantity=" + trade.getQuantity() +
                ", dateTime=" + trade.getTime() +
                '}');


        final Order order3 = service.addBuyRandomOrder();
        S.print("Random Buy Order");
        S.print(order3.getOrderID()+","+order3.getPrice().toString()+","+order3.getQuantity());
        S.print("Random Sell Order");
        final Order order4 = service.addSellRandomOrder();
        S.print(order4.getOrderID()+","+order4.getPrice().toString()+","+order4.getQuantity());

        final List<Trade> trades = service.tradesByDate(LocalDate.now());
        S.print("Trade by Date");
        trades
                .forEach((p)-> S.print(p.getTradeID()+","+p.getExecutedPrice().toString()+","+p.getQuantity()));

        final List<Trade> trades2 = service.tradesByTime(LocalDate.now(),
                LocalTime.now().minusMinutes(10), true, true);
        S.print("Trade by Time");
        trades2
                .forEach((p)-> S.print(p.getTradeID()+","+p.getExecutedPrice().toString()+","+p.getQuantity()));

        final List<Trade> trades3 = service.tradesByQuantity(10);
        S.print("Trade by Quantity");
        trades3
                .forEach((p)-> S.print(p.getTradeID()+","+p.getExecutedPrice().toString()+","+p.getQuantity()));


        final Order order5 = service.editBuyOrder(new String[]{orderID, "34.00", "12"});
        final Order order6 = service.editSellOrder(new String[]{orderID, "34.00", "12"});

        if(order5 != null) {
            S.print("Edited Buy Order");
            S.print(order5.getOrderID() + "," + order5.getPrice().toString() + "," + order5.getQuantity());
        }
        if(order6!= null) {
            S.print("Edited Sell Order");
            S.print(order6.getOrderID() + "," + order6.getPrice().toString() + "," + order6.getQuantity());
        }

        S.print("BuyOrders");
        allBuyOrders
                .forEach((p)-> S.print(p.getOrderID()+","+p.getPrice().toString()+","+p.getQuantity()));
        List<Order> allSellOrders= service.sortSellOrders();
        S.print("SellOrders");
        allSellOrders
                .forEach((p)-> S.print(p.getOrderID()+","+p.getPrice().toString()+","+p.getQuantity()));
        
        service.matchAllOrders();
        allBuyOrders= service.sortBuyOrders();
        S.print("BuyOrders");
        allBuyOrders
                .forEach((p)-> S.print(p.getOrderID()+","+p.getPrice().toString()+","+p.getQuantity()));
        allSellOrders= service.sortSellOrders();
        S.print("SellOrders");
        allSellOrders
                .forEach((p)-> S.print(p.getOrderID()+","+p.getPrice().toString()+","+p.getQuantity()));
        S.print("All Trades");
        
        List<Trade> allTrades= service.sortTrades();
        allTrades
                .forEach((p)-> S.print(p.getTradeID()+","+p.getExecutedPrice().toString()+","+p.getQuantity()));

    }
}
