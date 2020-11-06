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
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author recyn
 */
public class CarLotServiceLayerImplTest {
    CarLotDAO dao= new CarLotDAO();
    CarLotService service= new CarLotServiceLayerImpl();
    
    public static void main(String[] args) throws NoSuchCarException, OverpaidPriceException, UnderpaidPriceException{
    CarLotDAO dao= new CarLotDAO();
    CarLotService service= new CarLotServiceLayerImpl();
    
     BigDecimal price= new BigDecimal(100000.00);
        long miles= 50000;
        CarKey carKey= new CarKey("VIN1", true);
        Car test= new Car("VIN1", "Make1", "i8", "White", price, miles, carKey);
        System.out.println("HELLOOOOOOOOOOOOO");
       
        
        Car getCar= service.getACar("VIN1");
        
        System.out.println((test.equals(getCar)));
    
        List<Car> coloredCars= service.getCarsByColor("White");
        System.out.println("All White Cars");
        for (Car c:coloredCars){
            System.out.println(c.getColor());
        }
        List<Car> AllCars= service.getAllCars();
        System.out.println("All Cars");
        for (Car c:AllCars){
            System.out.println(c.getVIN());
        }
        List<Car> BudgetCars= service.getCarsInBudget(new BigDecimal(200000));
        System.out.println("All Cars Under 200000");
        for (Car c:BudgetCars){
            System.out.println(c.getVIN());
        }
        List<Car> BudgetCars2= service.getCarsInBudget(new BigDecimal(100000));
        System.out.println("All Cars Under 100000");
        for (Car c:BudgetCars2){
            System.out.println(c.getVIN());
        }
        List<Car> MadeModelCars= service.getCarByMakeAndModel("Make1", "i8");
        System.out.println("All Cars with Make1 and i8");
        for (Car c:MadeModelCars){
            System.out.println(c.getVIN());
        }
        
        List<Car> coloredCars2= service.getCarsByColor("Black");
        System.out.println("All Black Cars");
        for (Car c:coloredCars2){
            System.out.println(c.getVIN());
        }
        service.discountCar("VIN1", new BigDecimal(0.15));
        System.out.println(service.getACar("VIN1").getPrice());
        
        CarKey keys= service.sellCar("VIN1", new BigDecimal(85000));
        System.out.print(keys.getVIN());
    }
    
    public CarLotServiceLayerImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void addGetCarTest(){
        BigDecimal price= new BigDecimal(100000.00);
        long miles= 50000;
        CarKey carKey= new CarKey("VIN1", true);
        Car test= new Car("VIN1", "Make1", "i8", "White", price, miles, carKey);
        System.out.println("HELLOOOOOOOOOOOOO");
       
        
        Car getCar= service.getACar("VIN1");
        
        assertEquals(test, getCar, "Gotten monster should be the same");
        
    }
    
}
