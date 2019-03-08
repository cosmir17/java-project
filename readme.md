You can run from Maven
mvn exec:java -Dexec.args="src/test/resources/files"

You can run every test (TDD + BDD) by typing 'mvn clean verify'

You can create your jar file by typing 'mvn clean package', then go to 'target' folder.
run 'java -jar java-project-1.0-SNAPSHOT.jar "../src/test/resources/files"'

This app searches every file regardless of capatalisation of letters.