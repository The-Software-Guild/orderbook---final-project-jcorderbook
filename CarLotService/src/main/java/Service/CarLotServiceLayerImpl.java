/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.CarLotDAO;
import DTO.Car;
import DTO.CarKey;
import java.math.BigDecimal;
import static java.math.RoundingMode.HALF_UP;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author recyn
 */
public class CarLotServiceLayerImpl implements CarLotService {
    CarLotDAO dao= new CarLotDAO();
    //The following initializes some cars to put in the system. 
    BigDecimal price= new BigDecimal(100000.00);
    long miles= 50000;
    CarKey carKey= new CarKey("VIN1", true);
    CarKey carKey2= new CarKey("VIN2", false);
    CarKey carKey3= new CarKey("VIN3", true);
    Car test= new Car("VIN1", "Make1", "i8", "White", price, miles, carKey);
    Car result= dao.addCar("VIN1", test);
    
    BigDecimal price2= new BigDecimal(100001.00);
    Car test2= new Car("VIN2", "Make2", "i8", "Blue", price2, miles, carKey2);
    Car result2= dao.addCar("VIN2", test2);
    
    BigDecimal price3= new BigDecimal(50000.00);
    Car test3= new Car("VIN3", "Make1", "i8", "White", price3, miles, carKey3);
    Car result3= dao.addCar("VIN3", test3);
    
    @Override
    public Car getACar(String VIN) {
        return dao.getCar(VIN);
    }

    @Override
    public List<Car> getAllCars() {
        return dao.getCars();
    }

    @Override
    public List<Car> getCarsByColor(String color) {
        List<Car> carList= dao.getCars();
        List<Car> output= new ArrayList();
        for (Car c:carList){
            if (c.getColor().equals(color)){
                output.add(c);
            }
        }
        return output;
    }

    @Override
    public List<Car> getCarsInBudget(BigDecimal maxPrice) {
        List<Car> carList= dao.getCars();
        List<Car> output= new ArrayList();
        for (Car c:carList){
            if (c.getPrice().compareTo(maxPrice)<=0){
                output.add(c);
            }
        }
        return output;
    }

    @Override
    public List<Car> getCarByMakeAndModel(String make, String model) {
        List<Car> carList= dao.getCars();
        List<Car> output= new ArrayList();
        for (Car c:carList){
            if (c.getMake().equals(make) && c.getModel().equals(model)){
                output.add(c);
            }
        }
        return output;
    }
    //Applies a discount to the price of a car. 
    @Override
    public BigDecimal discountCar(String VIN, BigDecimal percentDiscount) throws NoSuchCarException {
        Car aCar= dao.getCar(VIN);
        if(aCar==null){
            throw new NoSuchCarException("No such car exists");
        } else {
        BigDecimal whole= new BigDecimal("1");
        BigDecimal percentage= whole.subtract(percentDiscount);
        
        BigDecimal discountedPrice= aCar.getPrice().multiply(percentage);
        BigDecimal exactPrice= discountedPrice.setScale(0, HALF_UP);
        aCar.setPrice(exactPrice);
        dao.editCar(VIN, aCar);
        return discountedPrice;}
    }

    //Sells a car if the user inputs the right amount of money, and chooses a car that exists in the system. Returns the bought car's carkey. 
    @Override
    public CarKey sellCar(String VIN, BigDecimal cashPaid) throws NoSuchCarException, OverpaidPriceException, UnderpaidPriceException {
        Car aCar=dao.getCar(VIN);
        CarKey output= null;
        if (aCar.getPrice().compareTo(cashPaid)==0){
            output= aCar.getKey();
            dao.removeCar(VIN);
        } else if (dao.getCar(VIN)== null){
            throw new NoSuchCarException("No such car exists.");
        }
        else if (aCar.getPrice().compareTo(cashPaid)<0){
            throw new OverpaidPriceException("Paid too much money");
        } else if (aCar.getPrice().compareTo(cashPaid)>0){
            throw new UnderpaidPriceException("Not enough money");
        }
        return output;
    }
    
    
    
}
