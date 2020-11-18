/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DTO;

import java.math.BigDecimal;

/**
 *
 * @author recyn
 */
public class Trade {
    String tradeID;
    //Execution time???? wtf
    BigDecimal executedPrice;
    int quantity;
    
    
    public Trade(String tradeID, BigDecimal executedPrice, int quantity ){
        this.tradeID=tradeID;
        this.quantity=quantity;
        this.executedPrice=executedPrice;
        this.executionTime
    }
    
    public String getTradeID() {
        return tradeID;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getExecutedPrice() {
        return executedPrice;
    }

    public void setTradeID(String tradeID) {
        this.tradeID = tradeID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExecutedPrice(BigDecimal executedPrice) {
        this.executedPrice = executedPrice;
    }
    
    
}
