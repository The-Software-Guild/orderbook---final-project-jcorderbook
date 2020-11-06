/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.dto;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import s.S;

/**
 *
 * @author recyn
 */

public class Item {

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.cost);
        hash = 97 * hash + this.count;
        
        return hash;
    }

    @Override
    public String toString() {
        return "Item{" + "name=" + name + ", cost=" + cost + ", count=" + count  + '}';
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
        final Item other = (Item) obj;
        if (this.count != other.count) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
       
        return true;
    }

    
    
    private String name;
    private BigDecimal cost;
    private int count;

    
    public Item (String name){
        this.name= name;
    }
    
    public Item (String name, BigDecimal cost, int count){
        this.name= name;
        this.cost= cost;
        this.count=count;
       
        
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name= name;
    }
    
    public void setCost(BigDecimal cost){
        this.cost= cost;

    }
    
  
    
    public BigDecimal getCost() {
        return this.cost;

    }
    
    public String getStringCost(){
        return cost.toString();
    }
    
    public int getCount(){
        return this.count;
    }
    
    public String getStringCount(){
        return Integer.toString(count);
    }
    
    public void setCount(int count){
        this.count =count;
    }
    
    public void updateRatingNote(List<String> ratingNote){
        
    }
    
}
