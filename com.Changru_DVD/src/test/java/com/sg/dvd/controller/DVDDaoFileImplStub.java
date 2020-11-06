/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvd.controller;

import com.sg.dvd.dao.DVDDao;
import com.sg.dvd.dao.DVDDaoException;
import com.sg.dvd.dao.DVDDaoFileImpl;
import static com.sg.dvd.dao.DVDDaoFileImplTest.testDao;
import com.sg.dvd.dto.DVD;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author recyn
 */
public class DVDDaoFileImplStub implements DVDDao {
        
    public  DVD newDVD;
    
    public DVDDaoFileImplStub(){
        newDVD = new DVD("The Incredibles");
        
        newDVD.setMpaa("PG13");
        newDVD.setDirector("Brad Bird");
        newDVD.setStudio("Pixar");
        newDVD.setRatingNote(new String[] {"Amazing"});
        String title= "The Incredibles";
    }
    
    public DVDDaoFileImplStub(DVD testDVD){
        this.newDVD=testDVD;
    }
    
    @Override
    public DVD addDVD(String title, DVD dvd) throws DVDDaoException {
        if (title.equals(newDVD.getTitle())){
            return newDVD;
        } else{
            return null;
        }
    }

    @Override
    public List<DVD> getAllDVDs() throws DVDDaoException {
        List<DVD> DVDlist= new ArrayList<>();
        DVDlist.add(newDVD);
        return DVDlist;
    }

    @Override
    public DVD getDVD(String title) throws DVDDaoException {
        if (title.equals(newDVD.getTitle())){
            return newDVD;
        } else{
            return null;
        }
    }

    @Override
    public DVD removeDVD(String title) throws DVDDaoException {
        if (title.equals(newDVD.getTitle())){
            return newDVD;
        } else{
            return null;
        }
    }
/*
    @Override
    public DVD editDVD(String title, String newTitle, String releaseDate, String Mpaa, String director, String studio, String ratingNote) throws DVDDaoException {
        if (!title.equals(newDVD.getTitle())){
            return null;
        }else{
        if (!newTitle.equals("")){
            newDVD.setTitle(newTitle);
        
        }
        if (!Mpaa.equals("")){
            newDVD.setMpaa(Mpaa);
        }
        if (!director.equals("")){
            newDVD.setDirector(director);
        }
        if (!studio.equals("")){
            newDVD.setStudio(studio);
        }
        if (!ratingNote.equals("")){
            newDVD.setRatingNote(new String[] {ratingNote});
        }
        if (!releaseDate.equals("")){
            newDVD.setReleaseDate(releaseDate);
        }
        return newDVD;
    }
    }
*/

    @Override
    public List<DVD> moviesInNYears(int years) throws DVDDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DVD> AllWithMPAARating(String Mpaa) throws DVDDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, List<DVD>> AllWIthDirector(String Director) throws DVDDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DVD> AllWithStudio(String Studio) throws DVDDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OptionalDouble AverageAge() throws DVDDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<DVD> NewestMovie() throws DVDDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<DVD> OldestMovie() throws DVDDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double AverageNumberOfNotes() throws DVDDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DVD editDVD(String title, String newTitle, String releaseDate, String Mpaa, String director, String studio, String ratingNote) throws DVDDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
