/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvd.dao;

import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.sg.dvd.dao.DVDDaoFileImpl;
import com.sg.dvd.dto.DVD;
import java.util.List;
/**
 *
 * @author recyn
 */
public class DVDDaoFileImplTest {
    //public static DVDDao testDao;
    public DVDDaoFileImplTest() {
    }
    public static DVDDao testDao;
    
    
    @BeforeAll
    
    public static void setUpClass() throws IOException {
        
        String testFile="testroster.txt";
        new FileWriter(testFile);
        testDao= new DVDDaoFileImpl(testFile);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    @Test
    public void setUp() throws IOException {
        
    }
    
    @AfterEach
    public void tearDown() {
    }
    /*
    @Test
    public void testAddGetDVD() throws Exception{
        
        String testFile="testroster.txt";
        new FileWriter(testFile);
        testDao= new DVDDaoFileImpl(testFile);
        DVD newDVD= new DVD("The Incredibles");
        newDVD.setReleaseDate("2004");
        newDVD.setMpaa("PG13");
        newDVD.setDirector("Brad Bird");
        newDVD.setStudio("Pixar");
        newDVD.setRatingNote("Amazing");
        String title= "The Incredibles";
        testDao.addDVD(title ,newDVD);
        
        DVD retrievedDVD=testDao.getDVD(title);
        
        assertEquals(newDVD.getTitle(), retrievedDVD.getTitle(), "Checking title");
        assertEquals(newDVD.getReleaseDate(), retrievedDVD.getReleaseDate(), "Checking title");
        assertEquals(newDVD.getMpaa(), retrievedDVD.getMpaa(), "Checking title");
        assertEquals(newDVD.getDirector(), retrievedDVD.getDirector(), "Checking title");
        assertEquals(newDVD.getStudio(), retrievedDVD.getStudio(), "Checking title");
        assertEquals(newDVD.getRatingNote(), retrievedDVD.getRatingNote(), "Checking title");
    }
    
    @Test
    public void testListDVD() throws Exception{
        String testFile="testroster.txt";
        new FileWriter(testFile);
        testDao= new DVDDaoFileImpl(testFile);
        DVD newDVD= new DVD("The Incredibles");
        newDVD.setReleaseDate("2004");
        newDVD.setMpaa("PG13");
        newDVD.setDirector("Brad Bird");
        newDVD.setStudio("Pixar");
        newDVD.setRatingNote("Amazing");
        String title= "The Incredibles";
        testDao.addDVD(title ,newDVD);
        DVD newDVD2= new DVD("Hero");
        newDVD2.setReleaseDate("2002");
        newDVD2.setMpaa("PG13");
        newDVD2.setDirector("Zhang Yimou");
        newDVD2.setStudio("Unknown");
        newDVD2.setRatingNote("Amazing");
        String title2= "Hero";
        testDao.addDVD(title2 ,newDVD2);
        
        List<DVD> returnedList= testDao.getAllDVDs();
        
        assertTrue(returnedList.contains(newDVD), "Does it contain The Incredibles?");
        assertTrue(returnedList.contains(newDVD2), "Does it contain Hero?");
        assertEquals(2, returnedList.size(),"Is it size 2");
        
    }
    
    @Test
    public void testRemoveDVD() throws Exception{
        String testFile="testroster.txt";
        new FileWriter(testFile);
        testDao= new DVDDaoFileImpl(testFile);
        DVD newDVD= new DVD("The Incredibles");
        newDVD.setReleaseDate("2004");
        newDVD.setMpaa("PG13");
        newDVD.setDirector("Brad Bird");
        newDVD.setStudio("Pixar");
        newDVD.setRatingNote("Amazing");
        String title= "The Incredibles";
        testDao.addDVD(title ,newDVD);

        DVD newDVD2= new DVD("Hero");
        newDVD2.setReleaseDate("2002");
        newDVD2.setMpaa("PG13");
        newDVD2.setDirector("Zhang Yimou");
        newDVD2.setStudio("Unknown");
        newDVD2.setRatingNote("Amazing");
        String title2= "Hero";
        testDao.addDVD(title2 ,newDVD2);
        
        DVD removedDVD= testDao.removeDVD(title);
        
        assertEquals(newDVD, removedDVD, "The correct object was removed");
        
        List<DVD> DVDlist=testDao.getAllDVDs();
        
        assertEquals(newDVD2, DVDlist.get(0), "Is Hero still there?");
        assertEquals(1, DVDlist.size(), "Should be size 1");
        
        DVD retrievedDVD=testDao.getDVD(title);
        assertNull(retrievedDVD, "Should be null");
        
        
        
    }
*/
    
}
