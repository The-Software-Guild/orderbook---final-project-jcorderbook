/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DAO;

import MainFolder.DTO.DTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author recyn
 */
public interface DTODaoInterface {

    DTO addDTO(DTO dto) throws DTOPersistenceException;
    
    List<DTO> getAllDTOs() throws DTOPersistenceException;
    
    DTO getDTO (Type1 var1) throws DTOPersistenceException;
    
    DTO removeDTO(Type1 var1) throws DTOPersistenceException;
    
    DTO editDTO(Type1 var1, Type2 var2, Type3 var3, Type4 var4, Type5 var5, Type6 var6, Type7 var7) throws DTOPersistenceException;
    
    Map<String, DTO> getMap();

}
