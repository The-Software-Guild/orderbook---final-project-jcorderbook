/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.CardDaoInterface;
import DAO.CardPersistenceException;
import DTO.Card;
import static DTO.Card.Element.NORMAL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author recyn
 */
public class CardDaoStub implements CardDaoInterface {
    Map<String, Card> cards= new HashMap<>();

    public CardDaoStub(){
        Card test= new Card("Rayquaza EX", 170, "Basic", "Dragon Pulse", NORMAL );
        cards.put(test.getName(), test);
    }
    @Override
    public Card addCard(Card card) throws CardPersistenceException {
        if (card.getName().equals("Rayquaza EX")){
            return cards.get("Rayquaza EX");
        }
        return null;
    }

    @Override
    public List<Card> getAllCards() throws CardPersistenceException {
        List<Card> output= new ArrayList();
        Card test= new Card("Rayquaza EX", 170, "Basic", "Dragon Pulse", NORMAL );
        output.add(test);
        return output; 
    }

    @Override
    public Card getCard(String name) throws CardPersistenceException {
        if (name.equals("Rayquaza EX")){
            return cards.get("Rayquaza EX");
        }
        return null;
    }

    @Override
    public Card removeCard(String name) throws CardPersistenceException {
        if (name.equals("Rayquaza EX")){
            return cards.get("Rayquaza EX");
        }
        return null;
    }

    @Override
    public Card editCard(String name, int hp, String stage, String attack, Card.Element element) throws CardPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Card> getMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
