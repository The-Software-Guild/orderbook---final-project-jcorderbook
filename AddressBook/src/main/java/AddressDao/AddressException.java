/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddressDao;

/**
 *
 * @author recyn
 */
public class AddressException extends Exception{
    public AddressException(String message){
        super(message); 
    }
    public AddressException(String message, Throwable cause){
        super(message);
    }
}
