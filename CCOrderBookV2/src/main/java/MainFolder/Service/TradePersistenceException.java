/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.Service;

/**
 *
 * @author recyn
 */
public class TradePersistenceException extends Exception {
    public TradePersistenceException(String message){
        super(message);
    }
    public TradePersistenceException(String message, Throwable cause){
        super(message, cause);
    }
}
