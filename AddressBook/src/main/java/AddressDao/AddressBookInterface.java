/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddressDao;

import AddressDTO.Address;
import java.util.List;

/**
 *
 * @author recyn
 */
public interface AddressBookInterface {
    Address addAddress(Address inputaddress) throws AddressException;

    
    Address removeAddress(String lastName)throws AddressException;
        
    
    Address getAddress(String lastname) throws AddressException;
    
    List<Address> listAllAddresses() throws AddressException;
    
    int countAddresses() throws AddressException;
    
    Address editAddress(String lastname, String[] fields) throws AddressException;
}
