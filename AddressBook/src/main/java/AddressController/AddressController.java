/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddressController;

import AddressDTO.Address;
import AddressDao.AddressBookImpl;
import AddressDao.AddressBookInterface;
import AddressDao.AddressException;
import AddressUI.AddressView;
import java.util.List;

/**
 *
 * @author recyn
 */
public class AddressController {
    // declare AddressBook class
    AddressBookInterface dao= new AddressBookImpl();
    //Initialize view class
    AddressView view= new AddressView();
    
//initiate while loop that keeps repeating the menu selection after catching any errors that arise from either selecting an option from the menu
//or one of the called functions. It will not specify the type of exception caught, however. 
    
    public void run() throws AddressException{
        boolean keepGoing= true;
        
    while (keepGoing){
        int choice = 0;

        try{
            choice= view.Menu();
        
            switch (choice){
                case 1:
                    addAddress();
                    break;
                case 2:
                    removeAddress();
                    break;
                case 3:
                    findAddress();
                    break;
                case 4:
                    countAddress();
                    break;
                case 5:
                    viewAllAddresses();
                    break;
                case 6:
                    editAddress();
                    break;
                case 7:
                    keepGoing=false;
                    break;
                        }
            } catch (Exception e){
                view.displayInvalidInputBanner();
            }
        
    }
    view.goodbyeMessage();
    
    //print menu
    
    //get user response
    
    //1) Add an Address
    //2) Remove an Address
    //3) Find an address by last name
    //4) Count of addresses in addressbook
    //5) View all addresses
    //6) Edit address
    
    //end while loop
    //print goodbye message
    
    }

    private void addAddress() throws AddressException {
        view.displayAddAddressBanner();
        
        Address newAddress=view.addAddress();
        dao.addAddress(newAddress);
        view.displayAddSuccessful();
    }

    private void removeAddress() throws AddressException {
        view.displayRemoveAddressBanner();
        String lastname=view.removeAddress();
        Address removed= dao.removeAddress(lastname);
        if (removed == null){
            view.displayInvalidInputBanner();
            
        }
        else {
            view.displayRemoveSuccessful();
        }
    }
    
    private void findAddress() throws AddressException {
        view.displayViewAddressBanner();
        String lastname=view.getAddressChoice();
        
        if (dao.getAddress(lastname)==null){
            view.displayInvalidInputBanner();
            
        } else{
            view.viewAddress(dao.getAddress(lastname));
        }
    }

    private void countAddress() throws AddressException {
        view.displayCountAddresses();
        int count= dao.countAddresses();
        view.displayAddressCount(count);
        
    }

    private void viewAllAddresses() throws AddressException {
        view.displayViewAllAddressBanner();
        List<Address> list= dao.listAllAddresses();
        view.viewAllAddresses(list);
    }

    private void editAddress() throws AddressException {
        view.displayEditAddressBanner();
        String lastname=view.getAddressChoice();
        String[] fields=view.editAddress();
        Address editedAddress=dao.editAddress(lastname, fields);
        view.viewAddress(editedAddress);
        view.displayEditSuccessful();
    }
    
}
