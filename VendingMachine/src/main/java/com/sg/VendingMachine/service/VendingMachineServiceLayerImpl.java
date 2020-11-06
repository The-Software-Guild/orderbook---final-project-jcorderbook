/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.service;


import com.sg.VendingMachine.dao.ItemDao;
import com.sg.VendingMachine.dao.ItemDaoInterface;
import com.sg.VendingMachine.dao.VendingMachineAuditDao;
import com.sg.VendingMachine.dao.VendingMachinePersistenceException;
import com.sg.VendingMachine.dto.Item;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author recyn
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {
    private VendingMachineAuditDao auditDao;
    private ItemDaoInterface dao;
    //private VendingMachineAuditDao auditDao;
    public VendingMachineServiceLayerImpl(ItemDaoInterface dao, VendingMachineAuditDao auditDao){
        this.dao=dao;
        this.auditDao=auditDao;
    }
    @Override
    public void createItem(Item item) throws VendingMachinePersistenceException {
        
            dao.addItem(item.getName(), item);
     
       
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }
    

 //Buys an item by decreasing item count by 1 and returning change to the user and the cash in the VendingMachien is set back to 0. 
    //If item doesn't exist, or item count is 0, or the money is not enough, the appropriate exception is thrown.
    @Override
    public BigDecimal BuyItem(String name) throws AllOutException, VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
        Item anItem= dao.getItem(name);
        
        if (anItem==null){
            throw new NoItemInventoryException("No such item in the inventory");
        } else if (anItem.getCount()==0){
            throw new AllOutException("All out of that item.");
        }
        BigDecimal change= dao.getCash().subtract(anItem.getCost());
        if (change.compareTo(new BigDecimal("0")) < 0){
            throw new InsufficientFundsException("Not enough money");
        } 
        dao.subtractItem(name);
        dao.setCash(new BigDecimal("0"));
        auditDao.writeAuditEntry(name+ " bought.");
        return change;
        
    }
    //Sets the cash amount in the DAO.
    @Override
    public void setCash(BigDecimal money) throws VendingMachinePersistenceException {
        dao.setCash(money);
    }
    //Resets every item count back to 10. 
    @Override
    public void restock() throws Exception {
           dao.restock();
           auditDao.writeAuditEntry("Restocked");
    }
    //Only gets all items with count>0. 
    @Override
    public List<Item> getAvailableItems() throws VendingMachinePersistenceException {
        List<Item> allItems= dao.getAllItems();
        List<Item> availableItems= allItems.stream()
                                    .filter((item) -> item.getCount()>0)
                                    .collect(Collectors.toList());
        return availableItems;
    }
    //Refunds the money the user put in and displays that. The VendingMachine cash value is set back to 0. 
    @Override
    public BigDecimal refund() throws VendingMachinePersistenceException{
        BigDecimal refund=dao.getCash();
        dao.setCash(BigDecimal.ZERO);
        return refund;
    }
    //Given a BigDecimal value, returns an array of strings for the number of quarters, dimes, nickels, and pennies returned to the user.
    public String[] changeInCoins(BigDecimal change) throws Exception{
        BigDecimal quarterValue= new BigDecimal("0.25");
        BigDecimal quarters= change.divide(quarterValue, 0, RoundingMode.FLOOR);
        BigDecimal subtractedquarters= quarters.multiply(quarterValue);
        BigDecimal changeSubtractedquarters= change.subtract(subtractedquarters);
        
        BigDecimal dimeValue= new BigDecimal("0.10");
        BigDecimal dimes= changeSubtractedquarters.divide(dimeValue, 0, RoundingMode.FLOOR);
        BigDecimal subtracteddimes= dimes.multiply(dimeValue);
        BigDecimal changeSubtracteddimes= changeSubtractedquarters.subtract(subtracteddimes);
        
        BigDecimal nickelValue= new BigDecimal("0.05");
        BigDecimal nickels= changeSubtracteddimes.divide(nickelValue, 0, RoundingMode.FLOOR);
        BigDecimal subtractednickels= nickels.multiply(nickelValue);

        BigDecimal changeSubtractednickels= changeSubtracteddimes.subtract(subtractednickels);

        BigDecimal pennyValue= new BigDecimal("0.01");
        BigDecimal pennys= changeSubtractednickels.divide(pennyValue, 0, RoundingMode.FLOOR);
        BigDecimal subtractedpennys= pennys.multiply(pennyValue);
        BigDecimal changeSubtractedpennys= changeSubtractednickels.subtract(subtractedpennys);
        
        String[] coins = {quarters.toString(), dimes.toString(), nickels.toString(), pennys.toString()};
        return coins;
}
}
