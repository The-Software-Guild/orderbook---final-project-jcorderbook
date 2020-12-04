/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DTO;

import java.util.Comparator;

/**
 *
 * @author recyn
 */
public class Sort implements Comparator<Order>{
    @Override
    public int compare(Order order1, Order order2) { 
  
    // for comparison 
    int PriceCompare = order2.getPrice().compareTo(order1.getPrice());
    String[] one= order1.getOrderID().split("D");
    String[] two= order2.getOrderID().split("D");
    int AgeCompare = Integer.parseInt(one[1])-Integer.parseInt(two[1]); 
  
    // 2-level comparison using if-else block 
    if (PriceCompare == 0) { 
        return ((AgeCompare == 0) ? PriceCompare : AgeCompare); 
    } else { 
        return PriceCompare; 
    } 
    } 
}
