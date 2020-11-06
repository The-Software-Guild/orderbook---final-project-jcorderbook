/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddressUI;

import AddressDTO.Address;
import java.util.List;

/**
 *
 * @author recyn
 */
public class AddressView {
    UserIO io = new UserIOConsoleImpl();
    
    public int Menu(){
        io.print("1) Add an Address\n" +
    "2) Remove an Address\n" +
    "3) Find an Address by last name\n" +
    "4) Count of Addresses in Addressbook\n" +
    "5) View all Addresses\n" +
    "6) Edit an Address\n"+
                "7) Exit\n");
     return io.readInt("Enter the number corresponding to your choice between:", 1, 7);   
    }
    public void displayAddAddressBanner(){
        io.print("=== Add Address ===");
    }
    
    public void displayAddSuccessful(){
        io.print("=== Successfully Added ===");
    }
    
    public void displayRemoveAddressBanner(){
        io.print("=== Remove Address ===");
    }
    
    public void displayRemoveSuccessful(){
        io.print("=== Successfully Removed ===");
    }
    
    public void displayEditAddressBanner(){
        io.print("=== Add Address ===");
    }
    
    public void displayEditSuccessful(){
        io.print("=== Successfully Edited ===");
    }
    
    public void displayViewAllAddressBanner(){
        io.print("=== View All Addresses ===");
    }
    
    public void displayViewAddressBanner(){
        io.print("=== View Address ===");
    }
    
    public void displayCountAddresses(){
        io.print("=== Address Count ===");
    }
    
    public void goodbyeMessage(){
        io.print("Goodbye!");
    }
    
    public Address addAddress(){
        String firstname= io.readString("What is the first name of the owner of the address?");
        String lastname= io.readString("What is the last name of the owner of the address?");
        String street= io.readString("What is the full address?");
        Address aAddress= new Address(firstname, lastname, street);
        return aAddress;
    }
    
    public String removeAddress(){
        String lastname= io.readString("What is the last name of the owner of the address?");
        return lastname;
    }
    
    public void displayRemoveResult(Address address){
        if (address != null){
            io.print("Address successfully removed.");
        }
        else{
            io.print("No such Address.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public String[] editAddress(){
        String firstname= io.readString("What is the new first name? Or press Enter if it's the same.");
        String lastname= io.readString("What is the new last name? Or press Enter if it's the same.");
        String street= io.readString("What is the new address? Or press Enter if it's the same.");
        String[] fields= {firstname, lastname, street};
        return fields;
    }
    
   public void viewAddress(Address address){
       io.print(address.getFirstName()+" "+address.getLastName()+"\n");
       io.print(address.getStreet()+"\n");
   } 
   
   public void viewAllAddresses(List<Address> addressList){
       for (Address address:addressList){
           io.print(address.getFirstName()+" "+address.getLastName()+"\n");
           io.print(address.getStreet()+"\n");
       }
   }
   public String getAddressChoice(){
        return io.readString("Please enter the last name of the address owner");
    }
   public void displayAddressCount(int count){
       io.print("There are "+count+" addresses in the address book.");
   }
   public void displayInvalidInputBanner(){
        io.print("Unknown Command!! \n");
    }
}
