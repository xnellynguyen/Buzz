package edu.lehigh.cse216.yen225.admin;

import edu.lehigh.cse216.yen225.admin.Database.RowData;
import edu.lehigh.cse216.yen225.admin.Database.RowDataUser;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Database
 */
public class DatabaseTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DatabaseTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DatabaseTest.class);
    }

    /**
     * Ensure that the constructor populates every field of the object it creates
     */
    public void testRowDataConstructorPost() {
        String message = "Test Message";
        String subject = "Test Subject";
        int id = 17;

        RowData rd = new RowData(id, subject, message, 0);

        assertTrue(rd.mMessage.equals(message));
        assertTrue(rd.mSubject.equals(subject));
        assertTrue(rd.mId == id);
        assertTrue(rd.mLikeCount == 0);
    }

    
    public void testRowDataConstructorUser() {
        String username = "TestUser";
        String email = "test225@lehigh.edu";
        int userID = 17;
        String genderID = "male";
        String sexO = "hetro";
        String bio = "17 years old";
<<<<<<<< HEAD:admin-cli/admin-cli-copy/src/test/java/edu/lehigh/cse216/yen225/admin/DatabaseTest.java

        RowDataUser rd = new RowDataUser(userID, username, email, genderID, sexO ,bio);
========
        int validity = 1;

        RowDataUser rd = new RowDataUser(userID, username, email, genderID, sexO ,bio, validity);
>>>>>>>> admin:admin-cli/src/test/java/edu/lehigh/cse216/yen225/admin/DatabaseTest.java

        assertTrue(rd.mEmail.equals(email));
        assertTrue(rd.mUsername.equals(username));
        assertTrue(rd.mUserID == userID);
        assertTrue(rd.mGenderID == genderID);
        assertTrue(rd.mSexO == sexO);

    }
}