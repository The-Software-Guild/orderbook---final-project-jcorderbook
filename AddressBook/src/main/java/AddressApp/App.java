/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddressApp;

import AddressController.AddressController;
import AddressDao.AddressException;

/**
 *
 * @author recyn
 */
public class App {
    public static void main(String[] ars) throws AddressException{
        AddressController controller= new AddressController();
        controller.run();
    }
}
