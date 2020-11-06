/**
 * 
 */
package DAO;

import DTO.Car;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



/**
 * @author Flavio Silva
 *
 */
public class CarLotDAO implements CarLotDAOInterface {
    
    private Map<String, Car> cars = new HashMap<>();
	

	
    @Override
    public Car addCar(String VIN, Car car)  {	
	Car newCar = cars.put(VIN, car);
        return newCar;
    }
	
    @Override
    public List<Car> getCars() {	
	return new ArrayList<Car>(cars.values());
    }
	
    @Override
    public Car getCar(String VIN)  {
	return cars.get(VIN);
    }
	
    @Override
    public Car removeCar(String VIN)  {	
        Car removedCar = cars.remove(VIN);
	return removedCar;
    }

    @Override
    public void editCar(String VIN, Car car) {
        cars.replace(VIN, car);
    }
        
        
	
}
