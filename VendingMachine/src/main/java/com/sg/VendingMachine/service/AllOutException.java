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

//For when the item count is 0. 
public class AllOutException extends Exception {

    public AllOutException(String message) {
        super(message);
    }
    
}
