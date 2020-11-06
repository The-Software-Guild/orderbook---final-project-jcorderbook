/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvd.controller;

import com.sg.dvd.dao.DVDDaoException;
import com.sg.dvd.dao.DVDDaoFileImpl;
import com.sg.dvd.dto.DVD;
import com.sg.dvd.ui.DVDView;
import com.sg.dvd.ui.UserIO;
import com.sg.dvd.ui.UserIOConsoleImpl;
import java.util.List;
import com.sg.dvd.dao.DVDDao;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 *
 * @author recyn
 */
public class DVDController {
    //constructor, takes in an instance of the DVDDao and DVDView classes
    public DVDController (DVDDao dao, DVDView view){
        this.dao = dao;
        this.view = view;
    }
    //defines the member fields as DVDView and DVDDao classes
    private DVDView view;
    private DVDDao dao;
    
    //main run function that is used by App
    public void run(){
        boolean keepGoing= true;
        int menuSelection= 0;
        //try catch loop with a while loop inside to keep asking the user for an input until a valid one is entered. 
        
        while (keepGoing) {
            try {
            
            while (true){
                try{
                    menuSelection=getMenuSelection();
                    break;
                }catch(Exception e){
                    view.displayErrorMessage("Invalid input. Please enter a number between 1 and 14.");
                }
            }
            //switch block pairs the functions with the user input
            switch(menuSelection){
                case 1:
                    listDVDs();
                    break;
                case 2:
                    createDVD();
                    break;
                case 3:
                    viewDVD();
                    break;
                case 4:
                    removeDVD();
                    break;
                case 5:
                    editDVD();
                    break;
                case 6:
                    moviesInNYears();
                    break;
                case 7:
                    AllWithMPAARating();
                    break;
                case 8:
                    AllWithDirector();
                    break;
                case 9:
                    AllWithStudio();
                    break;
                case 10:
                    AverageAge();
                    break;
                case 11:
                    NewestMovie();
                    break;
                case 12:
                    OldestMovie();
                    break;
                case 13:
                    AverageNumberOfNotes();
                    break;
                case 14:
                    keepGoing= false;
                    break;
                default:
                    unknownCommand();
                
            }
        }

        catch(DVDDaoException | NumberFormatException e){
            view.displayErrorMessage(e.getMessage());
        }
        }
        exitMessage();
    }
            
    
    
    //View functions are used to get input from the user and display results, dao functions are used to change the DVD collection. 
    private int getMenuSelection(){
        return view.printMenuAndGetSelection();
    }
    private void createDVD()throws DVDDaoException{
        view.displayCreateDVDBanner();
        DVD newDVD= view.getNewDVDInfo();
        DVD addedDVD= dao.addDVD(newDVD.getTitle(), newDVD);
        if (addedDVD==null){
            view.displayCreateSuccessBanner();
        } else {
            view.displayErrorMessage("That DVD already exists");
        }
    }
    private void listDVDs()throws DVDDaoException{
        view.displayDisplayAllBanner();
        List<DVD> dvdList= dao.getAllDVDs();
        view.displayDVDList(dvdList);
    }
    private void viewDVD()throws DVDDaoException{
        view.displayDisplayDVDBanner();
        String title= view.getTitleChoice();
        DVD dvd= dao.getDVD(title);
        view.displayDVD(dvd);
    }
    private void removeDVD()throws DVDDaoException{
        view.displayRemoveDVDBanner();
        String title= view.getTitleChoice();
        DVD removedDVD= dao.removeDVD(title);
        view.displayRemoveResult(removedDVD);
    }
    private void editDVD() throws DVDDaoException{
        view.displayEditDVDBanner();
        String[] newinfo= view.askDVDinfo();
        List<String> ratingNote= view.getEditRatingNote();
        DVD editedDVD=dao.editDVD(newinfo[0], newinfo[1], newinfo[2], newinfo[3], newinfo[4], newinfo[5], ratingNote);
        view.displaySuccessfulEditBanner();
        view.displayDVD(editedDVD);
    }
    
    private void unknownCommand(){
        view.displayUnknownCommandBanner();
    }
    
    private void exitMessage(){
        view.displayExitBanner();
    }
    private void moviesInNYears() throws DVDDaoException{
        int years= view.getNYears();
        List<DVD> moviesSince= dao.moviesInNYears(years);
        view.displayDVDList(moviesSince);
    }
    
    private void AllWithMPAARating() throws DVDDaoException{
        String mpaa= view.getMPAA();
        List<DVD> movies= dao.AllWithMPAARating(mpaa);
        view.displayDVDList(movies);
    }
    
    private void AllWithStudio() throws DVDDaoException{
        String studio= view.getStudio();
        List<DVD> movies= dao.AllWithStudio(studio);
        view.displayDVDList(movies);
    }
    
    private void AllWithDirector() throws DVDDaoException{
        String director = view.getDirector();
        Map<String, List<DVD>> movies= dao.AllWIthDirector(director);
        view.displayMap(movies);
    }
    
    private void AverageAge() throws DVDDaoException{
        OptionalDouble averageAge= dao.AverageAge();
        view.displayAverageAge(averageAge.getAsDouble());
    }
    
    private void NewestMovie() throws DVDDaoException{
        Optional<DVD> newest= dao.NewestMovie();
        view.displayDVD(newest.get());   
    }
    
    private void OldestMovie() throws DVDDaoException{
        Optional<DVD> oldest= dao.OldestMovie();
        view.displayDVD(oldest.get());   
    }
    
    private void AverageNumberOfNotes() throws DVDDaoException{
        double num= dao.AverageNumberOfNotes();
        view.displayAverageNotes(num);
    }
        

}