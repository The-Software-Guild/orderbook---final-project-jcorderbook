/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Card;
import DTO.Card.Element;
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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import s.S;

/**
 *
 * @author recyn
 */
public class CardDaoImpl implements CardDaoInterface {
    public final String COLLECTION_FILE;
    public static final String DELIMITER = "::";
  
    
    public CardDaoImpl(){
        COLLECTION_FILE="collection.txt";
    }
    public CardDaoImpl(String textFile){
        COLLECTION_FILE=textFile;
    }
    public Map<String, Card> cards = new HashMap<>();
    
    
    @Override
    public Card addCard(Card card) throws CardPersistenceException {
        loadCollection();
        Card newCard= cards.put(card.getName(), card);
        try {
            writeCollection();
        } catch (IOException ex) {
            Logger.getLogger(CardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newCard;
    }

    @Override
    public Card getCard(String name) throws CardPersistenceException {
        loadCollection();
       return cards.get(name);
    }

    @Override
    public List<Card> getAllCards()  throws CardPersistenceException{
        loadCollection();
        return new ArrayList<>(cards.values());
    }
    //Ended up not using this. For editing, the program just makes a new card and adds it in the service layer. 
    @Override
    public Card editCard(String name, int hp, String stage, String attack, Card.Element element) throws CardPersistenceException {
        loadCollection();
        Card newCard= new Card(name, hp, stage, attack, element);
        Card output= cards.replace(name, newCard);
        try {
            writeCollection();
        } catch (IOException ex) {
            Logger.getLogger(CardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    @Override
    public Card removeCard(String name)  throws CardPersistenceException{
        loadCollection();
        Card removed= cards.remove(name);
        try {
            writeCollection();
        } catch (IOException ex) {
            Logger.getLogger(CardDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return removed;
    }
    private Card unmarshallCard(String CardAsText){
        String[] CardTokens= CardAsText.split(DELIMITER);
        String name=CardTokens[0];
        Card CardFromFile= new Card(name, S.integize(CardTokens[1]), CardTokens[2], CardTokens[3], Element.valueOf(CardTokens[4]));

        return CardFromFile;
    }
    
    private String marshallCard(Card aCard){
        
        String CardAsText=aCard.getName()+DELIMITER;
        
        CardAsText += aCard.getHp()+DELIMITER;
        
        CardAsText += aCard.getStage()+DELIMITER;
        
        CardAsText += aCard.getAttack()+DELIMITER;
        
        CardAsText += aCard.getElement().toString();
        
        return CardAsText;
    }
    //writes to the text file using information from the map
    private void writeCollection() throws CardPersistenceException, IOException{
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(COLLECTION_FILE));
        } catch (Exception e){
            throw new CardPersistenceException("Could not load collection from memory.", e);
        }
        
        Set<String> keys= cards.keySet();
        for (String k:keys){
            String CardAsText= marshallCard(cards.get(k));
            out.println(CardAsText);
        }
        
        out.flush();
        out.close();
        
    }
    
    //creates a new map using information from the text file
    private void loadCollection() throws CardPersistenceException {
        Scanner scanner;
        
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(COLLECTION_FILE)));
        } catch (FileNotFoundException e){
            throw new CardPersistenceException("Could not load collection data into memory", e);
        }
        String currentLine;
        Card currentCard;
        while (scanner.hasNextLine()){
            currentLine=scanner.nextLine();
            currentCard=unmarshallCard(currentLine);
            cards.put(currentCard.getName(), currentCard);
        }
        scanner.close();
    }
    //for testing purposes
    @Override
    public Map<String, Card> getMap(){
        return cards;
    }
}
