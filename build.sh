#! /bin/bash
set -e
cd kotoba
mvn clean install
mvn install:install-file -DgroupId=net.fiive.kotoba -DartifactId=kotoba-droid -Dversion=1.0-SNAPSHOT -Dpackaging=jar -Dfile=target/kotoba-1.0-SNAPSHOT.jar
cd ../kotoba-testng
mvn clean install
cd ../
cd kotoba-test
mvn clean install

