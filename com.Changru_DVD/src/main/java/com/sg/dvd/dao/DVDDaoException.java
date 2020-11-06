/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvd.dao;

/**
 *
 * @author recyn
 */
public class DVDDaoException extends Exception{
    public DVDDaoException(String message){
        super(message);
    }
    
    public DVDDaoException(String message, Throwable cause){
        super(message, cause);
    }
}
