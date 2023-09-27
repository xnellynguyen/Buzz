package edu.lehigh.cse216.yen225.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Map;

/**
 * App is our basic admin app.  The admin-cli app has the following functions: connect, create/drop table, insert/delete/update a row, query a row/all rows, delete a row, disconnect
 */
public class App {

    /**
     * Print the menu for our program
     */
    public static void menu() {
        System.out.println("Main Menu");
        System.out.println("  [T] Create table");
        System.out.println("  [D] Drop table");
        System.out.println("  [1] Query for a specific row");
        System.out.println("  [*] Query for all rows");
        System.out.println("  [-] Delete a row");
        System.out.println("  [+] Insert a new row");
        System.out.println("  [~] Update a row");
        System.out.println("  [q] Quit Program");
        System.out.println("  [?] Help (this message)");
    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    public static char prompt(BufferedReader in) {
        // The valid actions:
        String actions = "TD1*-+~q?";
        
        // We repeat until a valid single-character option is selected        
        while (true) {
            System.out.print("[" + actions + "] :> ");
            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Ask the user to enter a String message
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The string that the user provided.  May be "".
     */
    public static String getString(BufferedReader in, String message) {
        String s;
        try {
            System.out.print(message + " :> ");
            s = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * Ask the user to enter an integer
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The integer that the user provided.  On error, it will be -1
     */
    public static int getInt(BufferedReader in, String message) {
        int i = -1;
        try {
            System.out.print(message + " :> ");
            i = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * The main routine runs a loop that gets a request from the user and
     * processes it
     * 
     * @param argv Command-line options.  Ignored by this program.
     */
    public static void main(String[] argv) {
        // get the Postgres configuration from the environment
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");

        // Get a fully-configured connection to the database, or exit 
        // immediately
       
        
        // Start our basic command-line interpreter:
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
           // System.out.println("Enter Table Name: ");
            String tableName = getString (in,"Enter Table Name: ") ; 
            Database db = Database.getDatabase(ip, port, user, pass,tableName);
            if (db == null)
                return;
            menu(); 
            // Get the user's request, and do it
            //
            // NB: for better testability, each action should be a separate
            //     function call


            char action = prompt(in);
            if (action == '?') {
                menu();
            } else if (action == 'q') {
                break;
            } else if (action == 'T') {
                db.createTable();
            } else if (action == 'D') {
                db.dropTable();
            } else if (action == '1') {
                int id = getInt(in, "Enter the row ID");
                if (id == -1)
                    continue;
                Database.RowData res = db.selectOne(id);
                if (res != null) {
                    System.out.println("  [" + res.mId + "] " + res.mSubject);
                    System.out.println("  --> " + res.mMessage);
                }
            } else if (action == '*') {
               
              //  ArrayList<Database.RowDataUser> resUser = db.selectAllUser();
                // if (res == null ||resUser == null )
                //     continue;
                System.out.println("  Current Database Contents");
                System.out.println("  -------------------------");
                if (tableName.equals ("tblData")) { 
                    ArrayList<Database.RowData> res = db.selectAll();
                for (Database.RowData rd : res) {
                    System.out.println("  [" + rd.mId + "] " + rd.mSubject + ":" + rd.mMessage + ":" + rd.mLikeCount);
                }
                } else if (tableName.equals ("tblUser")) { 
                    ArrayList<Database.RowDataUser> resUser = db.selectAllUser();
                    for (Database.RowDataUser rdUser : resUser) {
                        System.out.println ("in usertbl query") ;
                    System.out.println("  [" + rdUser.mUserID + "] " + rdUser.mUsername + ":" + rdUser.mEmail + ":" + rdUser.mGenderID + ":" + rdUser.mSexO + ":"+ rdUser.mBio);
                    }
                }
            } else if (action == '-') {
                int id = getInt(in, "Enter the row ID");
                if (tableName.equals ("tblData")) { 
                if (id == -1)
                    continue;
                int res = db.deleteRow(id);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows deleted");
                } else if (tableName.equals ("tblUser")) {
                    int idUser = getInt(in, "Enter the row ID");
                    if (idUser == -1)
                    continue;
                    int resUser = db.deleteUser(idUser);
                    if (resUser == -1)
                    continue;
                   System.out.println("  " + resUser + " rows deleted");
                }
            } else if (action == '+') {
                if (tableName.equals ("tblData")) { 
                String subject = getString(in, "Enter the subject");
                String message = getString(in, "Enter the message");
                if (subject.equals("") || message.equals(""))
                    continue;
                int res = db.insertRow(subject, message);
                System.out.println(res + " rows added");
                } else if (tableName.equals ("tblUser")) {
                    String username = getString(in, "Enter the username");
                    String email = getString(in, "Enter the email");
                    String genderID =  getString(in, "Enter the Gender Identification:");
                    String sexO =  getString(in, "Enter the sexual orinentation:");
                    String bio = getString(in, "Enter a bio:"); 
                    int res = db.insertRowUser(username, email, genderID,sexO, bio );
                    System.out.println(res + " rows added");
                }
            } else if (action == '~') {
                int id = getInt(in, "Enter the row ID :> ");
                if (id == -1)
                    continue;
                String newMessage = getString(in, "Enter the new message");
                int res = db.updateOne(id, newMessage);
                if (res == -1)
                    continue;
                System.out.println("  " + res + " rows updated");
            }
            db.disconnect();
        }
        // Always remember to disconnect from the database when the program exits
       
    }
}