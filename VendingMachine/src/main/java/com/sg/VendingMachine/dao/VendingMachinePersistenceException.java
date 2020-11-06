/**
 * 
 */
package com.sg.VendingMachine.dao;



//For problems arising from the ItemDao
public class VendingMachinePersistenceException extends Exception {
	
	public VendingMachinePersistenceException(String message) {
		super(message);
	}
	
	public VendingMachinePersistenceException(String message, Throwable cause) {
		super(message, cause);
	}
}
