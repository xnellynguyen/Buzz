## **Phase 3**  

### Team Information ##
* Team Number: 6
* Team Name: 6ware Engineers
* Team Members:</br>
        * Jesus Gutierrez, jeg325@lehigh.edu, Admin</br>
        * Riley Dembo, rmd225@lehigh.edu, Mobile</br>
        * Nelly Nguyen, yen225@lehigh.edu, PM</br>
        * Carson Stotler, crs225@lehigh.edu, Backend  </br>

## Functionality Description Phase 3' ##
As of phase 3', we have link and image attached to a post fully working. Admin has the validation functionality complete. Backend has google drive api -- service account implemented for storage. Backlog: caching is not fully implemented, comments is not fully implemented (however, this is phase 2 backlog and professor said we don't have to worry about it due to the dokku issue), and other users' profile (same reason).

## Functionality Description Phase 3 ##
As of Phase 3, due to ID token issue (which is explained in our sprint 12 video, we suspect it is a problem related to the Dokku reboot), we were not able to implement the phase 3 functionalities thus far (caching, google drive api, link, picture on posts and comments). We, however, have backend code, just need to test once the ID token issue is resolved. Mobile has pseudo code to help kickstart development once the issue is resolved. Scrolling is resolved, however. Profile and comments are still backlog items. 

## Functionality Description Phase 2' ##
As of Phase 2', our front-end is not fully functional as comments is the main concern that is not fully working by time of submission. Displaying the vote count doesn't work perfectly in front-end, but they just need to have the right routes to get the like count. Also, the functionality of scrolling stopped working for some reason, that may be a bug in the code. So a customer may be concern with the overall output from this phase.

## Functionality Description ##
As of Phase 2, our app has OAuth working properly, but due to the time consumption of getting that to work, other functionalities are not fully working but will be getting worked on this week. Individual users, their pages, comments, and new vote count will be worked on this coming sprint. In admin, the table for Votes and Comments is not fully complete as it hasn't been populated from frontend, but will be adjusted as needed as well this sprint. In backend, we want to be able to keep track of invalidated users and make sure they are unable to log back in, and that is a focus for the backend next sprint as well.

## Admin App ##
mvn clean </br>
export POSTGRES_IP=<server> POSTGRES_PORT=5432 POSTGRES_USER=<user> POSTGRES_PASS=<pw> && mvn package </br>
POSTGRES_IP=<server> POSTGRES_PORT=5432 POSTGRES_USER=<user> POSTGRES_PASS=<pw> mvn exec:java </br>

## Back-End Server ##
Running on Dokku instructions: </br>
1. On local host, run 'git push dokku backend-dokku:master' when there are changes </br>
2. dokku config:set 'appname' CORS_ENABLED=true </br>
3. Use dokku ps:start 'appname' </br>
4. Use dokku ps:restart 'appname' to stop and start (without doing a rebuild) </br>
5. dokku config:set 'appname' CORS_ENABLED=false </br>
6. Use dokku ps:stop 'appname' </br></br>

Running locally instructions: </br>
1. From the backend folder of the backend branch: run 'PORT=8998 DATABASE_URL=postgres://<user>:<password>@<???>.db.elephantsql.com/<user> java -jar ./target/backend-1.0-SNAPSHOT-jar-with-dependencies.jar'

## Developer Documentation Links/Paths ##
Docs/README-phase2.md</br>
admin-cli/README.md</br>
mobile/README.md</br>
backend/README.md  




## **Phase 1**  


### Team Information ##
* Team Number: 6
* Team Name: 6ware Engineers
* Team Members:</br>
        * Jesus Gutierrez, jeg325@lehigh.edu</br>
        * Riley Dembo, rmd225@lehigh.edu</br>
        * Nelly Nguyen, yen225@lehigh.edu</br>
        * Carson Stotler, crs225@lehigh.edu

## Functionality Description ##
As of Phase 1 our app, "The Buzz", allows a user to create new messages(or "ideas") that include a title and message. These ideas are then posted into a database. On our main page all created messages are displayed as they are retrieved from the database. In this main page users can also like or remove their like from messages displayed. 

## Admin App ##
mvn clean </br>
export POSTGRES_IP=<server> POSTGRES_PORT=5432 POSTGRES_USER=<user> POSTGRES_PASS=<pw> && mvn package </br>
POSTGRES_IP=<server> POSTGRES_PORT=5432 POSTGRES_USER=<user> POSTGRES_PASS=<pw> mvn exec:java </br>

## Back-End Server ##
Running on Dokku instructions: </br>
1. On local host, run 'git push dokku backend-dokku:master' when there are changes </br>
2. dokku config:set 'appname' CORS_ENABLED=true </br>
3. Use dokku ps:start 'appname' </br>
4. Use dokku ps:restart 'appname' to stop and start (without doing a rebuild) </br>
5. dokku config:set 'appname' CORS_ENABLED=false </br>
6. Use dokku ps:stop 'appname' </br></br>

Running locally instructions: </br>
1. From the backend folder of the backend branch: run 'PORT=8998 DATABASE_URL=postgres://<user>:<password>@<???>.db.elephantsql.com/<user> java -jar ./target/backend-1.0-SNAPSHOT-jar-with-dependencies.jar'

## Developer Documentation Links/Paths ##
Docs/README-phase1.md</br>
admin-cli/README.md</br>
mobile/README.md</br>
backend/README.md