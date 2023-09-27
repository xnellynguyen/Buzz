# Phase 1 Sprint 6 - PM Report Template

## Team Information [25 points]

Team Information:
* Number: 6
* Name: 6ware Engineers
* Mentor: <Ayon Bhowmick, ayb224@lehigh.edu>

Team Roles:
* Project Manager: <Jesus Gutierrez, jeg325@lehigh.edu>
* Backend developer: <Riley Dembo, rmd225@lehigh.edu>
* Admin developer: <Nelly Nguyen, yen225@lehigh.edu>
* Web/Mobile developer: <Carson Stotler, crs225@lehigh.edu>

Essential links for this project:
* Team's Dokku URL(s) (live web front-end link)
    * http://2023sp-phase1-team6.dokku.cse.lehigh.com
* Team's software repo (bitbucket)
    * https://bitbucket.org/cse216git/teamrepo/src/master/
* Team's Trello board
    * https://trello.com/b/k6TDKKw5/the-buzz-team-6


## Role reporting [75 points total, 15 points each (teams of 4 get 15 free points)]
Report-out on each role, from the PM perspective.
You may seek input where appropriate, but this is primarily a PM related activity.

### Back-end
1. Overall evaluation of back-end development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)</br>
The development of the backend went roughly but they still completed their tasks and completed their portion of the sprint by our completed and desired decline of friday. Backend utilized trello, the deliverables document, and kept in constant contact with the entire team to let us all know how their progress was going in terms of what they were doing. They also attended office hours consistently and even used in class team meetings to speak to the professor about issues they were running into.

2. List your back-end's REST API endpoints</br>
Get: /messages and /messages/:id </br>
Post: /messages </br>
Put: /messages/:id </br>
Delete: /messages/:id </br>

3. Assess the quality of the back-end code</br>
The backend code quality is well structured. They use good javadoc comments to explain what their functions and overall code does. When run it seems to be functioning well aside from some mentioned malfunctions but they seemed to be narrowed down to their own VSCode IDE not working properly. 

4. Describe the code review process you employed for the back-end</br>
For the backend I asked them to run their unit tests for me to show that their code was working as intended while also taking a look at their code and checking to make sure that they had, if not javadoc, some comments in their code to explain how their code works. I also asked them to describe the technical debt they have from their time working on the project. 

5. What was the biggest issue that came up in code review of the back-end server?</br>
This was more on my end, but I did not see that the backend had every file in their own backend folder so when they pull requested to pre-master and I approved it, their pom.xml file went into the main area of the pre-master branch and their backend files went into an src folder instead of a “backend” folder. Also, the unit tests they showed were on a different environment as the packages they were using caused errors in their VSCode environment. 

6. Is the back-end code appropriately organized into files / classes / packages?</br>
The back-end code is organized into files, classes, and packages however all those files aren’t into one “backend” folder. I had/have to go in and organize the pre-master branch of the repository so that when it is merged into the master branch some files, such as the pom.xml, aren’t in the main directory of the master and pre-master branch. However in the backend branch, all files are now in a backend folder. 

7. Are the dependencies in the pom.xml file appropriate? Were there any unexpected dependencies added to the program?</br>
All dependencies in the pom.xml are appropriate. No unexpected dependencies were added to the program.

8. Evaluate the quality of the unit tests for the back-end</br>
The Unit tests were well structured and ran smoothly. None failed when they were run.

9. Describe any technical debt you see in the back-end</br>
The packages the coder used resulted in errors in their VSCode environment even though there seems to be no actual errors. Also some of their imported libraries aren’t working as well as the coder would have liked.

### Admin

1. Overall evaluation of admin app development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)</br>
The admin app development went by really well and smoothly. Trello was used for the admin to be aware of what deliverables they had to complete for the week. Admin completed their admin tasks by Wednesday so pretty early on. 

