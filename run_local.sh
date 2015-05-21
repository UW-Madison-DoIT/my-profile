#!/bin/bash
if [ "$#" = "0" ]; then
 mvn -Djava.awt.headless=true clean install
else 
 mvn -Djava.awt.headless=true $@
fi

pushd my-profile-webapp
mvn tomcat7:run-war
popd

