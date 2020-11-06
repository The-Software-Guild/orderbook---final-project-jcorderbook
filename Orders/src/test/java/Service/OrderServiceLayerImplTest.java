/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;


import DAO.OrderDaoInterface;
import DAO.OrderPersistenceException;
import DTO.Order;
import java.math.BigDecimal;
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
public class OrderServiceLayerImplTest {
    OrderDaoInterface dao= new OrderDaoStub();
    OrderServiceLayer service= new OrderServiceLayerImpl(dao);
    String date = "01012021";
    int orderNumber=1;
    BigDecimal taxRate= new BigDecimal("25.00");
    BigDecimal area= new BigDecimal("249.00");
    BigDecimal CostPerSquareFoot= new BigDecimal("3.50");
    BigDecimal LaborCostPerSquareFoot= new BigDecimal("4.15");
    BigDecimal materialCost= new BigDecimal("871.50");
    BigDecimal laborCost= new BigDecimal("1033.35");
    BigDecimal tax= new BigDecimal("476.21");
    BigDecimal total= new BigDecimal("2381.06");
    Order test= new Order(orderNumber, "Ada Lovelace", "California", taxRate, "Tile", area, CostPerSquareFoot, LaborCostPerSquareFoot, materialCost, laborCost, tax, total);   
    public OrderServiceLayerImplTest() {
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
    public void testCreateValidOrder () throws OrderDataValidationException, OrderPersistenceException {
       
    //ACT
        try{
            service.createOrder(date, test);
        } catch(OrderDataValidationException | OrderPersistenceException e ){
            fail("Order was valid. No exception should have been thrown.");
}
    }
    

    
    @Test
    public void testCreateOrderInvalidData() throws OrderDuplicateIdException, OrderDataValidationException{
        Order test2= new Order(orderNumber, "", "California", taxRate, "Tile", area, CostPerSquareFoot, LaborCostPerSquareFoot, materialCost, laborCost, tax, total);   
        try{
            service.createOrder(date, test2);
            fail("Expected ValidationException Execution was not thrown");
        } catch(OrderPersistenceException e){
            fail("Incorrect exception was thrown.");
        } catch(OrderDataValidationException e){
            return;
        }
    }
    
    @Test
    public void testCreateOrderPersistenceException() throws OrderDuplicateIdException, OrderDataValidationException{
        Order test2= new Order(orderNumber, "Ada LoveLace", "Vermont", taxRate, "Tile", area, CostPerSquareFoot, LaborCostPerSquareFoot, materialCost, laborCost, tax, total);   
        try{
            service.createOrder(date, test2);
            fail("Expected ValidationException Execution was not thrown");
        } catch(OrderDataValidationException e){
            fail("Incorrect exception was thrown.");
        } catch(OrderPersistenceException e){
            return;
        }
    }
    
    @Test
    public void testGetAllOrders() throws Exception{

        assertEquals(1, service.getAllOrders(date).size(), "Should only have one order");
        assertTrue(service.getAllOrders(date).contains(test), "The one card should be Order Number 1");
    }
    @Test
    public void testGetAllOrdersException() throws Exception{

        try{
            service.getAllOrders("2893");
            fail("OrderPersistenceException should have been thrown for a nonexistent order date");
        } catch (OrderPersistenceException e){
            return;
        }
    }
    
    @Test
    public void testGetOrder() throws Exception{
        
        //ACT and ASSERT
        Order shouldBeRay=service.getOrder(date, orderNumber);
        assertNotNull(shouldBeRay, "Getting Order Number 1 should not be null");
        assertEquals(test, shouldBeRay, "Order stored under OrderNumber 1 should be test");
        
        Order shouldBeNull=service.getOrder(date, 2);
        assertNull(shouldBeNull, "Getting Order Number 2 should be null.");
    }
    
    @Test
    public void testGetOrderException() throws Exception{
        try{
            service.getOrder("2893", 1);
            fail("OrderPersistenceException should have been thrown for a nonexistent order date");
        } catch (OrderPersistenceException e){
            return;
        }
    }
    
    @Test
    public void testRemoveOrder() throws Exception{
        //ACT AND ASSERT
        Order shouldBeRay= service.removeOrder(date, orderNumber);
        assertNotNull(shouldBeRay, "Removing Order Number 1 should not be null");
        assertEquals(test, shouldBeRay, "Order removed should be test.");
        
        Order shouldBeNull=service.removeOrder(date, 2);
        assertNull(shouldBeNull, "Removing Order Number 2 should be null");
    }
    
    @Test
    public void testRemoveOrderException() throws Exception{
        try{
            service.removeOrder("2893", 1);
            fail("OrderPersistenceException should have been thrown for a nonexistent order date");
        } catch (OrderPersistenceException e){
            return;
        }
    }
    
    
    @Test
    @SuppressWarnings("empty-statement")
    public void testUpdateOrder() throws Exception{
        String[] updatedParam= {"", "", "", ""};
        Order updatedOrder= service.updateOrder(date, orderNumber, updatedParam, true);
        assertEquals(updatedOrder.getOrderNumber(), 1, "This parameter should not have changed.");
        assertEquals(updatedOrder.getCustomerName(), "Ada Lovelace", "This parameter should not have changed.");
        assertEquals(updatedOrder.getState(), "California", "This parameter should not have changed.");
        assertEquals(updatedOrder.getProductType(), "Tile", "This parameter should not have changed.");
        assertEquals(updatedOrder.getTotal().toString(), "2381.06", "This parameter should not have changed.");
        
        String[] updatedParam2= {"", "Texas", "", ""};
        updatedOrder= service.updateOrder(date, orderNumber, updatedParam2, true);
        assertEquals(updatedOrder.getOrderNumber(), 1, "This parameter should not have changed.");
        assertEquals(updatedOrder.getCustomerName(), "Ada Lovelace", "This parameter should not have changed.");
        assertEquals(updatedOrder.getState(), "Texas", "This parameter should have changed.");
        assertEquals(updatedOrder.getProductType(), "Tile", "This parameter should not have changed.");
        assertNotEquals(updatedOrder.getTotal().toString(), "2381.06", "This parameter should have changed.");
        
        String[] updatedParam3= {"Ada", "Texas", "", ""};
        updatedOrder= service.updateOrder(date, orderNumber, updatedParam3, true);
        assertEquals(updatedOrder.getOrderNumber(), 1, "This parameter should not have changed.");
        assertEquals(updatedOrder.getCustomerName(), "Ada", "This parameter should have changed.");
        assertEquals(updatedOrder.getState(), "Texas", "This parameter should have changed.");
        assertEquals(updatedOrder.getProductType(), "Tile", "This parameter should not have changed.");
        assertNotEquals(updatedOrder.getTotal().toString(), "2381.06", "This parameter should have changed.");
        
        try{
            updatedOrder= service.updateOrder("2034", orderNumber, updatedParam3, true);
            fail("Exception should've been thrown.");
        } catch (OrderPersistenceException e){
            return;
        }
        
        updatedOrder= service.updateOrder(date, 3, updatedParam3, true);
        assertNull(updatedOrder, "updatedOrder should be null");
    }
    
    @Test
    public void testValidationFunctions() throws OrderPersistenceException{
        BigDecimal gottenTaxRate= service.findTaxRate("California");
        assertEquals(taxRate, gottenTaxRate);
        try{
            gottenTaxRate=service.findTaxRate("adfljlakds");
            fail("Should have returned an exception for a state that's not on file.");
        } catch (OrderPersistenceException e){
            return;
        }
        
        String gottenAbbreviation= service.getAbbreviation("California");
        assertEquals("CA", gottenAbbreviation);
        try{
            gottenAbbreviation= service.getAbbreviation("242454");;
            fail("Should have returned an exception for a state that's not on file.");
        } catch (OrderPersistenceException e){
            return;
        }
        
        String gottenFullStateName= service.getFullStateName("CA");
        assertEquals("California", gottenFullStateName);
        try{
            gottenFullStateName= service.getFullStateName("242454");;
            fail("Should have returned an exception for a state that's not on file.");
        } catch (OrderPersistenceException e){
            return;
        }
        
        BigDecimal gottenCost= service.getCostPerSquareFoot("Tile");
        assertEquals(CostPerSquareFoot, gottenCost);
        try{
            gottenCost=service.getCostPerSquareFoot("adfljlakds");
            fail("Should have returned an exception for a productType that's not on file.");
        } catch (OrderPersistenceException e){
            return;
        }
        
        BigDecimal gottenLaborCost= service.getLaborCostPerSquareFoot("Tile");
        assertEquals(LaborCostPerSquareFoot, gottenCost);
        try{
            gottenLaborCost=service.getCostPerSquareFoot("adfljlakds");
            fail("Should have returned an exception for a productType that's not on file.");
        } catch (OrderPersistenceException e){
            return;
        }
    }
    
}
