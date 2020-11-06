/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AddressDao;

import AddressDTO.Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author recyn
 */
public class AddressBookImpl implements AddressBookInterface {
    
    private Map<String, Address> addressBook = new HashMap<>();
    
    @Override
    public Address addAddress(Address inputaddress) throws AddressException{
       Address newAddress= addressBook.put(inputaddress.getLastName(), inputaddress);
       return newAddress;
    }

    
    @Override
    public Address removeAddress(String lastName)throws AddressException{
        Address removedAddress= addressBook.remove(lastName);
        return removedAddress;
    }
        
    
    @Override
    public Address getAddress(String lastname)throws AddressException{
        Address aAddress= addressBook.get(lastname);
        return aAddress;
    }
    
    public List<Address> listAllAddresses()throws AddressException{
        List<Address> addressList= new ArrayList<Address>(addressBook.values());
        return addressList;
    }
    
    @Override
    public int countAddresses() throws AddressException{
       int count= addressBook.size();
       return count;
    }
    //Edits address by first making sure the edited address exists. If it does, it only replaces the fields where the updated values are not empty strings. 
    //Returns the edited address if edit is successful. 
    @Override
    public Address editAddress(String lastname, String[] fields)throws AddressException{
        
        Address aAddress= addressBook.get(lastname);
        if (aAddress== null){
            return aAddress;
        } 
        if (!fields[0].equals("")){
            aAddress.setFirstName(fields[0]);
        }
        if (!fields[1].equals("")){
            aAddress.setLastName(fields[1]);
        }
        if (!fields[2].equals("")){
            aAddress.setStreet(fields[2]);
        }
        addressBook.put(aAddress.getLastName(), aAddress);
        
        
        return aAddress;
    }
}
