/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine;

import com.sg.VendingMachine.controller.VendingController;
import com.sg.VendingMachine.dao.ItemDao;
import com.sg.VendingMachine.dao.VendingMachineAuditDao;
import com.sg.VendingMachine.dao.VendingMachinePersistenceException;
import com.sg.VendingMachine.service.VendingMachineAuditFileImpl;
import com.sg.VendingMachine.service.VendingMachineServiceLayer;
import com.sg.VendingMachine.service.VendingMachineServiceLayerImpl;
import com.sg.VendingMachine.ui.UserIOConsoleImpl;
import com.sg.VendingMachine.ui.VendingMachineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 *
 * @author recyn
 */
public class App {
   public static void main(String[] args) throws VendingMachinePersistenceException, Exception{
       // this instantiates instances of both the DAO and the View, which uses a specific implementation of the UserIO interface
       /*VendingMachineAuditDao auditDao= new VendingMachineAuditFileImpl();
       VendingMachineServiceLayer service= new VendingMachineServiceLayerImpl(new ItemDao(), auditDao);
       VendingMachineView view= new VendingMachineView(new UserIOConsoleImpl());
       VendingController controller = new VendingController(service, view);
       //controller.restock();
       controller.run();
*/
       ApplicationContext ctx = 
           new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingController controller = 
           ctx.getBean("controller", VendingController.class);
        controller.run();
   } 
}
