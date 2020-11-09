/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DAO;



/**
 *
 * @author recyn
 */

public interface DTOAuditDao {
    public void writeAuditEntry(String entry) throws DTOPersistenceException;
    
}
