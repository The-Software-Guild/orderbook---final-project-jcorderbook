/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

/**
 *
 * @author recyn
 */
public class CardDuplicateIdException extends Exception{
    public CardDuplicateIdException(String message){
        super(message);
    }
    public CardDuplicateIdException(String message, Throwable cause){
        super(message, cause);
    }
}
