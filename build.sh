#!/bin/bash
if [ "$#" = "0" ]; then
 mvn -Djava.awt.headless=true clean install
else 
 mvn -Djava.awt.headless=true $@
fi

rm -rf ~/tomcat/tomcat/webapps/profile.war ~/tomcat/tomcat/webapps/profile
cp ./my-profile-webapp/target/profile.war ~/tomcat/tomcat/webapps

