/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvd.dao;

import com.sg.dvd.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author recyn
 * You will add the following features to your program:

Find all movies released in the last N years
Find all the movies with a given MPAA rating
Find all the movies by a given director
When searching by director, the movies should be sorted into separate data structures by MPAA rating.
Find all the movies released by a particular studio
Find the average age of the movies in the collection
Find the newest movie in your collection
Find the oldest movie in your collection
Find the average number of notes associated with movies in your collection
Your implementation must include:
An Interface for your DAO that contains all the methods specified here plus all the methods in Version 1 of your DAO.
An implementation class that implements the DVDLibrary interface using Lambdas, Streams, and Aggregates.
All necessary changes to the Controller, View, and App classes
 */


public class DVDDaoFileImpl implements DVDDao {
    
    public final String COLLECTION_FILE;
    public static final String DELIMITER = "::";
    private Map<String, DVD> DVDs= new HashMap<>();
    
    public DVDDaoFileImpl(){
        COLLECTION_FILE="collection.txt";
    }
    public DVDDaoFileImpl(String textFile){
        COLLECTION_FILE=textFile;
    }

    
    
    @Override
    public DVD addDVD(String title, DVD dvd) throws DVDDaoException {
        loadCollection();
        DVD newDVD= DVDs.put(title, dvd);
        try {
            writeCollection();
        } catch (IOException ex) {
            Logger.getLogger(DVDDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return newDVD;
        
    }
    
    @Override
    //function takes in multiple parameters and only changes those fields in the DVD class if they're not emtpy strings. returns the DVD being edited.
    public DVD editDVD(String title, String newTitle, String releaseDate, String Mpaa, String director, String studio, List<String> ratingNote) throws DVDDaoException {
        loadCollection();
        DVD aDVD= DVDs.get(title);
        if (aDVD==null){
            return null;
        }
        if (!newTitle.equals("")){
            aDVD.setTitle(newTitle);
        
        }
        if (!Mpaa.equals("")){
            aDVD.setMpaa(Mpaa);
        }
        if (!director.equals("")){
            aDVD.setDirector(director);
        }
        if (!studio.equals("")){
            aDVD.setStudio(studio);
        }
        if (!ratingNote.isEmpty()){
            aDVD.updateRatingNote(ratingNote);
        }
        if (!releaseDate.equals("")){
            aDVD.setReleaseDate(releaseDate);
        }
        if (!newTitle.equals("")){
            DVDs.put(newTitle, aDVD);
            DVDs.remove(title);

        } else{
            DVDs.replace(title, aDVD);
        }
        try {
                writeCollection();
            } catch (IOException ex) {
                Logger.getLogger(DVDDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        return aDVD;
    }

    @Override
    public List<DVD> getAllDVDs() throws DVDDaoException {
        loadCollection();
        return new ArrayList<DVD>(DVDs.values());
    }

    @Override
    public DVD getDVD(String title) throws DVDDaoException {
        loadCollection();
        return DVDs.get(title);
    }

    @Override
    public DVD removeDVD(String title) throws DVDDaoException{
        loadCollection();
        DVD removedDVD=DVDs.remove(title);
        try {
            writeCollection();
        } catch (IOException ex) {
            Logger.getLogger(DVDDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return removedDVD;
    }
    
    private DVD unmarshallDVD(String DVDAsText){
        String[] DVDTokens= DVDAsText.split(DELIMITER);
        String title=DVDTokens[0];
        DVD DVDFromFile= new DVD(title);
        DVDFromFile.setReleaseDate(DVDTokens[1]);
        DVDFromFile.setMpaa(DVDTokens[2]);
        DVDFromFile.setDirector(DVDTokens[3]);
        DVDFromFile.setStudio(DVDTokens[4]);
        List<String> ratingNote= new ArrayList();
        for (int i = 5; i< DVDTokens.length; i++){
            ratingNote.add(DVDTokens[i]);
        }
        DVDFromFile.setRatingNote(ratingNote);
        return DVDFromFile;
    }
    
    private String marshallDVD(DVD aDVD){
        
        String DVDAsText=aDVD.getTitle()+DELIMITER;
        DVDAsText += aDVD.getReleaseDate()+DELIMITER;
        
        DVDAsText += aDVD.getMpaa()+DELIMITER;
        
        DVDAsText += aDVD.getDirector()+DELIMITER;
        
        DVDAsText += aDVD.getStudio()+DELIMITER;
        for (int i=0; i< aDVD.getRatingNote().size()-1;i++){
            DVDAsText += aDVD.getRatingNote().get(i)+DELIMITER;
        }
        DVDAsText += aDVD.getRatingNote().get(aDVD.getRatingNote().size()-1);
        
        return DVDAsText;
    }
    //writes to the text file using information from the map
    private void writeCollection() throws DVDDaoException, IOException{
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(COLLECTION_FILE));
        } catch (Exception e){
            throw new DVDDaoException("Could not load collection from memory.", e);
        }
        
        Set<String> keys= DVDs.keySet();
        for (String k:keys){
            String DVDAsText= marshallDVD(DVDs.get(k));
            out.println(DVDAsText);
        }
        
        out.flush();
        out.close();
        
    }
    
    //creates a new map using information from the text file
    private void loadCollection() throws DVDDaoException {
        Scanner scanner;
        
        try {
            scanner=new Scanner(new BufferedReader(new FileReader(COLLECTION_FILE)));
        } catch (FileNotFoundException e){
            throw new DVDDaoException("Could not load collection data into memory", e);
        }
        String currentLine;
        DVD currentDVD;
        while (scanner.hasNextLine()){
            currentLine=scanner.nextLine();
            currentDVD=unmarshallDVD(currentLine);
            DVDs.put(currentDVD.getTitle(), currentDVD);
        }
        scanner.close();
    }

    //Gets all movies made since N years ago. 
    @Override
    public List<DVD> moviesInNYears(int N) throws DVDDaoException {
        loadCollection();
        LocalDate today= LocalDate.now();
        int year= today.getYear();
        int years= year- N;
        List<DVD> movies=  new ArrayList(DVDs.values());
        List<DVD> afterYear= movies.stream()
                .filter((dvd) -> dvd.getYear() > years)
                .collect(Collectors.toList());
        return afterYear;
    }
    //The following function returns a list of all movies that has the input MPAA rating. 
    @Override
    public List<DVD> AllWithMPAARating(String Mpaa) throws DVDDaoException {
        loadCollection();
        List<DVD> movies=  new ArrayList(DVDs.values());
        List<DVD> output= movies.stream()
                .filter((d) -> d.getMpaa().equals(Mpaa))
                .collect(Collectors.toList());
        return output;
    }
    //Returns a map of all movies by one director and sorts that list by MPAA rating. 
    @Override
    public Map<String,List<DVD>> AllWIthDirector(String Director) throws DVDDaoException {
        loadCollection();
        List<DVD> movies=  new ArrayList(DVDs.values());
        
        List<DVD> moviesByDirector= movies.stream()
                .filter((d) -> d.getDirector().equals(Director))
                .collect(Collectors.toList());
        Map<String, List<DVD>> output= moviesByDirector.stream()
                .collect(Collectors.groupingBy((d)-> d.getMpaa()));
        return output;
    }


    @Override
    public List<DVD> AllWithStudio(String Studio) throws DVDDaoException {
        loadCollection();
        List<DVD> movies=  new ArrayList(DVDs.values());
        List<DVD> output= movies.stream()
                .filter((d) -> d.getStudio().equals(Studio))
                .collect(Collectors.toList());
        return output;
    }
    //Returns average age of all movies in the collection
    @Override
    public OptionalDouble AverageAge() throws DVDDaoException {
        loadCollection();
        LocalDate today= LocalDate.now();
        int year= today.getYear();
        List<DVD> movies=  new ArrayList(DVDs.values());
        OptionalDouble averageAge= movies.stream()
                .mapToDouble((d) -> year- d.getYear())
                .average();
        return averageAge;
    }
    
    @Override
    public Optional<DVD> NewestMovie() throws DVDDaoException {
        loadCollection();
        List<DVD> movies=  new ArrayList(DVDs.values());
        Optional<DVD> output= movies.stream()
                .collect(Collectors.maxBy(Comparator.comparing(DVD::getYear)));
        if (output.isPresent()){
        return output;
        } 
        return null;
    }

    @Override
    public Optional<DVD> OldestMovie() throws DVDDaoException {
        loadCollection();
        List<DVD> movies=  new ArrayList(DVDs.values());
        Optional<DVD> output= movies.stream()
                .collect(Collectors.minBy(Comparator.comparing(DVD::getYear)));
        if (output.isPresent()){
        return output;
        } 
        return null;
    }
    //Returns the average number of notes for a movie in the collection.
    @Override
    public double AverageNumberOfNotes() throws DVDDaoException {
        loadCollection();
        List<DVD> movies=  new ArrayList(DVDs.values());
        double notesList= movies.stream()
                .map((d) -> d.getRatingNote())
                .collect(Collectors 
                .averagingInt( 
                       num -> num.size())); 

        return notesList;        
        
    }

    

    

    
    
    
}
