/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.Service;

import DAO.DTOPersistenceException;
import DTO.DTO;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author recyn
 */
public interface DTOServiceLayer {
    void createDTO(DTO dto) throws
            DTODuplicateIdException,
            DTODataValidationException,
            DTOPersistenceException;
    
    List<DTO> getAllDTOs() throws
            DTOPersistenceException;
    
    DTO getDTO(Type1 var1) throws
            DTOPersistenceException;
    DTO removeDTO(Type1 var1) throws
            DTOPersistenceException;
    List<DTO> getDTOsOfType(Type 5 var5) throws DTOPersistenceException;
    
    DTO updateDTO(Type1 var1, String[] updatedParam) throws DTOPersistenceException;
}
