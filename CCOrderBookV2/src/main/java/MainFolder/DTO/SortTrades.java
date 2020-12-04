/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DTO;

import java.util.Comparator;
import s.S;

/**
 *
 * @author recyn
 */
public class SortTrades implements Comparator<Trade>{
    @Override
    public int compare(Trade trade1, Trade trade2) { 
  
    // for comparison 
    String[] one= trade1.getTradeID().split("E");
    String[] two= trade2.getTradeID().split("E");
    int AgeCompare = Integer.parseInt(one[1])-Integer.parseInt(two[1]); 
    

        return AgeCompare; 
    } 
     
}
