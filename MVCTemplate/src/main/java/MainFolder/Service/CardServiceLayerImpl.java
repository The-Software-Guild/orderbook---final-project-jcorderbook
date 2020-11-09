/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.Service;

import DAO.DTOAuditDao;
import DAO.DTOAuditDaoFileImpl;
import DAO.DTODaoInterface;
import DAO.DTOPersistenceException;
import DTO.DTO;
import DTO.DTO.Element;


import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author recyn
 */
@Component
public class DTOServiceLayerImpl implements DTOServiceLayer {
    DTODaoInterface dao;
    private DTOAuditDao auditDao;
    @Autowired
    public DTOServiceLayerImpl(DTODaoInterface dao, DTOAuditDao auditDao){
        this.dao=dao;
        this.auditDao=auditDao;
    }
    
    //Adds a dto to the DAO and makes sure the added dto doesn't already exist and has valid parameters. 
    @Override
    public void createDTO(DTO dto) throws DTODuplicateIdException, DTODataValidationException, DTOPersistenceException {

        
        if (dao.getDTO(dto.getVar1()) != null){
            throw new DTODuplicateIdException("Error: Could not Create dto. DTO Name "
            + dto.getVar1()
            + " already exists");
        }
        
        //Now validate all the fields on the given DTO object.
        //This method will throw an exception if any of the validation rtules are violated.
        validateDTOData(dto);
        
        //We passed all our business rules checks so go ahead and persist the dto object
        dao.addDTO(dto);
        //Tje stidemt was successfully created, now write to the audit log
        auditDao.writeAuditEntry("DTO "+dto.getVar1()+" CREATED.");
    }

    @Override
    public List<DTO> getAllDTOs() throws DTOPersistenceException {
        return dao.getAllDTOs();
    }

    @Override
    public DTO getDTO(Type1 var1) throws DTOPersistenceException {
        return dao.getDTO(var1);
    }
    //Removes a dto and will return the removed dto if it exists. If not, it returns null. 
    @Override
    public DTO removeDTO(Type1 var1) throws DTOPersistenceException {
        DTO removedDTO=null;
        removedDTO=dao.removeDTO(var1);
        
        //Write to audit log
        auditDao.writeAuditEntry("DTO " +var1 + " REMOVED");
        
        return removedDTO;
    }
    //Checks to see if a dto has values for all its member fields, except for Var2, because Var2 is an int and cannot be null. 
    //Any error with the Var2 would be caught in either the view or controller when converting a String input to an integer. 
    //Ideally this would also check whether or not the fields are also empty Strings. 
    private void validateDTOData(DTO dto) throws DTODataValidationException, DTODuplicateIdException, DTOPersistenceException{
        if (dto.getVar1()==null
                || dto.getVar1()==null
                || dto.getVar5()==null
                || dto.getVar3()==null
                || dto.getVar4()==null){
            throw new DTODataValidationException("Error: All fields [Name, HP, Var3, Var4, Var5] are required.");
        }
    }
    
    //Returns a list of dtos of a certain input element, which is an enum. 
    @Override
    public List<DTO> getDTOsOfType(Type5 var5) throws DTOPersistenceException{
        List<DTO> allDTOs= dao.getAllDTOs();
        List<DTO> allDTOsOfType= allDTOs.stream()
                .filter((c)-> c.getVar5()== var5)
                .collect(Collectors.toList());
        return allDTOsOfType;
    }
    //Given a dtoName that definitely exists in the dao already, this extracts that dto and updates the parameters if the updated parameter is not an empty String.
    //If the var1 has to be updated, then the original dto is removed before the updatedDTO is added to the dao. 
    @Override
    public DTO updateDTO(Type1 dtoName, String[] updatedParam) throws DTOPersistenceException {
            DTO updatedDTO= dao.getDTO(dtoName);
        if (!updatedParam[0].equals("")){
            updatedDTO.setName(updatedParam[0]);
            dao.removeDTO(dtoName);
        }
        if (!updatedParam[1].equals("")){
            updatedDTO.setVar2(Integer.parseInt(updatedParam[1]));
        }
        if (!updatedParam[2].equals("")){
            updatedDTO.setVar3(updatedParam[2]);
        }
        if (!updatedParam[3].equals("")){
            updatedDTO.setVar4(updatedParam[3]);
        }
        if (!updatedParam[4].equals("")){
            updatedDTO.setVar5(updatedParam[4]);
        }
        dao.addDTO(updatedDTO);
        return updatedDTO;
    }
    
    
}
