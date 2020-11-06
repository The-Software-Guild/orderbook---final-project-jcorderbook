/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvd.dao;


import com.sg.dvd.dto.DVD;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

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

//interface for the DVD data collection object
public interface DVDDao {
    DVD addDVD(String title, DVD dvd) throws DVDDaoException;
    
    List<DVD> getAllDVDs() throws DVDDaoException;
    
    DVD getDVD (String title) throws DVDDaoException;
    
    DVD removeDVD(String title) throws DVDDaoException;
    
    DVD editDVD(String title, String newTitle, String releaseDate, String Mpaa, String director, String studio, List<String> ratingNote) throws DVDDaoException;
    
    List<DVD> moviesInNYears(int years) throws DVDDaoException;
    
    List<DVD> AllWithMPAARating(String Mpaa) throws DVDDaoException;
    
    Map<String,List<DVD>> AllWIthDirector(String Director)throws DVDDaoException;
    
    List<DVD> AllWithStudio(String Studio)throws DVDDaoException;
    
    OptionalDouble AverageAge()throws DVDDaoException;
    
    Optional<DVD> NewestMovie()throws DVDDaoException;
    
    Optional<DVD> OldestMovie()throws DVDDaoException;
    
    double AverageNumberOfNotes() throws DVDDaoException;
    
    
}
