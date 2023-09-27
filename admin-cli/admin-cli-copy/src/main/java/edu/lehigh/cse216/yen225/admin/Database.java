package edu.lehigh.cse216.yen225.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class Database {
    /**
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    /**
     * A prepared statement for getting all data in the database
     */
    private PreparedStatement mSelectAll;

    /**
     * A prepared statement for getting all data in the tblUser  
     */
    private PreparedStatement mSelectAllUser ; 

    /**
     * A prepared statement for getting one row from the database
     */
    private PreparedStatement mSelectOne;

    /**
     * A prepared statement for deleting a row from the database
     */
    private PreparedStatement mDeleteOne;

    /**
     * A prepared statement for deleting a user from the database
     */
    private PreparedStatement  mDeleteOneUser ; 

    /**
     * A prepared statement for inserting into the database
     */
    private PreparedStatement mInsertOne;
 /**
     * A prepared statement for inserting into the database of tblUser
     */
    private PreparedStatement  mInsertOneUser ; 

    /**
     * A prepared statement for updating a single row in the database
     */
    private PreparedStatement mUpdateOne;

    /**
     * A prepared statement for creating the messages table in our database
     */
    private PreparedStatement mCreateTable;

        /**
     * A prepared statement for creating the user table in our database
     */
    private PreparedStatement mCreateTableUser;

    /**
     * A prepared statement for dropping the table in our database
     */
    private PreparedStatement mDropTable;

    /**
     * A prepared statement for getting a post's ID based on its message in our database
     */
    private PreparedStatement mGetId;

      /**
     * A prepared statement for getting a post's ID based on its message in our database
     */
    private PreparedStatement mGetIdUser;

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
    public static class RowData {
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
         * The like stored in this row
         */
        int mLikeCount;
        /**
         * Construct a RowData object by providing values for its fields
         * 
         * @param id ID of the row
         * @param subject Subject (title) of the post
         * @param message Message (content) of the post
         * @param likeCount The number of like associated with the post
         */
        public RowData(int id, String subject, String message, int likeCount) {
            mId = id;
            mSubject = subject;
            mMessage = message;
            mLikeCount = likeCount;
        }
    }


    public static class RowDataUser {
        /**
         * The ID of this user of the database
         */
        int mUserID;
        /**
         * The username stored in this row
         */
        String mUsername;
        /**
         * The email stored in this row
         */
        String mEmail;
        /**
         * The like stored in this row
         */
        String mGenderID;

          /**
         * The like stored in this row
         */
        String mSexO;
  /**
         * The intro stored in this row for the user 
         */
        String mBio ; 
        /**
         * Construct a RowData object by providing values for its fields
         * 
         * @param userID ID of the row
         * @param username username of the user 
         * @param email email of the user 
         * @param genderID The gender associated with the user 
         * @param sexO The gender associated with the user 
         * @param bio The gender associated with the user 
         */
        public RowDataUser(int userID, String username, String email, String genderID, String sexO, String bio ) {
            mUserID = userID;
            mUsername = username;
            mEmail = email;
            mGenderID = genderID;
            mSexO = sexO ; 
            mBio = bio ;

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
     * @param tableName The name of the table -- making the prepared statements dynamic
     * 
     * @return A Database object, or null if we cannot connect properly
     */
    static Database getDatabase(String ip, String port, String user, String pass, String tableName) {
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
            db.disconnect();
            return null;
        }
  

        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
            db.mCreateTable = db.mConnection.prepareStatement(
                    "CREATE TABLE "+tableName+" (id SERIAL PRIMARY KEY, subject VARCHAR(50) "
                    + "NOT NULL, message VARCHAR(1024) NOT NULL," + " likeCount int NOT NULL)");

            db.mCreateTableUser = db.mConnection.prepareStatement("CREATE TABLE "+tableName+" (id SERIAL PRIMARY KEY," +
              "username VARCHAR(50) NOT NULL," +  "useremail VARCHAR(50) NOT NULL,"+ "genderid VARCHAR(50) NOT NULL," + "sexori VARCHAR(50) NOT NULL," + "bio VARCHAR(1024) NOT NULL)" ) ; 
            db.mDropTable = db.mConnection.prepareStatement("DROP TABLE "+tableName);

            // Standard CRUD operations
            db.mDeleteOne = db.mConnection.prepareStatement("DELETE FROM "+tableName+" WHERE id = ?");
            db.mDeleteOneUser = db.mConnection.prepareStatement("DELETE FROM "+tableName+" WHERE userid = ?");
            db.mInsertOne = db.mConnection.prepareStatement("INSERT INTO "+tableName+" VALUES (default, ?, ?, ?)");
            db.mInsertOneUser = db.mConnection.prepareStatement("INSERT INTO "+tableName+" VALUES (default, ?, ?, ?, ?, ?)");
            db.mSelectAll = db.mConnection.prepareStatement("SELECT id, subject, message, likeCount FROM "+tableName);
            db.mSelectAllUser= db.mConnection.prepareStatement("SELECT userid, username, useremail, genderid, sexori, bio FROM " + tableName)  ;
            db.mSelectOne = db.mConnection.prepareStatement("SELECT * from "+tableName+" WHERE id=?");
            db.mUpdateOne = db.mConnection.prepareStatement("UPDATE "+tableName+" SET message = ? WHERE id = ?");
            db.mGetId = db.mConnection.prepareStatement("SELECT id from "+tableName+" WHERE subject=?");
            db.mGetIdUser =  db.mConnection.prepareStatement("SELECT id from "+tableName+" WHERE userName=?");
        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();

            db.disconnect();
            return null;
        }
        return db;
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
     * 
     * @return The number of rows that were inserted. Return 0 if message exceeds limit. 
     */
    int insertRow(String subject, String message) {
        int count = 0; 
        boolean loop = true;
        do{
            if((message.length())<1024){
                loop = false;
            }else{
                System.out.println("Do not exceed 1024 characters");
                return 0;
            }
        }while(loop);
        try {
            mInsertOne.setString(1, subject);
            mInsertOne.setString(2, message);
            mInsertOne.setInt(3, 0);
            count += mInsertOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

     /**
     * Insert a row into the database
     * 
     * @param subject The subject for this new row
     * @param message The message body for this new row
     * 
     * @return The number of rows that were inserted. Return 0 if message exceeds limit. 
     */
    int insertRowUser(String username, String email, String genderID, String sexO, String bio) {
        int count = 0; 
        boolean loop = true;
        try {
            mInsertOneUser.setString(1, username);
            mInsertOneUser.setString(2, email);
            mInsertOneUser.setString(3, genderID);
            mInsertOneUser.setString(4, sexO);
            mInsertOneUser.setString(5, bio);

            count += mInsertOneUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Query the database for a list of all subjects and their IDs
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<RowData> selectAll() {
        ArrayList<RowData> res = new ArrayList<RowData>();
        try {
            ResultSet rs = mSelectAll.executeQuery();
            while (rs.next()) {
                res.add(new RowData(rs.getInt("id"), rs.getString("subject"), rs.getString("message"),rs.getInt("likeCount")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        } 
    }


    ArrayList<RowDataUser> selectAllUser() {
        ArrayList<RowDataUser> res = new ArrayList<RowDataUser>();
        System.out.println("in select all");
        try {
            System.out.println("in try block");
            ResultSet rs = mSelectAllUser.executeQuery();
            while (rs.next()) {
                System.out.println("in select all, got row");
                res.add(new RowDataUser(rs.getInt("userid"), rs.getString("username"), rs.getString("useremail"), rs.getString("genderid"),  rs.getString("sexori"),  rs.getString("bio")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        } 
    }

    /**
     * Get all data for a specific row, by ID
     * 
     * @param id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    RowData selectOne(int id) {
        RowData res = null;
        try {
            mSelectOne.setInt(1, id);
            ResultSet rs = mSelectOne.executeQuery();
            if (rs.next()) {
                res = new RowData(rs.getInt("id"), rs.getString("subject"), rs.getString("message"),rs.getInt("likeCount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * can user for tblData, tblUSer 
     * @return The number of rows that were deleted.  -1 indicates an error.
     */
    int deleteRow(int id) {
        int res = -1;
        try {
            mDeleteOne.setInt(1, id);
            res = mDeleteOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return res;
    }

    int deleteUser(int id) {
        int res = -1;
        try {
            mDeleteOneUser.setInt(1, id);
            res = mDeleteOneUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return res;
    }

    /**
     * Get a row's ID based on its message
     * 
     * @param message
     * 
     * @return the id associated with the message. -1 indicates message was not found.
     */
    int getID(String message){
        int id = -1;
        try{
            mGetId.setString(1, message);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    int getIDUser(String message){
        int id = -1;
        try{
            mGetIdUser.setString(1, message);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Update the message for a row in the database
     * 
     * @param id The id of the row to update
     * @param message The new message contents
     * 
     * @return The number of rows that were updated.  -1 indicates an error.
     */
    int updateOne(int id, String message) {
        int res = -1;
        try {
            mUpdateOne.setString(1, message);
            mUpdateOne.setInt(2, id);
            res = mUpdateOne.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
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


    void createTableUser() {
        try {
            mCreateTableUser.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //mCreateTableUser

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
}