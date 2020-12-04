/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author recyn
 */
public class Trade {

    @Override
    public String toString() {
        return "Trade{" + "tradeID=" + tradeID + ", executedPrice=" + executedPrice + ", quantity=" + quantity + ", dateTime=" + dateTime + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.tradeID);
        hash = 97 * hash + Objects.hashCode(this.executedPrice);
        hash = 97 * hash + this.quantity;
        hash = 97 * hash + Objects.hashCode(this.dateTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trade other = (Trade) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.tradeID, other.tradeID)) {
            return false;
        }
        if (!Objects.equals(this.executedPrice, other.executedPrice)) {
            return false;
        }
        if (!Objects.equals(this.dateTime, other.dateTime)) {
            return false;
        }
        return true;
    }
    
    
    String tradeID;
    //Execution time???? wtf
    BigDecimal executedPrice;
    int quantity;
    //long executionTime;
    LocalDateTime dateTime;
    
    public Trade(String tradeID, BigDecimal executedPrice, int quantity, LocalDateTime dateTime ){
        this.tradeID=tradeID;
        this.quantity=quantity;
        this.executedPrice=executedPrice;
        //this.executionTime= executionTime;
        this.dateTime=dateTime;
    }

    public LocalDateTime getTime() {
        return dateTime;
    }

    public void setTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
