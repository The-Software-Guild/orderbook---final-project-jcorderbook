/**
 * 
 */
package DTO;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Flavio Silva
 *
 */
public class Car {
    private String VIN;
    private String make;
    private String model;
    private String color;

    private BigDecimal price;
    private long odometerMiles;

    private CarKey key;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.VIN);
        hash = 53 * hash + Objects.hashCode(this.make);
        hash = 53 * hash + Objects.hashCode(this.model);
        hash = 53 * hash + Objects.hashCode(this.color);
        hash = 53 * hash + Objects.hashCode(this.price);
        hash = 53 * hash + (int) (this.odometerMiles ^ (this.odometerMiles >>> 32));
        hash = 53 * hash + Objects.hashCode(this.key);
        return hash;
    }

    @Override
    public String toString() {
        return "Car{" + "VIN=" + VIN + ", make=" + make + ", model=" + model + ", color=" + color + ", price=" + price + ", odometerMiles=" + odometerMiles + ", key=" + key + '}';
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
        final Car other = (Car) obj;
        if (this.odometerMiles != other.odometerMiles) {
            return false;
        }
        if (!Objects.equals(this.VIN, other.VIN)) {
            return false;
        }
        if (!Objects.equals(this.make, other.make)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        return true;
    }


	
	public Car(String VIN) {
		this.VIN = VIN;
	}
        
        public Car(String VIN, String make, String model, String color, BigDecimal price, long odometerMiles, CarKey key) {
		this.VIN = VIN;
                this.color=color;
                this.key=key;
                this.make=make;
                this.model=model;
                this.odometerMiles=odometerMiles;
                this.price= price;
                
	}
	
	public String getMake() {
		return make;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getVIN() {
		return VIN;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color){
		this.color = color;
	}
        
        public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price){
		this.price = price;
	}
        
        public long getOdometerMiles() {
		return odometerMiles;
	}
	
	public void setOdometerMiles(long odometerMiles){
		this.odometerMiles = odometerMiles;
	}
        
        public CarKey getKey() {
		return key;
	}
	
	public void setKey(CarKey carkey){
		this.key = carkey;
	}
        
        
}
