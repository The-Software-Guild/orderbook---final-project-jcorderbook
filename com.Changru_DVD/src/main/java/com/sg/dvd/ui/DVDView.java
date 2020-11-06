/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvd.ui;

import com.sg.dvd.dto.DVD;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;

/**
 *
 * @author recyn
 */
public class DVDView {
    
    public DVDView(UserIO io){
        this.io=io;
    }
    public void displayErrorMessage(String errorMsg){
        io.print("\n=== ERROR ===");
        io.print(errorMsg+"\n");
    }
    private UserIO io;
    
    public int printMenuAndGetSelection(){
        io.print("Main Menu");
            io.print("1.List DVD's in collection");
            io.print("2. Add a new DVD");
            io.print("3. View a DVD");
            io.print("4. Remove a DVD");
            io.print("5. Edit DVD information");
            io.print("6. Find all movies released in the last N years");
            io.print("7. Find all movies with a given MPAA rating");
            io.print("8. Find all the movies by a given director");
            io.print("9. Find all the movies from one studio");
            io.print("10. Find the average age of the movies in the collection");
            io.print("11. Find the newest movie in the collection");
            io.print("12. Find the oldest movie in the collection");
            io.print("13. Find the average number of notes associated with movies in your collection.");
            io.print("14. Exit");
            
            return io.readInt("Please select from the " + "above choices.", 1, 14);
    }
    
    public DVD getNewDVDInfo() {
        String title = io.readString("Please enter DVD Title");
        String releaseDate= io.readString("Please enter the Release Date");
        String Mpaa= io.readString("Please enter the MPAA");
        String director = io.readString("Please enter the Director");
        String studio = io.readString("Please enter the Studio");
        String Rating = io.readString("Please enter the Rating");
        List<String> ratingNotes= new ArrayList();
        ratingNotes.add(Rating);
        String note="";
        do {
            note= io.readString("Do you have any further notes to add? Or press enter if none.");
            if (!note.equals("")){
                ratingNotes.add(note);
            }
        } while (!note.equals(""));
        DVD currentDVD= new DVD(title);
        currentDVD.setReleaseDate(releaseDate);
        currentDVD.setMpaa(Mpaa);
        currentDVD.setDirector(director);
        currentDVD.setStudio(studio);
        currentDVD.setRatingNote(ratingNotes);
        return currentDVD;
    }
    
    public void displayCreateDVDBanner(){
        io.print("=== Create DVD ===");
    }
    
    public void displayCreateSuccessBanner(){
        io.readString( "DVD successfully created. Please hit enter to continue\n");
    }
    
    //takes in a list of DVD's and displays the information for each of them
    public void displayDVDList(List<DVD> dvdList){
        for (DVD dvd:dvdList){
            String date=dvd.getReleaseDate();
            io.print(dvd.getTitle());
            io.print(dvd.getReleaseDate());
            io.print(dvd.getMpaa());
            io.print(dvd.getDirector());
            io.print(dvd.getStudio());
            for (String s: dvd.getRatingNote()){
                System.out.print(s+", ");
            }
            io.print("\n");
        }
        io.readString("Please hit enter to continue.");
    }
    public void displayDisplayAllBanner(){
        io.print("=== Display All DVDs ===");
    }
    
    public void displayDisplayDVDBanner(){
        io.print("=== Display DVD ===");
    }
    
    public String getTitleChoice(){
        return io.readString("Please enter the DVD Title.");
    }
    
    public void displayDVD(DVD dvd){
        if (dvd != null) {
            io.print(dvd.getTitle());
            io.print(dvd.getReleaseDate());
            io.print(dvd.getMpaa());
            io.print(dvd.getDirector());
            io.print(dvd.getStudio());
            for (String s: dvd.getRatingNote()){
                System.out.print(s+", ");
            }
            io.print("");
        }
        else{
            io.print("No such DVD.");
        }
        io.readString("Please hit enter to continue.");
    }
    public void displayRemoveDVDBanner(){
        io.print("=== Remove DVD ===");
    }
    
    public void displayRemoveResult(DVD dvdRecord){
        if (dvdRecord != null){
            io.print("DVD successfully removed.");
        }
        else{
            io.print("No such dvd.");
        }
        io.readString("Please hit enter to continue.");
    }
    public void displayExitBanner(){
        io.print("Good Bye!!!");
    }
    
    public void displayUnknownCommandBanner(){
        io.print("Unknown Command!!");
    }
    public void displayEditDVDBanner(){
        io.print("=== Edit DVD ===");
    }
    //takes in user inputs for DVD member fields and outputs a list of strings
    public String[] askDVDinfo(){
        String title= io.readString("What is the DVD Title?");
        String newTitle= io.readString("Please enter the new DVD Title if you would like to change it, or press Enter to continue");
        String releaseDate= io.readString("Please enter the new DVD Release Date if you would like to change it, or press Enter to continue");
        String mpaa= io.readString("Please enter the new DVD MPAA rating if you would like to change it, or press Enter to continue");
        String director= io.readString("Please enter the new DVD Director if you would like to change it, or press Enter to continue");
        String studio= io.readString("Please enter the new DVD Studio if you would like to change it, or press Enter to continue");
        String[] param= {title, newTitle, releaseDate, mpaa, director, studio};
        return param;
    }
    
    public List<String> getEditRatingNote(){
        List<String> ratingNotes= new ArrayList();
        String note;
        String response= io.readString("Replace existing Rating or Notes? Yes/No");
        if (response.equals("Yes")){
            ratingNotes.add("Yes");
        } else {
            ratingNotes.add("No");
        }
        do {
            note= io.readString("What are your new Rating/Notes? Or press enter if none.");
            if (!note.equals("")){
                ratingNotes.add(note);
            }
        } while (!note.equals(""));
        
        return ratingNotes;
    }
    
    public void displaySuccessfulEditBanner(){
        io.print("\n === DVD Successfully Edited === \n");
    }
    
    public void displayMap(Map<String,List<DVD>> map){
        Set<String> keySet= map.keySet();
        for (String s:keySet){
            io.print(s);
            List<DVD> DVDList= map.get(s);
            for (DVD dvd: DVDList){
                if (dvd != null) {
            io.print(dvd.getTitle());
            io.print(dvd.getReleaseDate());
            io.print(dvd.getMpaa());
            io.print(dvd.getDirector());
            io.print(dvd.getStudio());
            for (String note:dvd.getRatingNote()){
                System.out.print(note+", ");
            }
            io.print("");
        }
        else{
            io.print("No such DVD.");
        }
            }
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayAverageAge(Double d){
        io.print("The average age of all the movies is "+d+ " years old");
        io.readString("Please hit enter to continue.");
    }
    
    public void displayAverageNotes(Double d){
        io.print("The average number of notes of all the movies is "+d);
        io.readString("Please hit enter to continue.");
    }
    public int getNYears(){
        int N= io.readInt("Give an Integer N for the last N years.");
        return N;
    }
    
    public String getStudio(){
        String studio=io.readString("What studio?");
        return studio;
    }
    public String getDirector(){
        String studio= io.readString("What director?");
        return studio;
    }
    public String getMPAA(){
        String mpaa=io.readString(("What MPAA rating?"));
        return mpaa;
    }
    
}