2. Describe the tables created by the admin app</br>
The tables created by the admin are the basic data tables that can hold a subject and message. They also receive a row ID as they’re created. The data tables also have a “like” counter as well. They’re initially set to 0 when new messages, or rows, are created. Other than that the admin app runs all the same commands to create and delete tables, create and delta messages, view tables, and more.

3. Assess the quality of the admin code</br>
The quality of the admin code is well coded throughout the multiple files. As well as being organized, the coder added multiple comments to explain what their code does and what each function is for. 

4. Describe the code review process you employed for the admin app</br>
For the code review process I asked them to run their unit tests for me while I also looked over their code to make sure it was commented sufficiently. In their test they demonstrated how each data table was created, how to insert and delete rows/messages, and also how you could view them. They also ran their unit tests and showed proof that they all ran with no errors.

5. What was the biggest issue that came up in code review of the admin app?</br>
No issues came up for the admin app in the code review for this sprint.

6. Is the admin app code appropriately organized into files / classes / packages?</br>
The admin app code was indeed organized neatly into files, classes, and packages.

7. Are the dependencies in the pom.xml file appropriate? Were there any unexpected dependencies added to the program?</br>
All dependencies in the pom.xml file are appropriate and there were no unexpected dependencies that were added to the program.

8. Evaluate the quality of the unit tests for the admin app</br>
All unit tests were well coded and they all ran successfully with no errors.

9. Describe any technical debt you see in the admin app</br>
The Admin’s technical debt would have to be that the “getID()”  function returns an ID based on the message. Later down in the process we might run into a problem for identical messages.

### Web

1. Overall evaluation of Web development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)</br>
Web development went pretty well. Trello was used initially so the coder knew what deliverables were expected of them. They got to work on the interface immediately and got as much of the interface as they could get functioning without the backend server. Web also constantly kept the group updated on their progress and even showed the group their functionality during group time during class both Tuesday and Thursday. 

2. Describe the different models and other templates used to provide the web front-end's user interface</br>
Web front-end’s user interface was made to look like the mock up image that they had created the previous week. It was made to be somewhat like twitter but without all the small details that twitter offers such as retweets, quote retweets, analytics, date posted, etc. Flutter was used in the creation of the web front-end so that web front end could also use that same code to help create the mobile front-end since they’re working on both.

3. Assess the quality of the Web front-end code</br>
Web front-end code is well written with multiple comments. They are lacking a bit in some comments but they are looking to polish that up in the last sprint of phase 1, before handing it off to the next web front-end coder. There’s also some unneeded code that the coder used to help practice but it will be cleared up before the next phase.

4. Describe the code review process you employed for the Web front-end</br>
For the web front end I asked them to show proof that their unit tests functioned to which they provided them so. Since I also repeatedly saw that their code mostly functioned throughout the week, I knew that it was pretty reliable for a merge into pre-master.

5. What was the biggest issue that came up in code review of the Web front-end?</br>
The biggest issue seemed to be that the web front-end’s GET didn’t seem to function as intended. Messages created would in fact post onto the database and would show up there. However, the messages created by the web front-end would not be retrieved from the database and shown on the web’s main homepage where all messages should be displayed. 

6. Is the Web front-end code appropriately organized into files / classes / packages?</br>
The web front-end code is appropriately organized into files, classes, and packages.

7. Are the dependencies in the package.json file appropriate? Were there any unexpected dependencies added to the program?</br>
All dependencies in the package.json file are appropriate with no unexpected dependencies added to the program. 

8. Evaluate the quality of the unit tests for the Web front-end</br>
The unit tests of the web front-end are well written and clear. They are missing a bit of comments to explain what each does, however they will be adding those comments in the next sprint. Also all tests ran and passed.

9. Describe any technical debt you see in the Web front-end</br>
The technical debt in the web front-end currently is that our coder has a lot of unneeded code and files that they used to practice. They could use some refactoring to limit repeated code.

### Mobile // Ignore. We are a 4 person group with our web and mobile combined.

