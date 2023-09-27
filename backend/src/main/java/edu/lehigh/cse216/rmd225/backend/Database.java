package edu.lehigh.cse216.rmd225.backend;

import java.net.URISyntaxException;
import java.rmi.server.UID;
import java.net.URI ;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement ;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.Class ; 


import java.util.ArrayList;

import javax.swing.text.Position;
import javax.xml.crypto.Data;

import edu.lehigh.cse216.rmd225.backend.App.User;
import java.sql.Statement;

import java.io.IOException;
import java.security.GeneralSecurityException;

//import org.apache.http.client.utils.URIBuilder;



public class Database {
    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    /**
     * A prepared statement for getting all data in the tblData
     */
    private PreparedStatement mSelectAll;

    /**
     * A prepared statement for getting one row from tblData
     */
    private PreparedStatement mSelectOne;

    /**
     * A prepared statement for deleting a row from tblData
     */
    private PreparedStatement mDeleteOne;

    /**
     * A prepared statement for inserting into tblData
     */
    private PreparedStatement mInsertOne;

    /**
     * A prepared statement for updating a single row in the tblData
     */
    private PreparedStatement mUpdateOne;

    /**
     * A prepared statement for increasing likeCount in tblData
     */
    private PreparedStatement mUpdateLike;

    /**
     * A prepared statement for decreasing likeCount in tblData
     */
    private PreparedStatement mDecreaseLike;

    /**
     * A prepared statement for increasing dislikeCount in tblData
     */
    private PreparedStatement mUpdateUnlike;

    /**
     * A prepared statement for decreasing dislikeCount in tblData
     */
    private PreparedStatement mDecreaseUnlike;

    /**
     * A prepared statement for creating the table in tblData
     */
    private PreparedStatement mCreateTable;

    /**
     * A prepared statement for dropping the table in tblData
     */
    private PreparedStatement mDropTable;

    /**
     * A prepared statement for creating the table in tblComment
     */
    private PreparedStatement cCreateTable;

    /**
     * A prepared statement for dropping the table in tblComment
     */
    private PreparedStatement cDropTable;

    /**
     * A prepared statement for getting all data in the tblComment
     */
    private PreparedStatement cSelectAll;

    /**
     * A prepared statement for getting one row from the tblComment
     */
    private PreparedStatement cSelectOne;

    /**
     * A prepared statement for inserting into tblComment
     */
    private PreparedStatement cInsertOne;

    /**
     * A prepared statement for updating a single row in the tblComment
     */
    private PreparedStatement cUpdateOne;

    /**
     * A prepared statement for getting all data in the tblVote
     */
    private PreparedStatement vSelectAll;

    /**
     * A prepared statement for getting one row from tblVote given userID and postID
     */
    private PreparedStatement vSelectOne;

    /**
     * A prepared statement for inserting into tblVote
     */
    private PreparedStatement vInsertOne;

    /**
     * A prepared statement for +1 to likeState in tblVote
     */
    private PreparedStatement vIncrementLikeState;

    /**
     * A prepared statement for -1 to likeState in tblVote
     */
    private PreparedStatement vDecrementLikeState;

    /**
     * A prepared statement for decreasing a like in tblVote
     */
    private PreparedStatement vUpdateUnike ;

    /**
     * A prepared statement for creating the table in tblVote
     */
    private PreparedStatement vCreateTable;

    /**
     * A prepared statement for dropping the table in tblVote
     */
    private PreparedStatement vDropTable;

    /**
     * A prepared statement for getting all data in the tblUser
     */
    private PreparedStatement uSelectAll;

    /**
     * A prepared statement for getting one row from tblUser given userid
     */
    private PreparedStatement uSelectOne;

    /**
     * A prepared statement for getting one row from tblUser given email
     */
    private PreparedStatement uSelectOneEmail;

    /**
     * A prepared statement for deleting a row from tblUser
     */
    private PreparedStatement uDeleteOne;

    /**
     * A prepared statement for inserting into tblUser
     */
    private PreparedStatement uInsertOne;

    /**
     * A prepared statement for updating a single row's GI in the tblUser
     */
    private PreparedStatement uUpdateOneGI;

    /**
     * A prepared statement for updating a single row's SO in the tblUser
     */
    private PreparedStatement uUpdateOneSO;

    /**
     * A prepared statement for updating a single row's bio in the tblUser
     */
    private PreparedStatement uUpdateOneBio;

    /**
     * A prepared statement for creating the table in tblUser
     */
    private PreparedStatement uCreateTable;

    /**
     * A prepared statement for dropping the table in tblUser
     */
    private PreparedStatement uDropTable;

