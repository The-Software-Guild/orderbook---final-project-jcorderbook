/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Objects;

/**
 *
 * @author recyn
 */
public class CarKey {

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.VIN);
        hash = 83 * hash + (this.laserCut ? 1 : 0);
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
        final CarKey other = (CarKey) obj;
        if (this.laserCut != other.laserCut) {
            return false;
        }
        if (!Objects.equals(this.VIN, other.VIN)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CarKey{" + "VIN=" + VIN + ", laserCut=" + laserCut + '}';
    }

   
    
    private String VIN;
    private boolean laserCut;
    public CarKey(String vin, boolean laserCut){
        this.VIN=vin;
        this.laserCut=laserCut;
    }
    public void setVIN(String vin){
        this.VIN=vin;
    }
    public void setLaserCut(boolean laserCut){
        this.laserCut=laserCut;
    }
    public String getVIN(){
        return VIN;
    }
    public boolean getLaserCut(){
        return laserCut;
    }
    
}
