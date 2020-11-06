/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.CardAuditDao;
import DAO.CardDaoImpl;
import DAO.CardDaoInterface;
import DAO.CardPersistenceException;
import DTO.Card;
import static DTO.Card.Element.NORMAL;
import static DTO.Card.Element.PSYCHIC;
import static DTO.Card.Element.WATER;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author recyn
 */
public class CardServiceLayerImplTest{
    CardDaoInterface dao= new CardDaoStub();
    CardAuditDao audit= new CardAuditFileStub();
    CardServiceLayer service= new CardServiceLayerImpl(dao, audit); 
    public CardServiceLayerImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreateValidCard () throws CardDataValidationException, CardPersistenceException {
        //ARRANGE
       Card test2= new Card("Gardevoir EX", 150, "Level 2", "Psystorm", PSYCHIC );
       
    //ACT
        try{
            service.createCard(test2);
        } catch(CardDuplicateIdException | CardDataValidationException | CardPersistenceException e ){
            fail("Card was valid. No exception should have been thrown.");
}
    }
    @Test
    public void testCreateDuplicateIdCard(){
        //Arrange
        Card test= new Card("Rayquaza EX", 170, "Basic", "Dragon Pulse", NORMAL );
        
        //Act
        
        try{
            service.createCard(test);
            fail("Expected DupeId Execution was not thrown");
        } catch(CardDataValidationException | CardPersistenceException e){
            fail("Incorrect exception was thrown.");
        } catch(CardDuplicateIdException e){
            return;
        }
    }

    
    @Test
    public void testCreateCardInvalidData() throws CardDuplicateIdException, CardDataValidationException{
        //Arrange
        Card test2= new Card("Gardevoir EX", 150, null, "Psystorm", PSYCHIC );
        //Act
        
        try{
            service.createCard(test2);
            fail("Expected ValidationException Execution was not thrown");
        } catch(CardDuplicateIdException | CardPersistenceException e){
            fail("Incorrect exception was thrown.");
        } catch(CardDataValidationException e){
            return;
        }
    }
    @Test
    public void testGetAllCards() throws Exception{
        //ARRANGE
        Card testClone= new Card("Rayquaza EX", 170, "Basic", "Dragon Pulse", NORMAL );
        
        //ACT and ASSERT

        assertEquals(1, service.getAllCards().size(), "Should only have one card");
        assertTrue(service.getAllCards().contains(testClone), "The one card should be Ada");
    }
    @Test
    public void testGetCard() throws Exception{
        //ARRANGE
        Card testClone= new Card("Rayquaza EX", 170, "Basic", "Dragon Pulse", NORMAL );
        
        //ACT and ASSERT
        Card shouldBeRay=service.getCard("Rayquaza EX");
        assertNotNull(shouldBeRay, "Getting Rayquaza should not be null");
        assertEquals(testClone, shouldBeRay, "Card stored under Rayquaza EX should be Rayquaza EX");
        
        Card shouldBeNull=service.getCard("0042");
        assertNull(shouldBeNull, "Getting 0042 should be null.");
    }
    @Test
    public void testRemoveCard() throws Exception{
        //ARRANGE
        Card testClone= new Card("Rayquaza EX", 170, "Basic", "Dragon Pulse", NORMAL );
        
        //ACT AND ASSERT
        Card shouldBeRay= service.removeCard("Rayquaza EX");
        assertNotNull(shouldBeRay, "Removing Rayquaza EX should not be null");
        assertEquals(testClone, shouldBeRay, "Card removed from Rayquaza EX should be Rayquaza EX.");
        
        Card shouldBeNull=service.removeCard("0042");
        assertNull(shouldBeNull, "Removing 0042 should be null");
    }
    @Test
    @SuppressWarnings("empty-statement")
    public void testUpdateCard() throws Exception{
        String[] updatedParam= {"", "", "", "", ""};
        String name= "Rayquaza EX";
        Card updatedCard= service.updateCard(name, updatedParam);
        assertNotEquals(updatedCard.getName(), "", "This parameter should not have changed.");
        assertNotEquals(updatedCard.getHp(), "", "This parameter should not have changed.");
        assertNotEquals(updatedCard.getStage(), "", "This parameter should not have changed.");
        assertNotEquals(updatedCard.getAttack(), "", "This parameter should not have changed.");
        assertNotEquals(updatedCard.getElement(), "", "This parameter should not have changed.");
        
        String[] updatedParam2= {"", "180", "", "", "WATER"};
        updatedCard= service.updateCard(name, updatedParam2);
        assertNotEquals(updatedCard.getName(), "", "This parameter should not have changed.");
        assertEquals(180, updatedCard.getHp(), "This parameter should have changed to 180");
        assertNotEquals(updatedCard.getStage(), "", "This parameter should not have changed.");
        assertNotEquals(updatedCard.getAttack(), "", "This parameter should not have changed.");
        assertEquals(updatedCard.getElement(), WATER, "This parameter should have changed.");
        
        String[] updatedParam3= {"Rayquaza", "170", "", "", "NORMAL"};
        updatedCard= service.updateCard(name, updatedParam3);
        assertEquals(updatedCard.getName(), "Rayquaza", "This parameter should have changed.");
        assertEquals(170, updatedCard.getHp(), "This parameter should have changed to 170.");
        assertNotEquals(updatedCard.getStage(), "", "This parameter should not have changed.");
        assertNotEquals(updatedCard.getAttack(), "", "This parameter should not have changed.");
        assertEquals(updatedCard.getElement(), NORMAL, "This parameter should have changed.");
    }
    
}
