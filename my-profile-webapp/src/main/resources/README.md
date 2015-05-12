### Required properties files:

#### HRS Portlet dependencies
+ bnsemail-placeholder.properties - business email configuration
+ cypress-placeholder.properties - cypress configuration (not utilized but needed due to dependencies)
+ ps-placeholder.properties - people soft (hrs)
+ smime-keystore.jks - business email hand shake thing for certs

#### ISIS porltet dependencies*
+ datasource.properties - isis jdbc configuration

#### Temporary Setup for local/emergency Contact

Create an HSQL database

```sql
create table 
key_val (
 key varchar(30) primary key, 
 value varchar(4000)
);
```

Setup the datasource.properties with:
```
uportal.driverClassName=org.hsqldb.jdbc.JDBCDriver
uportal.url=jdbc:hsqldb:hsql://localhost:8887/uPortal
uportal.username=sa
uportal.password=
uportal.validationQuery=select 1 from INFORMATION_SCHEMA.SYSTEM_USERS
```