1. Overall evaluation of Mobile development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
2. Describe the activities that comprise the Mobile app
3. Assess the quality of the Mobile code
4. Describe the code review process you employed for the Mobile front-end
5. What was the biggest issue that came up in code review of the Mobile front-end?
6. Is the Mobile front-end code appropriately organized into files / classes / packages?
7. Are the dependencies in the pubspec.yaml (or build.gradle) file appropriate? Were there any unexpected dependencies added to the program?
8. Evaluate the quality of the unit tests for the Mobile front-end here
9. Describe any technical debt you see in the Mobile front-end here

### Project Management
Self-evaluation of PM performance

1. When did your team meet with your mentor, and for how long?</br>
This week the entire team did not meet with our mentor collectively. The only available time that the whole team is able to meet together with our mentor is normally Fridays at 11:30am for 30 minutes. However, with spring break approaching some members went home as early as Thursday night and Friday morning. Team members still met with our mentor during his office hours earlier in the week we just didn’t meet collectively. 

2. Describe your use of Trello.  Did you have too much detail?  Too little?  Just enough? Did you implement policies around its use (if so, what were they?)?</br>
At the beginning of the week once the sprint was released I created cards for every member of the team and added their required deliverables for the week. I also gave the backend a soft date for when I wanted their code to be done so that our web and admin could connect to that backend for their code. On top of that I messaged the group in our iMessage chat to let them know that I had created the cards with their required deliverables. I asked them to mark off the tasks they completed so I was aware of how much was completed, however they did not do so. I probably should have asked them to do so more often but all members still got their tasks done nonetheless. 

3. How did you conduct team meetings?  How did your team interact outside of these meetings?</br>
During our in class meetings members expressed how far they were in their tasks. For those who weren’t done, they also expressed the complications or issues they were running into about their code. They also made us all aware that they were going to office hours to ask for help on their issues, reading and posting on piazza, as well and going up to the professor during class for help. Outside of meetings members gave updates on how their end was going whenever they had any updates, they normally did so at least once a day. 

4. What techniques (daily check-ins/scrums, team programming, timelines, Trello use, group design exercises) did you use to mitigate risk?</br>
To mitigate risk we used our Trello board to show what tasks every team member had. On top of that all team members who weren’t done with their tasks gave daily updates on their progress without me having to ask them to do so. 

5. Describe any difficulties you faced in managing the interactions among your teammates? Were there any team issues that arose?</br>
No team issues arose as all members respect each other and were understanding of how busy our schedules were the past 2 weeks with exams and other courses. Only issues that arose was how our backend couldn’t get their backend up and running until Friday which was pretty late for our web frontend but backend was consistently going to get help to try and resolve the issue on top of their other course load so we all understood. 

6. How well did you estimate time during the early part of the phase?  How did your time estimates change as the phase progressed?</br>
I estimated that our backend would at least be complete by Wednesday night, however that was not possible as they did go and receive help but seemed to stumble on issues so their end was not up and running until Friday. On top of that, everyone was eager to begin their spring break so we had a smaller window of two days to complete all work and meet to record our group video. Although backend was a bit behind, web and admin worked ahead of schedule and completed early so it actually all balanced out well and left us finishing on our desired time competition. 

7. What aspects of the project would cause concern for your customer right now, if any?</br>
Although our front end is able to access the database and post messages to the database, the get function seems to not be functioning properly as our messages aren’t being displayed in the user interface main page where they should be. 

8. What is your biggest concern as you think ahead to the next phase of the project?</br>
The current biggest concern is that the main front end web page isn’t displaying the messages we expect it to display from the database. We should be able to fix it in this next sprint and be okay for future phases but if we can’t fix it then it will cause major concern and issues for the future.

9. Describe the most significant obstacle or difficulty your team faced.</br>
The difficulty was getting our backend up and running. Our backend was consistently seeking help from multiple sources and yet it still took a little while. This made is so our front end had to work on their code without testing through the backend at first, and once it was up and running trying to fix any bugs or errors before our team recording. 
