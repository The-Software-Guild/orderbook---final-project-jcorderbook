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
public class DTODataValidationException extends Exception {
    
    public DTODataValidationException(String message){
        super(message);
    }
    public DTODataValidationException(String message, Throwable cause){
        super(message, cause);
    }
}
