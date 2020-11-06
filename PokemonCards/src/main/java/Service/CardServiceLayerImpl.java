/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.CardAuditDao;
import DAO.CardAuditDaoFileImpl;
import DAO.CardDaoInterface;
import DAO.CardPersistenceException;
import DTO.Card;
import DTO.Card.Element;


import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author recyn
 */
public class CardServiceLayerImpl implements CardServiceLayer {
    CardDaoInterface dao;
    private CardAuditDao auditDao;
    public CardServiceLayerImpl(CardDaoInterface dao, CardAuditDao auditDao){
        this.dao=dao;
        this.auditDao=auditDao;
    }
    
    //Adds a card to the DAO and makes sure the added card doesn't already exist and has valid parameters. 
    @Override
    public void createCard(Card card) throws CardDuplicateIdException, CardDataValidationException, CardPersistenceException {

        
        if (dao.getCard(card.getName()) != null){
            throw new CardDuplicateIdException("Error: Could not Create card. Card Name "
            + card.getName()
            + " already exists");
        }
        
        //Now validate all the fields on the given Card object.
        //This method will throw an exception if any of the validation rtules are violated.
        validateCardData(card);
        
        //We passed all our business rules checks so go ahead and persist the card object
        dao.addCard(card);
        //Tje stidemt was successfully created, now write to the audit log
        auditDao.writeAuditEntry("Card "+card.getName()+" CREATED.");
    }

    @Override
    public List<Card> getAllCards() throws CardPersistenceException {
        return dao.getAllCards();
    }

    @Override
    public Card getCard(String name) throws CardPersistenceException {
        return dao.getCard(name);
    }
    //Removes a card and will return the removed card if it exists. If not, it returns null. 
    @Override
    public Card removeCard(String name) throws CardPersistenceException {
        Card removedCard=null;
        removedCard=dao.removeCard(name);
        
        //Write to audit log
        auditDao.writeAuditEntry("Card " +name + " REMOVED");
        
        return removedCard;
    }
    //Checks to see if a card has values for all its member fields, except for Hp, because Hp is an int and cannot be null. 
    //Any error with the Hp would be caught in either the view or controller when converting a String input to an integer. 
    //Ideally this would also check whether or not the fields are also empty Strings. 
    private void validateCardData(Card card) throws CardDataValidationException, CardDuplicateIdException, CardPersistenceException{
        if (card.getName()==null
                || card.getName()==null
                || card.getElement()==null
                || card.getStage()==null
                || card.getAttack()==null){
            throw new CardDataValidationException("Error: All fields [Name, HP, Stage, Attack, Element] are required.");
        }
    }
    
    //Returns a list of cards of a certain input element, which is an enum. 
    @Override
    public List<Card> getCardsOfType(Element element) throws CardPersistenceException{
        List<Card> allCards= dao.getAllCards();
        List<Card> allCardsOfType= allCards.stream()
                .filter((c)-> c.getElement()== element)
                .collect(Collectors.toList());
        return allCardsOfType;
    }
    //Given a cardName that definitely exists in the dao already, this extracts that card and updates the parameters if the updated parameter is not an empty String.
    //If the name has to be updated, then the original card is removed before the updatedCard is added to the dao. 
    @Override
    public Card updateCard(String cardName, String[] updatedParam) throws CardPersistenceException {
            Card updatedCard= dao.getCard(cardName);
        if (!updatedParam[0].equals("")){
            updatedCard.setName(updatedParam[0]);
            dao.removeCard(cardName);
        }
        if (!updatedParam[1].equals("")){
            updatedCard.setHp(Integer.parseInt(updatedParam[1]));
        }
        if (!updatedParam[2].equals("")){
            updatedCard.setStage(updatedParam[2]);
        }
        if (!updatedParam[3].equals("")){
            updatedCard.setAttack(updatedParam[3]);
        }
        if (!updatedParam[4].equals("")){
            updatedCard.setElement(Element.valueOf(updatedParam[4]));
        }
        dao.addCard(updatedCard);
        return updatedCard;
    }
    
    
}
