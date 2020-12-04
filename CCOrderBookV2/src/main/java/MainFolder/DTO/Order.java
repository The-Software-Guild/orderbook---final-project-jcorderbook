/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DTO;

import java.math.BigDecimal;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author recyn
 */

public class Order {

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", Price=" + Price + ", quantity=" + quantity + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.orderID);
        hash = 89 * hash + Objects.hashCode(this.Price);
        hash = 89 * hash + this.quantity;
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
        final Order other = (Order) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.orderID, other.orderID)) {
            return false;
        }
        if (!Objects.equals(this.Price, other.Price)) {
            return false;
        }
        return true;
    }
    
    private String orderID;
    private BigDecimal Price;
    private int quantity;
    
    public Order (String orderID, BigDecimal Price, int quantity){
        this.orderID=orderID;
        this.Price=Price;
        this.quantity=quantity;
    }
    public void setOrderID(String orderID){
        this.orderID= orderID;
    }
    
    public String getOrderID(){
        return orderID;
    }
    
    public void setPrice(BigDecimal Price){
        this.Price= Price;
    }
    
    public BigDecimal getPrice(){
        return Price;
    }
    
    public void setQuantity(int quantity){
        this.quantity= quantity;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
}