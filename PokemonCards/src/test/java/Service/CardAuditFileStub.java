/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.CardAuditDao;
import DAO.CardPersistenceException;

/**
 *
 * @author recyn
 */
public class CardAuditFileStub  implements CardAuditDao{

    @Override
    public void writeAuditEntry(String entry) throws CardPersistenceException {
        //Do nothing
    }
}