    /**
     * A prepared statement for getting the ID of a row in tblUser
     */
    private PreparedStatement uGetId;

    /**
     * A prepared statement for getting the validity of a row given the id in tblUser
     */
    private PreparedStatement uGetValidity;

    /**
     * RowData is like a struct in C: we use it to hold data, and we allow 
     * direct access to its fields.  In the context of this Database, RowData 
     * represents the data we'd see in a row.
     * 
     * We make RowData a static class of Database because we don't really want
     * to encourage users to think of RowData as being anything other than an
     * abstract representation of a row of the database.  RowData and the 
     * Database are tightly coupled: if one changes, the other should too.
     */
    public static class posts {
        /**
         * The ID of this row of the database
         */
        int mId;
        /**
         * The subject stored in this row
         */
        String mSubject;
        /**
         * The message stored in this row
         */
        String mMessage;
        /**
         * The upvote count stored in this row
         */
        int mLikeCount;
        /**
         * The dislike count stored in this row
         */
        int mDislikeCount;
        /**
         * The final like (like-dislike) in this row
         */
        int mTotalCount;
        /**
         * The user id in this row
         */
        int mUserID;
        /*
         * The string for the attachment for this row
         */
        String mAttachment;
        /*
         * The string for the link for this row
         */
        String mLink;
        /**
         * Construct a RowData object by providing values for its fields
         */
        public posts(int id, String subject, String message, int likes, int dislikes, int total, int usriden, String attachment, String link) {
            mId = id;
            mSubject = subject;
            mMessage = message;
            mLikeCount = likes;
            mDislikeCount = dislikes;
            mTotalCount = likes - dislikes;
            mUserID = usriden;
            mAttachment = attachment;
            mLink = link;
        }
    }

    public static class comments {
        /**
         * The ID of this row of the database
         */
        int cCommentID;
        /**
         * The content stored in this row
         */
        String cCommentMessage;
        /**
         * The post id of this row (this comment)
         */
        int pId;
        /**
         * The user id of this row (this comment)
         */
        int userID;
        /*
         * The string for the attachment for this row
         */
        String cAttachment;
        /*
         * The string for the link for this row
         */
        String cLink;
        /**
         * Construct a RowData object by providing values for its fields
         */
        public comments(int id, String message, int pid, int uid, String attachment, String link) {
            cCommentID = id;
            cCommentMessage = message;
            pId = pid;
            userID = uid;
            cAttachment = attachment;
            cLink = link;
        }
    }


