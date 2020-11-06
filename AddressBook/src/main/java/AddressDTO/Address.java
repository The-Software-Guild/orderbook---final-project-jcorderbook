/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddressDTO;

/**
 *
 * @author recyn
 */
public class Address {
    private String firstName;
    private String lastName;
    private String street;
    
    
    public Address(){
        
    }
    
    public Address(String firstname, String lastname, String street){
        this.firstName=firstname;
        this.lastName=lastname;
        this.street=street;
    }
    public void setFirstName(String firstname){
        this.firstName=firstname;
    }
    
    public void setLastName(String lastname){
        this.lastName=lastname;
    }
    
    public void setStreet(String street){
        this.street=street;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public String getStreet(){
        return street;
    }
    
}
