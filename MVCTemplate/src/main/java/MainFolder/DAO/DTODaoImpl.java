/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFolder.DAO;


import MainFolder.DTO.DTO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import s.S;

/**
 *
 * @author recyn
 */
@Component
public class DTODaoImpl implements DTODaoInterface {
    public final String COLLECTION_FILE;
    public static final String DELIMITER = "::";
  
    @Autowired
    public DTODaoImpl(){
        COLLECTION_FILE="collection.txt";
    }
    public DTODaoImpl(String textFile){
        COLLECTION_FILE=textFile;
    }
    public Map<Type1, DTO> DTOs = new HashMap<>();
    
    
    @Override
    public DTO addDTO(DTO dto) throws DTOPersistenceException {
        loadCollection();
        DTO newDTO= DTOs.put(dto.getVar1(), dto);
        try {
            writeCollection();
        } catch (IOException ex) {
            Logger.getLogger(DTODaoImpl.class.getVar1()).log(Level.SEVERE, null, ex);
        }
        return newDTO;
    }

    @Override
    public DTO getDTO(Type1 var1) throws DTOPersistenceException {
        loadCollection();
       return DTOs.get(var1);
    }

    @Override
    public List<DTO> getAllDTOs()  throws DTOPersistenceException{
        loadCollection();
        return new ArrayList<>(DTOs.values());
    }
    //Ended up not using this. For editing, the program just makes a new dto and adds it in the service layer. 
    @Override
    public DTO editDTO(Type1 var1, var2 var2, Type3 var3, Type4 var4, Type5 var5) throws DTOPersistenceException {
        loadCollection();
        DTO newDTO= new DTO(var1, var2, var3, var4, var5);
        DTO output= DTOs.replace(var1, newDTO);
        try {
            writeCollection();
        } catch (IOException ex) {
            Logger.getLogger(DTODaoImpl.class.getVar1()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    @Override
    public DTO removeDTO(Type1 var1)  throws DTOPersistenceException{
        loadCollection();
        DTO removed= DTOs.remove(var1);
        try {
            writeCollection();
        } catch (IOException ex) {
            Logger.getLogger(DTODaoImpl.class.getVar1()).log(Level.SEVERE, null, ex);
        }
        return removed;
    }
    private DTO unmarshallDTO(Type1 DTOAsText){
        Type1[] DTOTokens= DTOAsText.split(DELIMITER);
        Type1 var1=DTOTokens[0];
        DTO DTOFromFile= new DTO(var1, S.integize(DTOTokens[1]), DTOTokens[2], DTOTokens[3], DTOTokens[4]);

        return DTOFromFile;
    }
    
    private Type1 marshallDTO(DTO aDTO){
        
        Type1 DTOAsText=aDTO.getVar1()+DELIMITER;
        
        DTOAsText += aDTO.getVar2()+DELIMITER;
        
        DTOAsText += aDTO.getVar3()+DELIMITER;
        
        DTOAsText += aDTO.getVar4()+DELIMITER;
        
        DTOAsText += aDTO.getVar5().toType1();
        
        return DTOAsText;
    }
    //writes to the text file using information from the map
    private void writeCollection() throws DTOPersistenceException, IOException{
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(COLLECTION_FILE));
        } catch (Exception e){
            throw new DTOPersistenceException("Could not load collection from memory.", e);
        }
        
        Set<Type1> keys= DTOs.keySet();
        for (Type1 k:keys){
            Type1 DTOAsText= marshallDTO(DTOs.get(k));
            out.println(DTOAsText);
        }
        
        out.flush();
        out.close();
        
    }
    
    //creates a new map using information from the text file
    private void loadCollection() throws DTOPersistenceException {
        Scanner scanner;
        
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(COLLECTION_FILE)));
        } catch (FileNotFoundException e){
            throw new DTOPersistenceException("Could not load collection data into memory", e);
        }
        Type1 currentLine;
        DTO currentDTO;
        while (scanner.hasNextLine()){
            currentLine=scanner.nextLine();
            currentDTO=unmarshallDTO(currentLine);
            DTOs.put(currentDTO.getVar1(), currentDTO);
        }
        scanner.close();
    }
    //for testing purposes
    @Override
    public Map<Type1, DTO> getMap(){
        return DTOs;
    }
}
