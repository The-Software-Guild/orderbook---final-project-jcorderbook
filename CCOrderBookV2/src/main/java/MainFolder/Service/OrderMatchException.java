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
public class OrderMatchException extends Exception{
    public OrderMatchException(String message){
        super(message);
    }
    public OrderMatchException(String message, Throwable cause){
        super(message, cause);
    }
}
