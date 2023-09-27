package edu.lehigh.cse216.rmd225.backend;

// Import the Spark package, so that we can make use of the "get" function to 
// create an HTTP GET route
import spark.Spark;
import java.util.Map;

// Import Google's JSON library
import com.google.gson.*;

// Import Apache HttpComponents library
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

// Import MessageDigest for hashing session key
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

// Imports for isValidAccessToken method
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.Collections;
import org.apache.http.HttpResponse;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


// Imports for Google API Client --> and Google Drive
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static spark.Spark.*;




/**
 * For now, our App creates an HTTP server that can only get and add data.
 * 
 */
public class App {
    private static final String GOOGLE_CLIENT_ID = "691318830475-e26bc040cccn4puav2rbkpqqmqlt6tmt.apps.googleusercontent.com";


    // ADD COMMENTTTT
    static class User {
        Integer userID;
        String userName;
        String userEmail; // treating email as userID
        String genderID;
        String sexOri;

        // Add more here... maybe post, comments, likes

        public User() {
        }

        public User(String userName, String userEmail) {
            this.userName = userName;
            this.userEmail = userEmail;
        }
    }

    // !!! ADD COMMENT
    public static String hashEmail(String email) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(email.getBytes());
            byte[] hash = md.digest();
            byte[] encoded = Base64.getEncoder().encode(hash);
            return new String(encoded);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyIdToken(String idTokenString) {
        boolean ret = false;
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                .build();
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if(idToken != null){
                ret = true;
            }
        } catch (Exception e) {
            // handle the exception here, e.g. log it or throw a custom exception
            ret = false;
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * The main method of the program. This method is called when the program is
     * started
     * and serves as the entry point of the program. It takes an array of strings as
     * its
     * argument, which can be used to pass command-line arguments to the program.
     *
     * @param args the command-line arguments passed to the program
     */
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        //final Integer temp;
        // public static Map<String, Integer> sessionMap = new HashMap<String, Integer>(); // Local hash table to store session keys
        // gson provides us with a way to turn JSON into objects, and objects
        // into JSON.
        //
        // NB: it must be final, so that it can be accessed from our lambdas
        //
        // NB: Gson is thread-safe. See
        // https://stackoverflow.com/questions/10380835/is-it-ok-to-use-gson-instance-as-a-static-field-in-a-model-bean-reuse
        final Gson gson = new Gson();

        // Initialize the GoogleDriveService
        GoogleDriveService googleDriveService = new GoogleDriveService();

        // dataStore holds all of the data that has been provided via HTTP
        // requests
        //
        // NB: every time we shut down the server, we will lose all data, and
        // every time we start the server, we'll have an empty dataStore,
        // with IDs starting over from 0.
        // final DataStore dataStore = new DataStore();
        Database db = getDatabaseConnection();

        // Set up the location for serving static files
        Spark.staticFileLocation("/web");
        // Get the port on which to listen for requests
        Spark.port(getIntFromEnv("PORT", DEFAULT_PORT_SPARK));

        // CHANGE THIS TO FIX FRONTEND
        String static_location_override = System.getenv("STATIC_LOCATION");
        if (static_location_override == null) {
            Spark.staticFileLocation("/web");
        } else {
            Spark.staticFiles.externalLocation(static_location_override);
        }

        Spark.options("/*",
        (request, response) -> {

            String accessControlRequestHeaders = request
                    .headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers",
                        accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request
                    .headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods",
                        accessControlRequestMethod);
            }

            return "OK";
        });

        Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        // CHANGE THISS TO FIX FRONTEND
        // Set up a route for serving the main page
        Spark.get("/", (req, res) -> {
            res.redirect("/index.html");
            return "";
        });

        if ("True".equalsIgnoreCase(System.getenv("CORS_ENABLED"))) {
            final String acceptCrossOriginRequestsFrom = "*";
            final String acceptedCrossOriginRoutes = "GET,PUT,POST,DELETE,OPTIONS";
            final String supportedRequestHeaders = "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin";
            enableCORS(acceptCrossOriginRequestsFrom, acceptedCrossOriginRoutes, supportedRequestHeaders);
        }

        // POST route that returns message to indicate whether login was succesful.
        // We first get accessToken from front-end. Validate such accessToken.
        // We then use Http to send Http requests to Google Oauth to get payload.
        // Turn the payload string into Json, use the JsonObject's get() method to
        // retrieve user info.
        // Save user info to database. Call hashEmail() to hash up session key.
        // Store the session key in the sessionMap (HashMap).
        // Set session key as cookie for furture authentication.
        Spark.post("/login/:token", (request, response) -> {
            response.status(200);
            response.type("application/json");
            try {
                String accessToken = request.params("token");

                // Verify the access token
                boolean isTokenValid = verifyIdToken(accessToken);
                if (isTokenValid == false) {
                    response.status(401);
                    return gson.toJson(new StructuredResponse("error", "Error authenticating because invalid token", accessToken));
                } else {
                    // Send an HTTP request to the Google OAuth server to retrieve the user's
                    // information
                    HttpClient client = HttpClients.createDefault();
                    HttpGet requ = new HttpGet(
                            "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + accessToken);
                    HttpResponse httpResponse = client.execute(requ);
                    String payload = EntityUtils.toString(httpResponse.getEntity());

                    // Parse the JSON response to retrieve the user's information
                    JsonParser parser = new JsonParser();
                    JsonObject json = parser.parse(payload).getAsJsonObject();
                    String userName = json.get("name").getAsString();
                    String email = json.get("email").getAsString();
                    User usr = new User(userName, email);

                    // Modify to account for returning users (already in database)
                    // Save the user's information to the database
                    DataRow row = db.uSelectOne(usr.userEmail);
                    if (row == null) {
                        db.uInsertRow(usr.userName, usr.userEmail);
                    }

                    // Hash the email to generate the session key
                    String sessionKey = hashEmail(usr.userEmail);
                    //System.out.println(sessionKey);
                    int id = db.uGetId(usr);
                    // get validity from that id
                    int valid = db.uGetValidity(id);
                    // check validity to ensure it is not 0 --> throw exception if 0
                    if ( valid == 0 ){
                        throw new IllegalArgumentException("Invalid validity: " + valid);
                    }
                    // Store the session key in the local hash table
                    MyHashMap.addToSessionMap(sessionKey, id);


                    // Return session key
                    return gson.toJson(new StructuredResponse("ok", "Authenticated, session: ", sessionKey));
                }
            } catch (Exception e) {
                // Error occurred
                e.printStackTrace();
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }
        });

        // GET route that returns null if user hasn't been authenticated.
        // Returns own user information (DataRow) if authenticated
        // Purpose: Display own profile
        Spark.get("/profile", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send a status 500
            // String sessKey = request.params("sessionKey");
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            
            // Get the session token from the request headers
            String sessKey = request.headers("Authorization");
            
            // Look up the user's session information in the sessions hashmap
            int uID = -1;
            if (MyHashMap.getFromSessionMap(sessKey) != null) {
                Integer temp = MyHashMap.getFromSessionMap(sessKey);
                uID = temp.intValue();
            }

            // If the user is not authenticated, redirect to the login page
            if (uID == -1) {
                response.redirect("/login");
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }
            
            //Authenticated users
            DataRow data = db.uSelectOne(uID);

            if (data == null) {
                return gson.toJson(new StructuredResponse("error", uID + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });

        // GET route that returns null if user hasn't been authenticated.
        // Returns user information (DataRow) if authenticated
        // Purpose: Return DataRow to display other users' profile
        Spark.get("/profile/:usrID", (request, response) -> {
            int idx = Integer.parseInt(request.params("usrID"));
            
            // If we can't get an ID or can't parse the JSON, Spark will send a status 500
            // String sessKey = request.params("sessionKey");
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            
            // Get the session token from the request headers
            String sessKey = request.headers("Authorization");
            
            // Look up the user's session information in the sessions hashmap
            int uID = -1;
            if (MyHashMap.getFromSessionMap(sessKey) != null) {
                Integer temp = MyHashMap.getFromSessionMap(sessKey);
                uID = temp.intValue();
            }

            // If the user is not authenticated, redirect to the login page
            if (uID == -1) {
                response.redirect("/login");
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }

            // Authenticated
            DataRow data = db.uSelectOne(idx);

            if (data == null) {
                return gson.toJson(new StructuredResponse("error", uID + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });

        // PUT route for for changing the SO and GI for a specfic user. 
        // Front end pass in userID (of the )
        Spark.put("/profile", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send a status 500
            
            // String sessKey = request.params("sessionKey");
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);

            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            // Get the session token from the request headers
            String sessKey = request.headers("Authorization");
            
            // Look up the user's session information in the sessions hashmap
            int uID = -1;
            if (MyHashMap.getFromSessionMap(sessKey) != null) {
                Integer temp = MyHashMap.getFromSessionMap(sessKey);
                uID = temp.intValue();
            }

            // If the user is not authenticated, redirect to the login page
            if (uID == -1) {
                response.redirect("/login");
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }

            // Authenticated users
            int result = db.uUpdateOne(uID, req.uGenderID, req.uSexOrientation, req.uBio);
            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + uID, null));
            } else {
                DataRow data = db.uSelectOne(uID);
                if (data == null) {
                    return gson.toJson(new StructuredResponse("error", uID + " not found", null));
                }
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });

        // GET route that returns all message titles and Ids. All we do is get
        // the data, embed it in a StructuredResponse, turn it into JSON, and
        // return it. If there's no data, we return "[]", so there's no need
        // for error handling.
        Spark.get("/messages", (request, response) -> {
            // ensure status 200 OK, with a MIME type of JSON
            // SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // Ensure status 200 OK, with a MIME type of JSON
            System.out.println("print messages");
            response.status(200);
            response.type("application/json");

            // Get the session token from the request headers
            String sessKey = request.headers("Authorization");
            
            // Look up the user's session information in the sessions hashmap
            int uID = -1;
            if (MyHashMap.getFromSessionMap(sessKey) != null) {
                Integer temp = MyHashMap.getFromSessionMap(sessKey);
                uID = temp.intValue();
            }

            // If the user is not authenticated, redirect to the login page
            if (uID == -1) {
                response.redirect("/login");
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }

            return gson.toJson(new StructuredResponse("ok", null, db.selectAll()));
        });
        // GET route that returns everything for a single row in the DataStore.
        // The ":id" suffix in the first parameter to get() becomes
        // request.params("id"), so that we can get the requested row ID. If
        // ":id" isn't a number, Spark will reply with a status 500 Internal
        // Server Error. Otherwise, we have an integer, and the only possible
        // error is that it doesn't correspond to a row with data.
        Spark.get("/messages/:id", (request, response) -> {
            int idx = Integer.parseInt(request.params("id"));
            // SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // Ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            // Get the session token from the request headers
            String sessKey = request.headers("Authorization");
            
            // Look up the user's session information in the sessions hashmap
            int uID = -1;
            if (MyHashMap.getFromSessionMap(sessKey) != null) {
                Integer temp = MyHashMap.getFromSessionMap(sessKey);
                uID = temp.intValue();
            }

            // If the user is not authenticated, redirect to the login page
            if (uID == -1) {
                response.redirect("/login");
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }

            System.out.println("ID number is: " + idx);

            DataRow data = db.selectOne(idx);

            if (data == null) {
                return gson.toJson(new StructuredResponse("error", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });

        // POST route for adding a new element to the DataStore. This will read
        // JSON from the body of the request, turn it into a SimpleRequest
        // object, extract the title and message, insert them, and return the
        // ID of the newly created row.
        Spark.post("/messages", (request, response) -> {
            // NB: if gson.Json fails, Spark will reply with status 500 Internal
            // Server Error
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            
            // SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // Ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            // Get the session token from the request headers
            String sessKey = request.headers("Authorization");
            
            // Look up the user's session information in the sessions hashmap
            int uID = -1;
            if (MyHashMap.getFromSessionMap(sessKey) != null) {
                Integer temp = MyHashMap.getFromSessionMap(sessKey);
                uID = temp.intValue();
            }

            // If the user is not authenticated, redirect to the login page
            if (uID == -1) {
                response.redirect("/login");
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }
            String fileURL = null;
            // need to convert req.mAttachment to binary then to a file to put in Google Drive
            if ( req.mAttachment != null ){
                // byte[] fileBytes = req.mAttachment.getBytes();
                byte[] fileBytes = Base64.getDecoder().decode(req.mAttachment);
                // String fileName = req.mAttachment.queryParams("name");
                fileURL = googleDriveService.uploadFile(fileBytes, "buzzImage"); // may have to change req.mAttachment here -- this is just the fileName being passed through
                System.out.println(fileURL);
                response.status(200);
                // return gson.toJson(new StructuredResponse("ok", "Added attachment- newId: " + out, null));
            }

            // NB: createEntry checks for null title and message
            // put txt in tblData in database --> need to convert to binary then to a file to put in Google Drive
            int out = db.insertRow(req.mSubject, req.mMessage, fileURL, req.mLink, uID);
            if (out == -1) {
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", "" + out, null));
            }

            // if req.aLink == null --> post message as done before
            
          
            
        });

        // PUT route for for changing the message for a specfic post. This will read
        // JSON from the body of the request, turn it into a SimpleRequest
        // object, extract the id and new message, insert it into the row, and return
        // the
        // new reponse row.
        /*Spark.put("/messages/:id/", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            int idx = Integer.parseInt(request.params("id"));
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            // DataRow result = db.updateOne(idx, req.mTitle, req.mMessage);
            System.out.println(idx);
            System.out.println(req.mMessage);
            int result = db.updateOne(idx, req.mMessage);
            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });
        */

        // PUT route for for changing the like count for a specfic post. This will read
        // JSON from the body of the request, turn it into a SimpleRequest
        // object. Right now, there is only one user, so posts should only have 1 like
        // or no likes.
        // the updateLike method determines if the specficied id has a like count of <
        // or > 1, then
        // increments or decrements the like count based on this condtion. Then, extract
        // the id
        // and new likecount, insert it into the row, and return the new reponse row.
        Spark.put("/likes/:id", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            int idx = Integer.parseInt(request.params("id"));
 
            // SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // Ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            // Get the session token from the request headers
            String sessKey = request.headers("Authorization");
            
            // Look up the user's session information in the sessions hashmap
            int uID = -1;
            if (MyHashMap.getFromSessionMap(sessKey) != null) {
                Integer temp = MyHashMap.getFromSessionMap(sessKey);
                uID = temp.intValue();
            }

            // If the user is not authenticated, redirect to the login page
            if (uID == -1) {
                response.redirect("/login");
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }

            //Authenticated users
            int result = db.updateLike(uID,idx);
            
            if (result == -5) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });

        Spark.put("/dislikes/:id", (request, response) -> {
            // If we can't get an ID or can't parse the JSON, Spark will send
            // a status 500
            int idx = Integer.parseInt(request.params("id"));
            
            // SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // Ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            // Get the session token from the request headers
            String sessKey = request.headers("Authorization");
            
            // Look up the user's session information in the sessions hashmap
            int uID = -1;
            if (MyHashMap.getFromSessionMap(sessKey) != null) {
                Integer temp = MyHashMap.getFromSessionMap(sessKey);
                uID = temp.intValue();
            }

            // If the user is not authenticated, redirect to the login page
            if (uID == -1) {
                response.redirect("/login");
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }

            //Authenticated users
            int result = db.updateDislike(uID,idx);
            if (result == -5) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });

/*       // DELETE route for removing a row from the DataStore
        Spark.delete("/messages/:id", (request, response) -> {
            // If we can't get an ID, Spark will send a status 500
            int idx = Integer.parseInt(request.params("id"));
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);

            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");
            // NB: we won't concern ourselves too much with the quality of the
            // message sent on a successful delete
            int result = db.deleteRow(req.mid);
            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to delete row " + idx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, null));
            }
        });
*/

        // POST route for adding a comment (adding a row) to tblComment
        Spark.post("/comments/:id", (request, response) -> {
            int idx = Integer.parseInt(request.params("id"));
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            // Get the session token from the request headers
            String sessKey = request.headers("Authorization");
            
            // Look up the user's session information in the sessions hashmap
            int uID = -1;
            if (MyHashMap.getFromSessionMap(sessKey) != null) {
                Integer temp = MyHashMap.getFromSessionMap(sessKey);
                uID = temp.intValue();
            }

            // Authenticated users
            // If the user is not authenticated, redirect to the login page
            if (uID == -1) {
                response.redirect("/login");
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }

            String fileURL = null;
            // need to convert req.mAttachment to binary then to a file to put in Google Drive
            if ( req.cAttachment != null ){
                // byte[] fileBytes = req.mAttachment.getBytes();
                byte[] fileBytes = Base64.getDecoder().decode(req.cAttachment);
                // String fileName = req.mAttachment.queryParams("name");
                fileURL = googleDriveService.uploadFile(fileBytes, "buzzCommentImage"); // may have to change req.mAttachment here -- this is just the fileName being passed through
                System.out.println(fileURL);
                response.status(200);
                // return gson.toJson(new StructuredResponse("ok", "Added attachment- newId: " + out, null));
            }

            // Authenticated users
            int ret = db.cInsertRow(req.cMessage, fileURL, req.cLink, idx, uID);
            if (ret == -1) {
                return gson.toJson(new StructuredResponse("error", "error performing insertion", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", "" + ret, req.cMessage));
            }
            // if req.aLink == null --> post message as done before

            // if req.aLink != null --> post current post to tblData, capture the post Id (called id), call the aInsertOne to attachment and then set commentID = null
        });

        // GET route that returns all comments specific to a post
        Spark.get("/messages/comments/:id", (request, response) -> {
            int idx = Integer.parseInt(request.params("id"));
             
            // SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // Ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            // Get the session token from the request headers
            String sessKey = request.headers("Authorization");
            
            // Look up the user's session information in the sessions hashmap
            int uID = -1;
            if (MyHashMap.getFromSessionMap(sessKey) != null) {
                Integer temp = MyHashMap.getFromSessionMap(sessKey);
                uID = temp.intValue();
            }

            // If the user is not authenticated, redirect to the login page
            if (uID == -1) {
                response.redirect("/login");
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }

            System.out.println("post ID number is: " + idx);
            if (db.cSelectAll(idx) == null) {
                return gson.toJson(new StructuredResponse("error, comments for post", idx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, db.cSelectAll(idx)));
            }
        });

        // GET route that returns a single comment
        Spark.get("/comments/:commentID", (request, response) -> {
            // int idx = Integer.parseInt(request.params("id"));
            int cIdx = Integer.parseInt(request.params("commentID"));
            
            // SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // Ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            // Get the session token from the request headers
            String sessKey = request.headers("Authorization");
            
            // Look up the user's session information in the sessions hashmap
            int uID = -1;
            if (MyHashMap.getFromSessionMap(sessKey) != null) {
                Integer temp = MyHashMap.getFromSessionMap(sessKey);
                uID = temp.intValue();
            }

            // If the user is not authenticated, redirect to the login page
            if (uID == -1) {
                response.redirect("/login");
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }

            System.out.println("commentID number is: " + cIdx);
            DataRow data = db.cSelectOne(cIdx);
            if (data == null) {
                return gson.toJson(new StructuredResponse("error", cIdx + " not found", null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, data));
            }
        });

        // PUT route for for changing/ editing the message for a specfic comment
        Spark.put("/comments/:commentID", (request, response) -> {
            int cIdx = Integer.parseInt(request.params("commentID"));
            SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
            // Ensure status 200 OK, with a MIME type of JSON
            response.status(200);
            response.type("application/json");

            // Get the session token from the request headers
            String sessKey = request.headers("Authorization");
            
            // Look up the user's session information in the sessions hashmap
            int uID = -1;
            if (MyHashMap.getFromSessionMap(sessKey) != null) {
                Integer temp = MyHashMap.getFromSessionMap(sessKey);
                uID = temp.intValue();
            }

            // If the user is not authenticated, redirect to the login page
            if (uID == -1) {
                response.redirect("/login");
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }

            String fileURL = null;
            // need to convert req.mAttachment to binary then to a file to put in Google Drive
            if ( req.cAttachment != null ){
                byte[] fileBytes = Base64.getDecoder().decode(req.cAttachment);
                fileURL = googleDriveService.uploadFile(fileBytes, "buzzNewCommentImage"); // may have to change req.mAttachment here -- this is just the fileName being passed through
                System.out.println(fileURL);
                response.status(200);
                // return gson.toJson(new StructuredResponse("ok", "Added attachment- newId: " + out, null));
            }

            // Valid access
            int result = db.cUpdateOne(cIdx, uID, req.cMessage, fileURL, req.cLink);
            if (result == -1) {
                return gson.toJson(new StructuredResponse("error", "unable to update row " + cIdx, null));
            } else {
                return gson.toJson(new StructuredResponse("ok", null, result));
            }
        });

        Spark.post("/logout", (request, response) -> {
            try {
                
                SimpleRequest req = gson.fromJson(request.body(), SimpleRequest.class);
                // Ensure status 200 OK, with a MIME type of JSON
                response.status(200);
                response.type("application/json");
                
                // Get the session token from the request headers
                String sessKey = request.headers("Authorization");
                
                // Look up the user's session information in the sessions hashmap
                int uID = -1;
                if (MyHashMap.getFromSessionMap(sessKey) != null) {
                    Integer temp = MyHashMap.getFromSessionMap(sessKey);
                    uID = temp.intValue();
                }

                // If the user is not authenticated, redirect to the login page
                if (uID == -1) {
                    response.redirect("/login");
                    return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
                }

                //Authorized session 
                int removedUser = -1;
                Integer temp = MyHashMap.deleteFromSessionMap(sessKey);
                removedUser = temp.intValue();

                if (removedUser != -1) {
                    return gson.toJson(new StructuredResponse("ok", "logout success, session terminated", removedUser));
                } else {
                    return gson.toJson(new StructuredResponse("ok", "logout failed, user not removed", null));
                }
                
            } catch (Exception e) {
                // Error occurred
                e.printStackTrace();
                return gson.toJson(new StructuredResponse("error", "Error authenticating", null));
            }
        });
        
    }

    private static final String DEFAULT_PORT_DB = "5432";
    private static final int DEFAULT_PORT_SPARK = 4567;

    /**
     * Get a fully-configured connection to the database, or exit immediately
     * Uses the Postgres configuration from environment variables
     * 
     * NB: now when we shutdown the server, we no longer lose all data
     * 
     * @return null on failure, otherwise configured database object
     */
    private static Database getDatabaseConnection() {
        if (System.getenv("DATABASE_URL") != null) {
            // System.out.println("in null if");
            try {
                return Database.getDatabase(System.getenv("DATABASE_URL"), DEFAULT_PORT_DB);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        Map<String, String> env = System.getenv();
        String ip = env.get("POSTGRES_IP");
        String port = env.get("POSTGRES_PORT");
        String user = env.get("POSTGRES_USER");
        String pass = env.get("POSTGRES_PASS");
        return Database.getDatabase(ip, port, "", user, pass);
    }

    /**
     * Set up CORS headers for the OPTIONS verb, and for every response that the
     * server sends. This only needs to be called once.
     * 
     * @param origin  The server that is allowed to send requests to this server
     * @param methods The allowed HTTP verbs from the above origin
     * @param headers The headers that can be sent with a request from the above
     *                origin
     */
    private static void enableCORS(String origin, String methods, String headers) {
        // Create an OPTIONS route that reports the allowed CORS headers and methods
        Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        // 'before' is a decorator, which will run before any
        // get/post/put/delete. In our case, it will put three extra CORS
        // headers into the response
        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }

    /**
     * Get an integer environment variable if it exists, and otherwise return the
     * default value.
     * 
     * @param @envar     The name of the environment variable to get.
     * @param defaultVal The integer value to use as the default if envar isn't
     *                   found
     * 
     * @returns The best answer we could come up with for a value for envar
     */
    static int getIntFromEnv(String envar, int defaultVal) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get(envar) != null) {
            return Integer.parseInt(processBuilder.environment().get(envar));
        }
        return defaultVal;
    }

    public void testWillFail() {
        // assertFalse(true);
    }

}