### Running Admin App Unit Tests
export POSTGRES_IP=<server> POSTGRES_PORT=5432 POSTGRES_USER=<user> POSTGRES_PASS=<pw> && mvn package

### Running Admin-CLI
mvn clean </br>
export POSTGRES_IP=<server> POSTGRES_PORT=5432 POSTGRES_USER=<user> POSTGRES_PASS=<pw> && mvn package </br>
POSTGRES_IP=<server> POSTGRES_PORT=5432 POSTGRES_USER=<user> POSTGRES_PASS=<pw> mvn exec:java </br>

### Phase 2 Admin Artifacts
https://bitbucket.org/cse216git/teamrepo/raw/0fcba17ac3bf68cfcbddf0110c8f7bb2fc2922aa/admin-cli/artifacts/Phase1/edu/lehigh/cse216/yen225/admin/App.html</br>
https://bitbucket.org/cse216git/teamrepo/raw/0fcba17ac3bf68cfcbddf0110c8f7bb2fc2922aa/admin-cli/artifacts/Phase1/edu/lehigh/cse216/yen225/admin/Database.html</br>
https://bitbucket.org/cse216git/teamrepo/raw/0fcba17ac3bf68cfcbddf0110c8f7bb2fc2922aa/admin-cli/artifacts/Phase1/edu/lehigh/cse216/yen225/admin/Database.RowData.html</br>
javadocs: admin-cli/api-docs