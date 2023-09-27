# Phase 1' Sprint 7 - PM Report Template

## Team Information [10 points]

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


## Beginning of Phase 1' [20 points]
Report out on the Phase 1 backlog and any technical debt accrued during Phase 1.
1. What required Phase 1 functionality was not implemented and why? </br>
Throughout phase 1 the group faced some challenges and “bumps” that caused some slight delay. During sprint 6 our backend had many issues trying to connect to Dokku and getting it running for our web front end. Once the backend was able to get working our web front end wasn’t able to get the “GET” function working to retrieve messages, sent to the database, to display on the main page. At the end of Phase 1, during sprint 7, the backend was having issues fixing the previous sprint’s issue with the “GET” function. Eventually they were able to get it functioning and the web was able to display messages. However a new issue arrived in which Backend and Web didn’t know how to send the “likes” to the database. By the end of the sprint 7 week they were able to fix it and get it working. So all tasks were completed and nothing was left unfinished. 

1. What technical debt did the team accrue during Phase 1?</br>
	With the time crunch that occurred at the end of sprint 6 it was later discovered that the backend was creating its own tables and not actually fully connecting to the database. This was causing the “GET” function to not work properly for our front end and also was the reason that the messages weren’t retrieved from the database and displayed. 

## End of Phase 1' [20 points]
Report out on the Phase 1' backlog.

1. What required Phase 1 functionality still has not been implemented or is not operating properly and why?</br>
	Everything is working properly and nothing is no longer unfinished or not operating.

2. What technical debt remains?</br>
	In the “Admin App” the “getID()” function returns an ID based on the message it searches for, we may run into the problem with identical messages as they’re not accounted for yet. Also, on our web front end, the main page doesn’t immediately display newly added messages, you have to refresh the page so that the new messages are displayed. At the end of phase 1 the backend accidentally merged the pre-master branch into the backend. This may cause future problems but currently holds no issues.


## Role reporting [50 points]
Report-out on each team members' activity, from the PM perspective (you may seek input where appropriate, but this is primarily a PM related activity).

1. If there was any remaining Phase 1 functionality that needed to be implemented in Phase 1', what did the PM do to assist in the effort of getting this functionality implemented and operating properly?</br>
	No functionality that was needed to be implemented in Phase 1 remained unfinished. 

### Back-end
What did the back-end developer do during Phase 1'?</br>
	During Phase 1 the backend developer worked hard to get our initial backend program up and running. They were able to get the Dokku app up and running for the web front as well as connecting to the database. In their program they got the Get, Put, Delete, and Post functions working properly for the front-end so that the web front end could post new messages to the database, retrieve messages, update likes, and even added in the function for deleting messages for future use. They also made sure to create unit tests for their code and created javadocs for their code.

### Admin
What did the admin front-end developer do during Phase 1'?</br>
	The Admin front-end developer kickstarted the project by creating the database in ElephantSQL and creating our initial table so that messages could be entered into the database as well as a like counter. The admin also made sure that the database could do its regular functionalities such as create tables, delete them, create and delete messages. They also created user stories and their respective javadocs as well as unit tests.

### Web
What did the web front-end developer do during Phase 1'?</br>
	Our web front end created our web page that presented our app. They used flutter to create the page and all the functionalities and buttons. They made sure the web page had an add message button that would direct you to a new page to create said message, post that message, and also add and unadd a like from all displayed messages. They worked together with the backend so that all functions were working properly with one another. Web also created their javadocs as well as some user stories as well as unit tests. 

### Mobile – No Mobile
What did the mobile front-end developer do during Phase 1'?</br>
</br>
**It should be noted that all team members helped in the creation of all the project’s artifacts such as routes, ERD, System Diagram, and FSM. We also tried our best to help with integration tests, debug diagnosis and problem resolution. Especially when our backend ran into some last minute issues after a merge into pre-master, which was fixed shortly after and everything continues to run smoothly.**

### Project Management
Self-evaluation of team performance

1. When did your team meet with your mentor, and for how long?
	The team met with our mentor on Friday morning from 11:30am to 12:00pm. So a 30 minute meeting with our mentor. 

2. Describe how the team worked together in Phase 1'. Were all members engaged? Was the work started early in the week or was there significant procrastination?
	All work was started as soon as possible. Members still had some exams they had to initially focus on but everyone still got most functionality working by Friday morning the latest on sprint 6, excluding our web not being able to use “Get” function properly. There was no significant procrastination and members who needed help consistently found themselves at office hours to fix issues they were running into. Everyone was extremely engaged through consistent updates to one another, either through our personal Imessage group chat or Slack channel. This way everyone knew how the app progress was going for all members. 

3. Describe any concerns you have about the prospects for success moving forward? What steps can the team take to reduce your concern?
	I’d say the current team dynamic and communication is going extremely well. We set dates for one another that are also pretty early on so that extremely pressing issues that come up can be fixed on time. The only concern I have is on myself and hoping that I can keep up with the group once I begin coding myself and step away from the PM role. 
