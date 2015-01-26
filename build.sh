#!/bin/bash

if [ "$#" = "0" ]; then
 mvn -Djava.awt.headless=true clean install
else 
 mvn -Djava.awt.headless=true $@
fi

if [ `echo $TC_HOME` != "" ]; then

rm -rf $TC_HOME/webapps/profile.war $TC_HOME/webapps/profile
cp ./my-profile-webapp/target/profile.war $TC_HOME/webapps

echo "WAR delivered to TC"

fi

exit 0;
