Master branch build status: [![Build Status](https://travis-ci.org/UW-Madison-DoIT/my-profile.svg?branch=master)](https://travis-ci.org/UW-Madison-DoIT/my-profile)

### Running locally

Run `./run_local.sh` from the project root.

#### First-time setup

Follow these steps before attempting to run locally:
1. In `my-profile-webapp/src/pom.xml`, comment out the `my-profile-middleware-impl` dependency, and uncomment the `my-profile-local-contact-impl` dependency
2. In `my-profile-webapp/src/main/webapp/WEB_INF/context/applicationContext.xml`, comment out the 4 beans with `<constructor-arg ref="mw" />` and uncomment the ones with `<constructor-arg ref="uportal" />`
3. In the `my-profile-webapp/src/resources/`, create a file called `datasource.properties` and paste in the following content:
```
## Database Configuration
uportal.driverClassName=org.hsqldb.jdbc.JDBCDriver
uportal.url=jdbc:hsqldb:hsql://localhost:8887/uPortal
uportal.username=sa
uportal.password=
uportal.validationQuery=select 1 from INFORMATION_SCHEMA.SYSTEM_USERS
```