    /**
     * The Database constructor is private: we only create Database objects 
     * through the getDatabase() method.
     */
    private Database() {
    }

/**
     * Get a fully-configured connection to the database
     * 
     * @param ip   The IP address of the database server
     * @param port The port on the database server to which connection requests
     *             should be sent
     * @param user The user ID to use when connecting
     * @param pass The password to use when connecting
     * 
     * @return A Database object, or null if we cannot connect properly
     */
    static Database getDatabase(String ip, String port, String user, String pass) {
        // Create an un-configured Database object
        Database db = new Database();

        // Give the Database object a connection, fail if we cannot get one
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://" + ip + ":" + port + "/", user, pass);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        }

        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
             //Prepared statements for tblUsers
            db.uCreateTable = db.mConnection.prepareStatement("CREATE TABLE tblUser(userID SERIAL PRIMARY KEY, userName varchar(255) NOT NULL, userEmail varchar(255) NOT NULL, genderID varchar(50), sexOri varchar(50), bio varchar(50))");
            db.uDropTable = db.mConnection.prepareStatement("DROP TABLE tblUser");
            db.uDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblUser WHERE userID = ?");
            db.uInsertOne = db.mConnection.prepareStatement("INSERT INTO tblUser VALUES (default, ?, ?, ?, ?)");
            db.uSelectAll = db.mConnection.prepareStatement("SELECT userID, userName, userEmail, genderID, sexOri FROM tblUser"); // WHERE validity=1 ??
            db.uSelectOne = db.mConnection.prepareStatement("SELECT * from tblUser WHERE userID=? "); // AND validity=1
            db.uSelectOneEmail = db.mConnection.prepareStatement("SELECT * from tblUser WHERE userEmail=?");
            db.uUpdateOneGI = db.mConnection.prepareStatement("UPDATE tblUser SET genderID = ? WHERE userID = ?");
            db.uUpdateOneSO = db.mConnection.prepareStatement("UPDATE tblUser SET sexOri = ? WHERE userID = ?");
            db.uUpdateOneBio = db.mConnection.prepareStatement("UPDATE tblUser SET bio = ? WHERE userID = ?");
            db.uGetId = db.mConnection.prepareStatement("SELECT userid from tblUser WHERE userEmail=?");
            db.uGetValidity = db.mConnection.prepareStatement("SELECT validity from tblUser WHERE userID = ?");

            //Prepared statement for tblVote
            db.vCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE tblVote(likeID SERIAL PRIMARY KEY, userID int, id int NOT NULL, likeState int CHECK(likeState BETWEEN -1 and 1))");
            db.vDropTable = db.mConnection.prepareStatement("DROP TABLE tblVote");
            db.vInsertOne = db.mConnection.prepareStatement("INSERT INTO tblVote VALUES (default, ?, ?, ?)");
            db.vSelectAll = db.mConnection.prepareStatement("SELECT likeID, userID, id, likeState FROM tblVote"); 
            db.vSelectOne = db.mConnection.prepareStatement("SELECT * from tblVote WHERE userID=? AND id=?"); 
            db.vIncrementLikeState = db.mConnection.prepareStatement("UPDATE tblVote SET likeState = CAST(likeState AS INTEGER) + 1 WHERE likeID = ?"); 
            db.vDecrementLikeState = db.mConnection.prepareStatement("UPDATE tblVote SET likeState = CAST(likeState AS INTEGER) - 1 WHERE likeID = ?"); 

            //Prepared statements for tblData 
            db.mCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE tblData (id SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL, message VARCHAR(1024) NOT NULL, likeCount int, dislikesCount int, totalCount int, userID int, attachment VARCHAR(1024), link VARCHAR(1024))");
            db.mDropTable = db.mConnection.prepareStatement("DROP TABLE tblData");
            db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblData WHERE id = ?");
            db.mInsertOne = db.mConnection.prepareStatement("INSERT INTO tblData VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            db.mSelectAll = db.mConnection.prepareStatement("SELECT id, subject, message, likecount, dislikescount, totalcount, userid, attachment, link FROM tblData WHERE validity=1"); // WHERE validity=1 ??
            db.mSelectOne = db.mConnection.prepareStatement("SELECT * from tblData WHERE id=? AND validity=1"); // AND validity=1 ??
            //db.mUpdateOne = db.mConnection.prepareStatement("UPDATE tblData SET message = ? WHERE id = ?");
            db.mUpdateLike = db.mConnection.prepareStatement("UPDATE tblData SET likeCount = CAST(likeCount AS INTEGER) + 1 WHERE id = ?"); 
            db.mDecreaseLike = db.mConnection.prepareStatement("UPDATE tblData SET likeCount = CAST(likeCount AS INTEGER) - 1 WHERE id = ?");
            db.mUpdateUnlike = db.mConnection.prepareStatement("UPDATE tblData SET dislikescount = CAST(dislikescount AS INTEGER) + 1 WHERE id = ?"); 
            db.mDecreaseUnlike = db.mConnection.prepareStatement("UPDATE tblData SET dislikescount = CAST(dislikescount AS INTEGER) - 1 WHERE id = ?");

            //Prepared statements for tblComment
            db.cCreateTable = db.mConnection.prepareStatement("CREATE TABLE tblComment (commentID SERIAL PRIMARY KEY, commentMessage VARCHAR(100), id int NOT NULL," + " userID int NOT NULL, commentAttachment VARCHAR(1024), commentLink VARCHAR(1024))");
            db.cDropTable = db.mConnection.prepareStatement("DROP TABLE tblComment");
            db.cInsertOne = db.mConnection.prepareStatement("INSERT INTO tblComment VALUES (default, ?, ?, ?, ?, ?, ?)");
            db.cSelectAll = db.mConnection.prepareStatement("SELECT * FROM tblComment WHERE id=? AND validity=1"); // AND validity=1
            db.cSelectOne = db.mConnection.prepareStatement("SELECT * from tblComment WHERE commentID=? AND validity=1"); // AND validity=1
            db.cUpdateOne = db.mConnection.prepareStatement("UPDATE tblComment SET commentMessage = ? and commentAttachment = ? and commentLink = ? and validity = ? WHERE commentID = ?");


        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
    }

    /**
    * Get a fully-configured connection to the database
    * 
    * @param host The IP address or hostname of the database server
    * @param port The port on the database server to which connection requests
    *             should be sent
    * @param path The path to use, can be null
    * @param user The user ID to use when connecting
    * @param pass The password to use when connecting
    * 
    * @return A Database object, or null if we cannot connect properly
    */
    static Database getDatabase(String host, String port, String path, String user, String pass) {
        if( path==null || "".equals(path) ){
            path="/";
        }

        // Create an un-configured Database object
        Database db = new Database();

        // Give the Database object a connection, fail if we cannot get one
        try {
            String dbUrl = "jdbc:postgresql://" + host + ':' + port + path;
            Connection conn = DriverManager.getConnection(dbUrl, user, pass);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        }

        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
             //Prepared statements for tblUsers
            db.uCreateTable = db.mConnection.prepareStatement("CREATE TABLE tblUser(userID SERIAL PRIMARY KEY, userName varchar(255) NOT NULL, userEmail varchar(255) NOT NULL, genderID varchar(50), sexOri varchar(50), bio varchar(50))");
            db.uDropTable = db.mConnection.prepareStatement("DROP TABLE tblUser");
            db.uDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblUser WHERE userID = ?");
            db.uInsertOne = db.mConnection.prepareStatement("INSERT INTO tblUser VALUES (default, ?, ?, ?, ?)");
            db.uSelectAll = db.mConnection.prepareStatement("SELECT userID, userName, userEmail, genderID, sexOri FROM tblUser"); // WHERE validity=1 ??
            db.uSelectOne = db.mConnection.prepareStatement("SELECT * from tblUser WHERE userID=? "); // AND validity=1
            db.uSelectOneEmail = db.mConnection.prepareStatement("SELECT * from tblUser WHERE userEmail=?");
            db.uUpdateOneGI = db.mConnection.prepareStatement("UPDATE tblUser SET genderID = ? WHERE userID = ?");
            db.uUpdateOneSO = db.mConnection.prepareStatement("UPDATE tblUser SET sexOri = ? WHERE userID = ?");
            db.uUpdateOneBio = db.mConnection.prepareStatement("UPDATE tblUser SET bio = ? WHERE userID = ?");
            db.uGetId = db.mConnection.prepareStatement("SELECT userid from tblUser WHERE userEmail=?");
            db.uGetValidity = db.mConnection.prepareStatement("SELECT validity from tblUser WHERE userID = ?");

            //Prepared statement for tblVote
            db.vCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE tblVote(likeID SERIAL PRIMARY KEY, userID int, id int NOT NULL, likeState int CHECK(likeState BETWEEN -1 and 1))");
            db.vDropTable = db.mConnection.prepareStatement("DROP TABLE tblVote");
            db.vInsertOne = db.mConnection.prepareStatement("INSERT INTO tblVote VALUES (default, ?, ?, ?)");
            db.vSelectAll = db.mConnection.prepareStatement("SELECT likeID, userID, id, likeState FROM tblVote"); 
            db.vSelectOne = db.mConnection.prepareStatement("SELECT * from tblVote WHERE userID=? AND id=?"); 
            db.vIncrementLikeState = db.mConnection.prepareStatement("UPDATE tblVote SET likeState = CAST(likeState AS INTEGER) + 1 WHERE likeID = ?"); 
            db.vDecrementLikeState = db.mConnection.prepareStatement("UPDATE tblVote SET likeState = CAST(likeState AS INTEGER) - 1 WHERE likeID = ?"); 

            //Prepared statements for tblData 
            db.mCreateTable = db.mConnection.prepareStatement(
                "CREATE TABLE tblData (id SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL, message VARCHAR(1024) NOT NULL, likeCount int, dislikesCount int, totalCount int, userID int, attachment VARCHAR(1024), link VARCHAR(1024))");
            db.mDropTable = db.mConnection.prepareStatement("DROP TABLE tblData");
            db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM tblData WHERE id = ?");
            db.mInsertOne = db.mConnection.prepareStatement("INSERT INTO tblData VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            db.mSelectAll = db.mConnection.prepareStatement("SELECT id, subject, message, likecount, dislikescount, totalcount, userid, attachment, link FROM tblData WHERE validity=1"); // WHERE validity=1 ??
            db.mSelectOne = db.mConnection.prepareStatement("SELECT * from tblData WHERE id=? AND validity=1"); // AND validity=1 ??
            //db.mUpdateOne = db.mConnection.prepareStatement("UPDATE tblData SET message = ? WHERE id = ?");
            db.mUpdateLike = db.mConnection.prepareStatement("UPDATE tblData SET likeCount = CAST(likeCount AS INTEGER) + 1 WHERE id = ?"); 
            db.mDecreaseLike = db.mConnection.prepareStatement("UPDATE tblData SET likeCount = CAST(likeCount AS INTEGER) - 1 WHERE id = ?");
            db.mUpdateUnlike = db.mConnection.prepareStatement("UPDATE tblData SET dislikescount = CAST(dislikescount AS INTEGER) + 1 WHERE id = ?"); 
            db.mDecreaseUnlike = db.mConnection.prepareStatement("UPDATE tblData SET dislikescount = CAST(dislikescount AS INTEGER) - 1 WHERE id = ?");

            //Prepared statements for tblComment
            db.cCreateTable = db.mConnection.prepareStatement("CREATE TABLE tblComment (commentID SERIAL PRIMARY KEY, commentMessage VARCHAR(100), id int NOT NULL," + " userID int NOT NULL, commentAttachment VARCHAR(1024), commentLink VARCHAR(1024))");
            db.cDropTable = db.mConnection.prepareStatement("DROP TABLE tblComment");
            db.cInsertOne = db.mConnection.prepareStatement("INSERT INTO tblComment VALUES (default, ?, ?, ?, ?, ?, ?)");
            db.cSelectAll = db.mConnection.prepareStatement("SELECT * FROM tblComment WHERE id=? AND validity=1"); // AND validity=1
            db.cSelectOne = db.mConnection.prepareStatement("SELECT * from tblComment WHERE commentID=? AND validity=1"); // AND validity=1
            db.cUpdateOne = db.mConnection.prepareStatement("UPDATE tblComment SET commentMessage = ? and commentAttachment = ? and commentLink = ? and validity = ? WHERE commentID = ?");

            
        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
    } 

    /**
    * Get a fully-configured connection to the database
    * 
    * @param db_url The url to the database
    * @param port_default port to use if absent in db_url
    * 
    * @return A Database object, or null if we cannot connect properly
    */
    static Database getDatabase(String db_url, String port_default) {
        try {
            URI dbUri = new URI(db_url);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String host = dbUri.getHost();
            String path = dbUri.getPath();
            String port = dbUri.getPort() == -1 ? port_default : Integer.toString(dbUri.getPort());

            return getDatabase(host, port, path, username, password);
        } catch (Exception s) {
            System.out.println("URI Syntax Error");
            return null;
        }
    } 

    /**
     * Close the current connection to the database, if one exists.
     * 
     * NB: The connection will always be null after this call, even if an 
     *     error occurred during the closing operation.
     * 
     * @return True if the connection was cleanly closed, false otherwise
     */
    boolean disconnect() {
        if (mConnection == null) {
            System.err.println("Unable to close connection: Connection was null");
            return false;
        }
        try {
            mConnection.close();
        } catch (SQLException e) {
            System.err.println("Error: Connection.close() threw a SQLException");
            e.printStackTrace();
            mConnection = null;
            return false;
        }
        mConnection = null;
        return true;
    }

    /**
     * Insert a row into the database
     * 
     * @param subject The subject for this new row
     * @param message The message body for this new row
     * @param attachment The attachment string for this new row (image)
     * @param link The link string for this new row (link/url)
     * @param userID the userID
     * 
     * @return The number of rows that were inserted
     */

    int insertRow(String subject, String message, String attachment, String link, int usrID) { 
        int newId = 0;
        try {
            mInsertOne.setString(1, subject);
            mInsertOne.setString(2, message);
            mInsertOne.setInt(3, 0); // like count and stuff for the next three lines
            mInsertOne.setInt(4, 0);
            mInsertOne.setInt(5, 0);
            mInsertOne.setInt(6, usrID);
            mInsertOne.setInt(7, 1);
            mInsertOne.setString(8, attachment);
            mInsertOne.setString(9, link);
            //mInsertOne.executeUpdate();
            ResultSet rs = mInsertOne.executeQuery(); // executeQuery();
            System.out.println(rs);
            if (rs.next()) {
                newId = rs.getInt("id");
            }
            System.out.println("after if statement");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newId;
    }

    /**
     * Insert a row (an user) into tblUser
     * 
     * @return The number of rows that were inserted
     */
    int uInsertRow(String userName, String userEmail) {
        int newId = 0; 
        try {
           // mInsertOne.setInt(1, id);
            uInsertOne.setString(1, userName);
            uInsertOne.setString(2, userEmail);
            uInsertOne.setString(3, null);
            uInsertOne.setString(4, null);
            ResultSet rs = uInsertOne.executeQuery();
            // newId = mInsertOne.executeUpdate();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newId;
    }

    /**
     * Insert a row (a comment) into tblComment
     * 
     * @return The number of rows that were inserted
     */

    int cInsertRow(String cMessage, String cAttachment, String cLink, int cPostID, int cUserID){
        int newId = 0; 
        try {
            // mInsertOne.setInt(1, id);
            cInsertOne.setString(1, cMessage);
            cInsertOne.setInt(2, cPostID);
            cInsertOne.setInt(3, cUserID);
            cInsertOne.setInt(4, 1);
            cInsertOne.setString(5, cAttachment);
            cInsertOne.setString(6, cLink);
            //newId = mInsertOne.executeUpdate();
            ResultSet rs = cInsertOne.executeQuery();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newId;
    }

    /**
     * Query the database for a list of all subjects and their IDs
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<posts> selectAll() {
        ArrayList<posts> res = new ArrayList<posts>();
        try {
            ResultSet rs = mSelectAll.executeQuery();
            while (rs.next()) {
                posts newPost = new posts(rs.getInt("id"), rs.getString("subject"), rs.getString("message"), rs.getInt("likeCount"), rs.getInt("dislikescount"), rs.getInt("totalcount"), rs.getInt("userid"), rs.getString("attachment"), rs.getString("link"));
                res.add(newPost);
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all data for a specific row, by ID from tblData
     * 
     * @param id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    DataRow selectOne(int id) {
        DataRow res = null;
        try {
            mSelectOne.setInt(1, id);
            ResultSet rs = mSelectOne.executeQuery();
            if (rs.next()) { 
                res = new DataRow(rs.getInt("id"), rs.getString("subject"), rs.getString("message"), rs.getInt("likeCount"), rs.getInt("dislikescount"), rs.getInt("totalcount"), rs.getInt("userid"), rs.getString("attachment"), rs.getString("link"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get all data for a specific row, by ID from tblUser
     * 
     * @param id The userId of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    DataRow uSelectOne(int id) {
        DataRow res = null;
        try {
            uSelectOne.setInt(1, id);
            ResultSet rs = uSelectOne.executeQuery();
            if (rs.next()) {
                res = new DataRow(rs.getInt("userID"), rs.getString("userName"), rs.getString("userEmail"), rs.getString("genderID"), rs.getString("sexOri"), rs.getString("bio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get all data for a specific row, by email from tblUser
     * 
     * @param String email
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    DataRow uSelectOne(String email) {
        DataRow res = null;
        try {
            uSelectOneEmail.setString(1, email);
            ResultSet rs = uSelectOneEmail.executeQuery();
            if (rs.next()) {
                res = new DataRow(rs.getInt("userID"), rs.getString("userName"), rs.getString("userEmail"), rs.getString("genderID"), rs.getString("sexOri"), rs.getString("bio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Get all data for a specific row, by ID from tblUser
     * 
     * @param id The commentID of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    DataRow cSelectOne(int cId) {
        DataRow res = null;
        try {
            cSelectOne.setInt(1, cId);
            ResultSet rs = cSelectOne.executeQuery();
            if (rs.next()) {
                res = new DataRow(rs.getInt("commentID"), rs.getString("commentMessage"), rs.getInt("id"), rs.getInt("userID"), rs.getString("commentAttachment"), rs.getString("commentLink"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * Query the database for a list of all comments given post id
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<comments> cSelectAll(int pID) {
        ArrayList<comments> res = new ArrayList<comments>();
        try {
            cSelectAll.setInt(1, pID);
            ResultSet rs = cSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new comments(rs.getInt("commentID"), rs.getString("commentMessage"), rs.getInt("id"), rs.getInt("userID"), rs.getString("commentAttachment"), rs.getString("commentLink")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted.  -1 indicates an error.
     */
    int deleteRow(int id) {
        int res = -1;
        try {
            mDeleteOne.setInt(1, id);
            res = mDeleteOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Update the message for a row in the database
     * 
     * @param id The id of the row to update
     * @param message The new message contents
     * 
     * @return The number of rows that were updated.  -1 indicates an error.
     */
    int updateOne (int id, String message ) {
        int res = -1;
        
        try {
            System.out.println (id) ; 
            System.out.println (message) ; 
            mUpdateOne.setInt(2, id);
          //  mUpdateOne.setString(2, subject);
            mUpdateOne.setString(1, message);
            //mUpdateOne.setInt(2, likes);

            //mUpdateOne.setInt(1, likes);

            //mUpdateOne.setInt(3, likes);
            res = (int) mUpdateOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int uUpdateOne (int Uid, String genderID, String sexOrientation, String uBio) {
        int res = -1;
        try {
            //System.out.println (id) ; 
            //System.out.println (message) ; 
            uUpdateOneGI.setString(1, genderID);
            uUpdateOneGI.setInt(2, Uid);
            res = (int) uUpdateOneGI.executeUpdate();
            uUpdateOneSO.setString(1, sexOrientation);
            uUpdateOneSO.setInt(2, Uid);
            res = (int) uUpdateOneSO.executeUpdate();
            uUpdateOneBio.setString(1, uBio);
            uUpdateOneBio.setInt(2, Uid);
            res = (int) uUpdateOneBio.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int cUpdateOne (int cID, int uID, String cMessage, String cAttachment, String cLink) {
        int res = 0;
        try {
            //System.out.println (cID) ; 
            //System.out.println (cMessage) ; 
            cUpdateOne.setString(1, cMessage);
            cUpdateOne.setString(2, cAttachment);
            cUpdateOne.setString(3, cLink);
            cUpdateOne.setInt(4, 1);
            cUpdateOne.setInt(5, cID);
            // cUpdateOne.setInt(6, uID);
            //cUpdateOne.setString(5, cAttachment);
            //cUpdateOne.setString(6, cLink);
            res = (int) cUpdateOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * Upvote method: Update likeCount in tblData, change likeState in tblVote (1 if new upvote, back to 0 if alr upvoted)
     * 
     * @param int userID
     * @param int postID
     * 
     * @return likeState of the vote (1 if upvoted, 0 if removed an upvote). -1 if fail.
     */

    int updateLike(int userIden, int postIden) {
        int resNum = -5;
        DataRow res = null;
    
        try {
            //get a like given userid and postid
            vSelectOne.setInt(1, userIden);
            vSelectOne.setInt(2, postIden);
            ResultSet rs = vSelectOne.executeQuery(); //(rs.next()) checks if there is at least one row of data
            if (rs.next()) { //the like (given userID and postID) already exists in tblVote
                resNum++;
                res = new DataRow(rs.getInt("likeID"), rs.getInt("userID"), rs.getInt("id"), rs.getInt("likeState"));
                int likeStatus = res.vLikeState;
                if(likeStatus==0){ //Neutral vote. Goal: Upvote. tblVote: Increment likeState (to 1). tblData: Increment likeCount
                    vIncrementLikeState.setInt(1, res.vLikeID);
                    vIncrementLikeState.executeUpdate();
                    mUpdateLike.setInt(1, postIden);
                    mUpdateLike.executeUpdate();
                    vSelectOne.setInt(1, userIden);
                    vSelectOne.setInt(2, postIden);
                    rs = vSelectOne.executeQuery();
                    if (rs.next()) {
                        res = new DataRow(rs.getInt("likeID"), rs.getInt("userID"), rs.getInt("id"), rs.getInt("likeState"));
                    }
                    return res.vLikeState;
                }else if(likeStatus==1){ //Upvoted. Goal: Back to neutral. tblVote: Decrement likeState (back to 0). tblData: Decrement likeCount
                    //set likestate back to 0 in tblVote
                    vDecrementLikeState.setInt(1, res.vLikeID);
                    vDecrementLikeState.executeUpdate();
                    //decrease likeCount in tblData
                    mDecreaseLike.setInt(1, postIden);
                    mDecreaseLike.executeUpdate();
                    vSelectOne.setInt(1, userIden);
                    vSelectOne.setInt(2, postIden);
                    rs = vSelectOne.executeQuery();
                    if (rs.next()) {
                        res = new DataRow(rs.getInt("likeID"), rs.getInt("userID"), rs.getInt("id"), rs.getInt("likeState"));
                    }
                    return res.vLikeState;
                }else{ //Downvoted (likeStatus == -1). Goal: Upvote. tblVote: Increment likeState twice (from -1 to 1). tblData: Increment likeCount, decrement dislikeCount
                    vIncrementLikeState.setInt(1, res.vLikeID); vIncrementLikeState.executeUpdate();
                    vIncrementLikeState.setInt(1, res.vLikeID); vIncrementLikeState.executeUpdate();
                    mUpdateLike.setInt(1, postIden); mUpdateLike.execute();
                    mDecreaseUnlike.setInt(1, postIden); mDecreaseUnlike.execute();
                    vSelectOne.setInt(1, userIden);
                    vSelectOne.setInt(2, postIden);
                    rs = vSelectOne.executeQuery();
                    if (rs.next()) {
                        res = new DataRow(rs.getInt("likeID"), rs.getInt("userID"), rs.getInt("id"), rs.getInt("likeState"));
                    }
                    return res.vLikeState;
                }   
            }else{  //the like (given userID and postID) does not exist in tblVote. Insert like into tblVote, set likeState to 1. tblData: Increment likeCount
                vInsertOne.setInt(1, userIden);
                vInsertOne.setInt(2, postIden);
                vInsertOne.setInt(3, 1);
                vInsertOne.executeUpdate();
                vSelectOne.setInt(1, userIden);
                vSelectOne.setInt(2, postIden);
                rs = vSelectOne.executeQuery();
                if (rs.next()) {
                    res = new DataRow(rs.getInt("likeID"), rs.getInt("userID"), rs.getInt("id"), rs.getInt("likeState"));
                }
                mUpdateLike.setInt(1, postIden);
                mUpdateLike.execute();
                return res.vLikeState;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if (resNum > -5) {
            return res.vLikeState;
        } else {
            return -5;
        }
    }

    /**
     * Downvote method: Update dislikeCount in tblData, change likeState in tblVote (-1 if new dislike, back to 0 if alr disliked)
     * 
     * @param int userID
     * @param int postID
     * 
     * @return likeState of the vote (-1 if downvoted, 0 if removed an downvote). 1 if fail.
     */

     int updateDislike(int userIden, int postIden) {
        int resNum = -5;
        DataRow res = null;
    
        try {
            //get a like given userid and postid
            vSelectOne.setInt(1, userIden);
            vSelectOne.setInt(2, postIden);
            ResultSet rs = vSelectOne.executeQuery(); //(rs.next()) checks if there is at least one row of data
            if (rs.next()) { //the vote (given userID and postID) already exists in tblVote
                resNum++;
                res = new DataRow(rs.getInt("likeID"), rs.getInt("userID"), rs.getInt("id"), rs.getInt("likeState"));
                int likeStatus = res.vLikeState;
                if(likeStatus==0){ //Neutral vote. Goal: Upvote. tblVote: Increment likeState (to 1). tblData: Increment likeCount
                    vDecrementLikeState.setInt(1, res.vLikeID);
                    vDecrementLikeState.executeUpdate();
                    mUpdateUnlike.setInt(1, postIden);
                    mUpdateUnlike.executeUpdate();
                    return res.vLikeState;
                }else if(likeStatus==-1){ //Downvoted. Goal: Back to neutral. tblVote: Increment likeState (back to 0). tblData: Decrement unlikeCount
                    //set likestate back to 0 in tblVote
                    vIncrementLikeState.setInt(1, res.vLikeID);
                    vIncrementLikeState.executeUpdate();
                    //decrease dislikesCount in tblData
                    mDecreaseUnlike.setInt(1, postIden);
                    mDecreaseUnlike.executeUpdate();
                    return res.vLikeState;
                }else{ //Upvoted (likeStatus == 1). Goal: Upvote. tblVote: Increment likeState twice (from -1 to 1). tblData: Increment likeCount, decrement dislikeCount
                    vDecrementLikeState.setInt(1, res.vLikeID); vDecrementLikeState.executeUpdate();
                    vDecrementLikeState.setInt(1, res.vLikeID); vDecrementLikeState.executeUpdate();
                    mDecreaseLike.setInt(1, postIden); mDecreaseLike.execute();
                    mUpdateUnlike.setInt(1, postIden); mUpdateUnlike.execute();
                    return res.vLikeState;
                }   
            }else{  //the like (given userID and postID) does not exist in tblVote. Insert like into tblVote, set likeState to 1. tblData: Increment likeCount
                vInsertOne.setInt(1, userIden);
                vInsertOne.setInt(2, postIden);
                vInsertOne.setInt(3, -1);
                vInsertOne.executeUpdate();
                vSelectOne.setInt(1, userIden);
                vSelectOne.setInt(2, postIden);
                rs = vSelectOne.executeQuery();
                if (rs.next()) {
                    res = new DataRow(rs.getInt("likeID"), rs.getInt("userID"), rs.getInt("id"), rs.getInt("likeState"));
                }
                mUpdateUnlike.setInt(1, postIden);
                mUpdateUnlike.execute();
                return res.vLikeState;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if (resNum > -5) {
            return res.vLikeState;
        } else {
            return -5;
        }
    }

    /**
     * Create tblData.  If it already exists, this will print an error
     */
    void createTable() {
        try {
            mCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblData from the database.  If it does not exist, this will print
     * an error.
     */
    void dropTable() {
        try {
            mDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get userID from tblUser given user's email
     * @param User usr
     * @return id of the user
     */
    int uGetId(User usr){
        int id = -1;
        try{
            uGetId.setString(1, usr.userEmail);
            ResultSet rs = uGetId.executeQuery();
            if(rs.next()){
                id = rs.getInt("userID");
            }
            return id;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Get userID from tblUser given user's email
     * @param User usr
     * @return id of the user
     */
    int uGetValidity(int id){
        int valid = -1;
        try{
            uGetValidity.setInt(1, id);
            ResultSet rs = uGetValidity.executeQuery();
            if(rs.next()){
                valid = rs.getInt("validity");
            }
            return id;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return valid;
    }
}