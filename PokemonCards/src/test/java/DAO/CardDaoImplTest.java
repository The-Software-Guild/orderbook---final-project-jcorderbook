/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Card;
import static DTO.Card.Element.NORMAL;
import static DTO.Card.Element.PSYCHIC;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author recyn
 */
public class CardDaoImplTest {
    static CardDaoInterface dao;
    public CardDaoImplTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() throws IOException {

    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @BeforeEach
    @Test
    public void setUp() throws IOException {
        String testFile="textroster.txt";
        //use the FileWriter to quicklky blank the file
        new FileWriter(testFile);
        dao=new CardDaoImpl(testFile);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void addGetCardTest()throws Exception {
        String testFile="textroster.txt";
        //use the FileWriter to quicklky blank the file
        new FileWriter(testFile);
        dao=new CardDaoImpl(testFile);
        Card test= new Card("Rayquaza EX", 170, "Basic", "Dragon Pulse", NORMAL );
        Card result= dao.addCard(test);
        
        assertNull(result, "Supposed to be null");
        String name="Rayquaza EX";
        Card getCard= dao.getCard(name);
        
        assertEquals(test, getCard, "Gotten monster should be the same");
        Card result2= dao.addCard(test);
        assertEquals(test, result2, "if you add something that's already there it should return the same value");
        List<Card> cardList= dao.getAllCards();
        assertEquals(1, cardList.size(),"");
    }
    @Test
    public void GetTest() throws Exception{
        String testFile="textroster.txt";
        //use the FileWriter to quicklky blank the file
        new FileWriter(testFile);
        dao=new CardDaoImpl(testFile);
        Card test= new Card("Rayquaza EX", 170, "Basic", "Dragon Pulse", NORMAL );
        Card result= dao.addCard(test);
        
        assertNull(result, "Supposed to be null");
        String name="Rayquaza EX";
        Card getCard= dao.getCard(name);
        
        assertEquals(test, getCard, "Gotten monster should be the same");
        List<Card> cardList= dao.getAllCards();
        assertEquals(1, cardList.size(),"The get function should not do anything to the size of the list");
    }
    
    @Test
    public void GetTest2() throws Exception {
        String testFile="textroster.txt";
        //use the FileWriter to quicklky blank the file
        new FileWriter(testFile);
        dao=new CardDaoImpl(testFile);
        Card test= new Card("Rayquaza EX", 170, "Basic", "Dragon Pulse", NORMAL );
        Card result= dao.addCard(test);
        
        assertNull(result, "Supposed to be null");
        String name="Rayquaza EX";
        Card getCard= dao.getCard(name);
        
        assertEquals(test, getCard, "Gotten monster should be the same");
        List<Card> cardList= dao.getAllCards();
        for (Card m:cardList){
            System.out.print(m);
        }
        assertEquals(test, cardList.get(0),"The get function should not do anything to the contents of the list");
    } 
    
    @Test
    public void testNoAddAgain() throws Exception{
        String testFile="textroster.txt";
        //use the FileWriter to quicklky blank the file
        new FileWriter(testFile);
        dao=new CardDaoImpl(testFile);
        //Map<String, Card> cards = new HashMap<>();
        Map<String, Card> cards= dao.getMap();
        Card test= new Card("Rayquaza EX", 170, "Basic", "Dragon Pulse", NORMAL );
        Card result= dao.addCard(test);
        List<Card> cardList= dao.getAllCards();
        assertEquals(1, cardList.size(),"List should be 1 after adding one entry");
        
        Card test2= new Card("Gardevoir EX", 150, "Level 2", "Psystorm", PSYCHIC );
        Card result2= dao.addCard(test);
        cards=dao.getMap();
        cardList= dao.getAllCards();
        System.out.print("SIZE OF LIST IS " +cardList.size());
        //System.out.print(dao.getCard("Gardevoir EX").getName());
        //System.out.print(dao.getCard("Rayquaza EX").getName());
        assertEquals(1, cardList.size(),"Adding again shouldn't increase the size of the list");
        assertEquals(test, cardList.get(0), "Adding again should replace the value if the keys are the same");
    }
 

     @Test
  public void getAllCardsTest() throws Exception {
      String testFile="textroster.txt";
        //use the FileWriter to quicklky blank the file
        new FileWriter(testFile);
        dao=new CardDaoImpl(testFile);
      Card test= new Card("Rayquaza EX", 170, "Basic", "Dragon Pulse", NORMAL );
      Card result= dao.addCard(test);
      
      List<Card> cardList= dao.getAllCards();
      
      assertEquals(1, cardList.size(), "Size should be 1");
      assertEquals(test, cardList.get(0), "Only entry should be test");
      
      Card test2= new Card("Gardevoir EX", 150, "Level 2", "Psystorm", PSYCHIC );
      Card result2= dao.addCard(test2);
      cardList= dao.getAllCards();
      assertEquals(2, cardList.size(), "Size at first was 1, as expected, now size should be 2");
      assertTrue(cardList.contains(test), "Entry should be test");
      assertTrue(cardList.contains(test2), "Entry should be test2");
      
      
  }
    
  /*
  @Test
  public void updateCardWithGet(){
      Card test= new Card("Sullivan", YETI, 0, "Cheerios");
      dao.addCard(1, test);
      
      Card test2 = new Card("Mike Wazoski", LIZARDMAN, 0, "Cheerios");
      dao.addCard(2, test2);
      
      Card test3 = new Card("Tenma", LIZARDMAN, 0, "Honey Nut Cheerios");
      
      dao.updateCard(1, test3);
      
      assertEquals(test3, dao.getCard(1), "Entry should be test3");
      
      dao.updateCard(3, test3);
      
      assertNull(dao.getCard(3), "Should be no such thing");
      
  }*/
  /*
  @Test
  public void updateCardWithList(){
      Card test= new Card("Sullivan", YETI, 0, "Cheerios");
      dao.addCard(1, test);
      
      Card test2= new Card("Gardevoir EX", 150, "Level 2", "Psystorm", PSYCHIC );
        Card result2= dao.addCard("Gardevoir EX", test);
      
      Card test3 = new Card("Tenma", LIZARDMAN, 0, "Honey Nut Cheerios");
      
      dao.updateCard(1, test3);
      List<Card> cardList= dao.getAllCards();
      
      assertEquals(2, cardList.size(), "CardList size should be 2");
      
      assertTrue(cardList.contains(test3), "Entry should be test3");
      
      dao.updateCard(3, test3);
      
      
      assertNull(dao.getCard(3), "Should be no such thing");
      
  }*/
  
  @Test
  public void removeCard() throws Exception {
      String testFile="textroster.txt";
        //use the FileWriter to quicklky blank the file
        new FileWriter(testFile);
        dao=new CardDaoImpl(testFile);
      Card test2= new Card("Gardevoir EX", 150, "Level 2", "Psystorm", PSYCHIC );
      Card result2= dao.addCard(test2);
      Card removed= dao.removeCard("Gardevoir EX");
      assertEquals(test2, removed, "Entry should be test3");
      assertNull(dao.getCard("Gardevoir EX"), "The removed entry should no longer be there");
      
      Card removed2= dao.removeCard("Gardevoir EX");
      assertNull(removed2, "You can't remove something that theoretically we already removed");
  }
    
  /*
  @Test
  public void testKeySet() throws Exception{
      Card test= new Card("Rayquaza EX", 170, "Basic", "Dragon Pulse", NORMAL );
      Card result= dao.addCard("Rayquaza EX", test);
      Set<String> keySet= dao.cards.keySet();
      int id=1;
      for (int i:keySet){
          System.out.print(i+"\nHELLOOOOOOOOOOOOOOOOOOOOOOOOOO\n");
      }
      assertEquals(1, keySet.size(),"Size of keySet should be 1");
      assertTrue(keySet.contains(id), "The only key should be id 1");
      
  }
   public void testKeySetAfterGet(){
      Card test= new Card("Sullivan", YETI, 0, "Cheerios");
      dao.addCard(1, test);
      dao.getCard(1);
      Set<Integer> keySet= dao.monsters.keySet();
      int id=1;
      for (int i:keySet){
          System.out.print(i+"\nHELLOOOOOOOOOOOOOOOOOOOOOOOOOO\n");
      }
      assertEquals(1, keySet.size(),"Size of keySet should be 1");
      assertTrue(keySet.contains(id), "The only key should be id 1");
      
  }
    
    */
    
}
