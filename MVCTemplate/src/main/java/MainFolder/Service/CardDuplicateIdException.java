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
public class DTODuplicateIdException extends Exception{
    public DTODuplicateIdException(String message){
        super(message);
    }
    public DTODuplicateIdException(String message, Throwable cause){
        super(message, cause);
    }
}
