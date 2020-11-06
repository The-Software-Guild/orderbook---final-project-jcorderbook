/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvd;

import com.sg.dvd.controller.DVDController;
import com.sg.dvd.dao.DVDDaoFileImpl;
import com.sg.dvd.ui.DVDView;
import com.sg.dvd.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author recyn
 */
public class App {
   public static void main(String[] args){
       // this instantiates instances of both the DAO and the View, which uses a specific implementation of the UserIO interface
       /*DVDController controller = new DVDController(new DVDDaoFileImpl(), new DVDView(new UserIOConsoleImpl()));
       controller.run();
*/
       ApplicationContext ctx= new ClassPathXmlApplicationContext("applicationContext.xml");
            DVDController controller= ctx.getBean("controller", DVDController.class);
            controller.run();
   } 
}
