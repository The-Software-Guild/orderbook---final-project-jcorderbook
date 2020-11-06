/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Card;
import DTO.Card.Element;
import java.util.List;
import java.util.Map;

/**
 *
 * @author recyn
 */
public interface CardDaoInterface {

    Card addCard(Card card) throws CardPersistenceException;
    
    List<Card> getAllCards() throws CardPersistenceException;
    
    Card getCard (String name) throws CardPersistenceException;
    
    Card removeCard(String name) throws CardPersistenceException;
    
    Card editCard(String name, int hp, String stage, String attack, Element element) throws CardPersistenceException;
    
    Map<String, Card> getMap();

}
