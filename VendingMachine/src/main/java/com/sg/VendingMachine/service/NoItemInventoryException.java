/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.service;

/**
 *
 * @author recyn
 */

//For when the user doesn't input enough money to buy the item they selected. 
public class NoItemInventoryException extends Exception {
    public NoItemInventoryException(String message) {
		super(message);
	}
	
	public NoItemInventoryException(String message, Throwable cause) {
		super(message, cause);
	}
        public NoItemInventoryException(){
            
        }
}
