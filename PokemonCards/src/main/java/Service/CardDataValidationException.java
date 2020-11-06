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
public class CardDataValidationException extends Exception {
    
    public CardDataValidationException(String message){
        super(message);
    }
    public CardDataValidationException(String message, Throwable cause){
        super(message, cause);
    }
}
