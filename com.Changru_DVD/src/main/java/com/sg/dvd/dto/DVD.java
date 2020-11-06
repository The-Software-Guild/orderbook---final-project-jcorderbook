/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvd.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;
import s.S;

/**
 *
 * @author recyn
 */

public class DVD {

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.title);
        hash = 59 * hash + Objects.hashCode(this.releaseDate);
        hash = 59 * hash + Objects.hashCode(this.mpaa);
        hash = 59 * hash + Objects.hashCode(this.director);
        hash = 59 * hash + Objects.hashCode(this.studio);
        hash = 59 * hash + Objects.hashCode(this.ratingNote);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DVD other = (DVD) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.releaseDate, other.releaseDate)) {
            return false;
        }
        if (!Objects.equals(this.mpaa, other.mpaa)) {
            return false;
        }
        if (!Objects.equals(this.director, other.director)) {
            return false;
        }
        if (!Objects.equals(this.studio, other.studio)) {
            return false;
        }
        if (!Objects.equals(this.ratingNote, other.ratingNote)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DVD{" + "title=" + title + ", releaseDate=" + releaseDate + ", mpaa=" + mpaa + ", director=" + director + ", studio=" + studio + ", ratingNote=" + ratingNote + '}';
    }
    
    private String title;
    private LocalDate releaseDate;
    private String mpaa;
    //Programming Language +cohort month/year
    private String director;
    private String studio;
    private List<String> ratingNote;
    
    public DVD (String title){
        this.title= title;
    }
    
    public DVD (String title, String releaseDate, String mpaa, String director, String studio, List<String> ratingNote){
        this.title= title;
        LocalDate localdate= LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        this.releaseDate= localdate;
        this.mpaa=mpaa;
        this.director= director;
        this.studio=studio;
        this.ratingNote= ratingNote;
        
    }
    
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title= title;
    }
    
    public void setReleaseDate(String date){
        try{LocalDate localdate= LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                this.releaseDate=localdate;
        } catch(Exception e){
            LocalDate localdate= LocalDate.parse(date, DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
            this.releaseDate=localdate;
        }

    }
    
  
    
    public String getReleaseDate() {
        try{String releaseDateString= releaseDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
                return releaseDateString;
        } catch (Exception e){
            String releaseDateString= releaseDate.toString();
            return releaseDateString;
        }

    }
    
    public int getYear(){
        int year= releaseDate.getYear();
        return year; 
    }
    
    public void setMpaa(String mpaa){
        this.mpaa =mpaa;
    }
    
    public String getMpaa(){
        return mpaa;
    }
    
    public String getDirector(){
        return director;
    }
    
    public void setDirector(String director){
        this.director = director;
    }
    public void setStudio(String studio){
        this.studio=studio;
    }
    public String getStudio(){
        return studio;
    }
    public void setRatingNote(List<String> ratingNote){
        
        this.ratingNote=ratingNote;
    }
    public List<String> getRatingNote(){
        return ratingNote;
    }
    
    public void updateRatingNote(List<String> updatedRatingNote){
        S.print(updatedRatingNote.get(0));
        if (updatedRatingNote.get(0).equals("Yes")){
            updatedRatingNote.remove("Yes");
            this.ratingNote=updatedRatingNote;
        } else if (updatedRatingNote.get(0).equals("No")){
            List<String> current= ratingNote;
            for (int i=1; i<updatedRatingNote.size();i++){
                current.add(updatedRatingNote.get(i));

            }
            this.ratingNote= current;
        }
    }
    
}
