# Delectable

The main purpose of this project is to practice object-oriented analysis, design and programming and build an application runs on a server and that various clients can access it through a REST interface.

Project description: Spring2016_ProjectDesciption.pdf
API Description: Spring2016_ProjectAPI.pdf

--------------------------------------
Minimum Requirement
--------------------------------------
1. JDK 7
2. Tomcat 7
3. Maven 3

---------------------------------------
Deployment: Project Delectable (Instuctino for Linux)
---------------------------------------
STEP 1: Download Project and Unzip
zip File: sudo unzip Delectable.zip 
or
tar.gz File: sudo tar –xvzf delectable.tar.gz 

STEP 2: STEP 3 will create a directory “Delectable”. Change your directory to this folder.
cd Delectable

STEP 3: Run build script “pom.xml”. This script will run and create coverage report and war file.
sudo mvn clean test jacoco:report package

STEP 3 will create:
Code Coverage Report: /target/site/jacoco/index.html
Test Report: /target/surefire-reports/TestDelectable.txt
War file: /target/delectable.war

STEP 4: Deploy war file on tomcat7
cd /target
sudo cp delectable.war /var/lib/tomcat7/webapps or 
whereever your tomcat 7 installation directory is.

STEP 7: Restart server
sudo service tomcat7 restart

Project is Deploy. Goto: http://localhost:8080/delectable
