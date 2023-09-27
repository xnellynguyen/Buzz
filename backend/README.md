# backend server


# Running locally instructions:
1. mvn clean
2. mvn package
3. PORT=8998 DATABASE_URL=<URL> mvn exec:java

# Running on dokku 
1. ssh into dokku
2. dokku ps:start 2023sp-phase1-6
3. dokku config:set 2023sp-phase1-6 CORS_ENABLED=true

# Pushing to dokku
1. git checkout backend-dokku
2. git read-tree backend:backend
3. git status
4. git commit 
5. git push origin backend-dokku
6. git push dokku backend-dokku:master

# Getting sessionID from token
1. https://oauth2.googleapis.com/tokeninfo?id_token=


Java docs is located under backend/apidocs/