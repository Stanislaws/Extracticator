/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package extracticator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author XZAJACZK
 */
public class ExtracticatorTest {
    
    public ExtracticatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of about method, of class Extracticator.
     */
    @Test
    public void testAbout() {
        System.out.println("about");
        Extracticator instance = new Extracticator();
        instance.about();
    }

    /**
     * Test of extract method, of class Extracticator.
     */
    @Test
    public void testExtract() {
        System.out.println("extract");
        Extracticator instance = new Extracticator();
        instance.extract();
    }


    /**
     * Test of newfile method, of class Extracticator.
     */
    @Test
    public void testNewfile() {
        System.out.println("newfile");
        Extracticator instance = new Extracticator();
        instance.newfile();
    }

    /**
     * Test of ready method, of class Extracticator.
     */
    @Test
    public void testReady() {
        System.out.println("ready");
        Extracticator instance = new Extracticator();
        instance.ready();
    }

    /**
     * Test of open method, of class Extracticator.
     */
    @Test
    public void testOpen() {
        System.out.println("open");
        Extracticator instance = new Extracticator();
        instance.open();
    }

    /**
     * Test of save method, of class Extracticator.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        Extracticator instance = new Extracticator();
        instance.save();
    }

    /**
     * Test of selectall method, of class Extracticator.
     */
    @Test
    public void testSelectall() {
        System.out.println("selectall");
        Extracticator instance = new Extracticator();
        instance.selectall();    
    }
    
    /**
     * Test of main method, of class Extracticator.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Extracticator.main(args);       
    }
    
}
