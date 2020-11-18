/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DTO;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 *
 * @author recyn
 */
@Component
public class Order {
    
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