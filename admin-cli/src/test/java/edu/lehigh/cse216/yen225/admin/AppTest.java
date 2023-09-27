package edu.lehigh.cse216.yen225.admin;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.Map;
import edu.lehigh.cse216.yen225.admin.Database;

/**
 * Unit test for simple Admin app
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Test for getDatabase()
     */
    public void testConnection(){
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        String tblName = "tblTest";
        Database db = Database.getDatabase(ip, port, user, pass,tblName);
        assertTrue( db != null );
    }

    /*
     * Test for createTable()
     
     public void testAddTable(){
         Map<String, String> env = System.getenv();
         String ip = env.get("POSTGRES_IP");
         String port = env.get("POSTGRES_PORT");
         String user = env.get("POSTGRES_USER");
         String pass = env.get("POSTGRES_PASS");
         String tblName = "tblTest";
         Database db = Database.getDatabase(ip, port, user, pass,tblName);
         db.createTable();
         assertTrue( db.selectAll() != null);
         db.dropTable();
     } */

     /*
      * Test for dropTable()
      
     public void testDropTable(){
         Map<String, String> env = System.getenv();
         String ip = env.get("POSTGRES_IP");
         String port = env.get("POSTGRES_PORT");
         String user = env.get("POSTGRES_USER");
         String pass = env.get("POSTGRES_PASS");
         String tblName = "tblTest";
         Database db = Database.getDatabase(ip, port, user, pass,tblName);
         db.createTable();
         db.dropTable();
         assertTrue(db.selectAll() == null);
     } */


     /*
     * Test for insertRow()
     * for regular posts 
     
   public void testInsertRow(){
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        String tblName = "tblTest";
        String message = "Test Message";
        String subject = "Test Subject";
        Database db = Database.getDatabase(ip, port, user, pass,tblName);
        db.createTable();
        int count = db.insertRow(subject, message);
        assertEquals(count, 1);
        db.dropTable();
    } */


    /* 
     * Test for deleteRow()
    
    public void testRemoveRow(){
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        String tblName = "tblTest";
        String message = "Test Message";
        String subject = "Test Subject";
        Database db = Database.getDatabase(ip, port, user, pass,tblName);
        db.createTable();
        db.insertRow(subject, message);
        int id= db.getID(message);
        int count = db.deleteRow(id);
        assertTrue(count==0);
        db.dropTable();
    } */

    /*
     * Test for createTable()
     * for the User table 
     
     public void testAddTableUser(){
         Map<String, String> env = System.getenv();
         String ip = env.get("POSTGRES_IP");
         String port = env.get("POSTGRES_PORT");
         String user = env.get("POSTGRES_USER");
         String pass = env.get("POSTGRES_PASS");
         String tblName = "tblTest";
         Database db = Database.getDatabase(ip, port, user, pass,tblName);
         db.createTable();
         assertTrue( db.selectAll() != null);
         db.dropTable();
     } */

 /*
     * Test for insertRow()
     * for adding a user to app 
     
    public void testInsertRowUser(){
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        String tblName = "tblTest";
        String username = "TestUser";
        String email = "test225@lehigh.edu";
        String genderID  = "male" ; 
        String sexO  = "hetro" ; 
        String bio  = "Video Game player" ; 
        Database db = Database.getDatabase(ip, port, user, pass,tblName);
        db.createTableUser();
        int count = db.insertRowUser(username, email, genderID, sexO, bio);
       //assertEquals(count == 0);
        System.out.println("Number of rows inserted: " + count);
        assertTrue(count==1);
        db.dropTable();
    } */

    
 /*
     * Test for insertRow()
     * for adding a user to app 
     
    public void  testRemoveRowUser (){
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        String tblName = "tblTest";
        String username = "TestUser";
        String email = "test225@lehigh.edu";
        String genderID  = "male" ; 
        String sexO  = "hetro" ; 
        String bio  = "Video Game player" ; 
        Database db = Database.getDatabase(ip, port, user, pass, tblName);
        db.createTableUser();
        db.insertRowUser(username, email, genderID, sexO, bio);
        int id= db.getID(username);
        int count = db.deleteRow(id);
        assertTrue(count==1);
        db.dropTable();
    } */

    /**
     * Test for validateUser()
     * for making a user valid
     */
    public void testValidateUser(){
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        String tblName = "tblTest";
        String username = "TestUser";
        String email = "test225@lehigh.edu";
        String genderID  = "male" ; 
        String sexO  = "hetro" ; 
        String bio  = "Video Game player" ; 
        Database db = Database.getDatabase(ip, port, user, pass, tblName);
        db.createTableUser();
        db.insertRowUser(username, email, genderID, sexO, bio);
        int id= db.getID(username);
        db.validate("tblUser", id);
        db.dropTable();
    }

    /**
     * Test for validateUser()
     * for making a user valid
     */
    public void testInvalidateUser(){
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        String tblName = "tblTest";
        String username = "TestUser";
        String email = "test225@lehigh.edu";
        String genderID  = "male" ; 
        String sexO  = "hetro" ; 
        String bio  = "Video Game player" ; 
        Database db = Database.getDatabase(ip, port, user, pass, tblName);
        db.createTableUser();
        db.insertRowUser(username, email, genderID, sexO, bio);
        int id= db.getID(username);
        db.validate("tblUser", id);
        db.invalidate("tblUser", id);
        db.validate("tblUser", id);
        db.dropTable();
    }

}