FROM tomcat:8-jre7
MAINTAINER Tim Levett <tim.levett@wisc.edu>

ADD ./my-profile-webapp/target/profile.war /usr/local/tomcat/webapps/

ADD tomcat-users.xml /usr/local/tomcat/conf/
