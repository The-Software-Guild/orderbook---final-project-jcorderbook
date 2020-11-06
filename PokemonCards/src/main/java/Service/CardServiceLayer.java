/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.CardPersistenceException;
import DTO.Card;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author recyn
 */
public interface CardServiceLayer {
    void createCard(Card card) throws
            CardDuplicateIdException,
            CardDataValidationException,
            CardPersistenceException;
    
    List<Card> getAllCards() throws
            CardPersistenceException;
    
    Card getCard(String name) throws
            CardPersistenceException;
    Card removeCard(String name) throws
            CardPersistenceException;
    List<Card> getCardsOfType(Card.Element element) throws CardPersistenceException;
    
    Card updateCard(String cardName, String[] updatedParam) throws CardPersistenceException;
}
