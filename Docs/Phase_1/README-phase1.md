## Listing of User Stories for Admin and "Anonymous User" Personas" ##

### Admin User Story
#### User Story 8
1. As an **admin user**
2. I want to **be able to delete certain messages**
3. So I can **ensure that the messages on the platform are appropriate**
Test: Manual test -  An admin user can try to delete certain messages that might not meet the guidelines of the company. Check to see if it still exists. 

#### User Story 9
1. As an *admin user*
2. I want to **see messages, add a message, and like messages**
3. So I can **use the platform the way other users are**
Test: Manual - An admin user can try to post, like, remove a like. Check if the message has been posted, liked, or see if the like has been removed.

### Anonymous User Story
#### User Story 1
1. As a **user/employee (web visitor)**
2. I want to **add a message to my company's website**
3. So I can **communicate my ideas to other employees**
**Test**: Manual -- Anon. User clicks the add message button and fills out the relevant fields and pushes the post button to add a message to the website.

#### User Story 2
1. As a **user/employee (web visitor)**
2. I want to **be able to see all the previous messages that was posted**
3. So I can **see all ideas published to the company's website**
**Test**: Manual -- Anon. User scrolls and is able to see all the previous messages through a Get call.

#### User Story 3
1. As a **user/employee (web visitor)**
2. I want to **like a message that myself or another employee added to the website**
3. So I can **communicate to other employees that I like their ideas**
**Test**: Manual -- Anon. User likes a previous message and checks to see if the count increases.

#### User Story 4
1. As a **user/employee (mobile app visitor)**
2. I want to **add a message to my company's mobile app**
3. So I can **communicate my ideas to other employees**
**Test**: Manual -- Anon. User clicks the add message button and fills out the relevant fields and pushes the post button to add a message to the app.

#### User Story 5
1. As a **user/employee (mobile app visitor)**
2. I want to **be able to see all the previous messages that was posted**
3. So I can **see all ideas published to the company's mobile app**
**Test**: Manual -- Anon. User scrolls and is able to see all the previous messages through a Get call.

#### User Story 6
1. As a **user/employee (mobile app visitor)**
2. I want to **like a message that myself or another employee added to the mobile app**
3. So I can **communicate to other employees that I like their ideas**
**Test**: Manual -- Anon. User likes a previous message and checks to see if the count increases.

## Web/Mobile User Interface
Mobile User Interface:</br>
![Mobile User Interface](Images\BuzzMobileMockP1.png)</br>
Web User Interface:</br>
![Web User Interface](images\BuzzWebMockP1.png)


## Routes
| Route   | Path     | HTTP Verb | Purpose |
| ------- | -------- | --------- | ------- |
| Index/Read | /messages      | GET | Display all messages |
| Create     | /messages    | POST | Creates a new post |
| Show | /messages/:id  | GET | Shows one specified post |
| Update | /likes/:id | PUT | Changes "like count" of a single post |

## The Buzz Artifacts 
System Diagram:</br>
![Team 6 System Diagram](images\phase1.sd.png)</br>
ERD:</br>
![Team 6 ERD](images\ERDPhase1.png)</br>
FSM:</br>
![Team 6 FSM](images\phase1.smd.png)</br>

## Descriptions of Tests

### Admin
1. Creating a table test</br>
    a. Create a table, test if select all is not null
2. Deleting a table test<br>
    a. Delete a table, test if select all is null
3. Inserting a row test</br>
    a. Add a row, check if row inserted return 1
4. Establishing a connection to the db test</br>
    a. Connect to database, check if database is not null