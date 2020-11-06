/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.ui;

/**
 *
 * @author recyn
 */

//For when user inputs an invalid input for the menu choice. 
public class InvalidSelectionException extends Exception {
    public InvalidSelectionException(String message){
        super(message);
}
}
