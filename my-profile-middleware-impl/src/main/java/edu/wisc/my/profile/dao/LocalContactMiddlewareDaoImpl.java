package edu.wisc.my.profile.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import edu.wisc.my.profile.model.ContactInformation;

public class LocalContactMiddlewareDaoImpl implements MiddlewareDao {
  
  private JdbcTemplate jdbcTemplate;  
  
  public LocalContactMiddlewareDaoImpl(DataSource ds) {
    jdbcTemplate = new JdbcTemplate(ds);
  }

  @Override
  public ContactInformation getContactInfo(String netId) {
    ContactInformation ci = jdbcTemplate.query("select PERSONPROFILE.PERSONPROFILE.GET_PERSON_PROFILE( ? ) from dual", new ContactInfoResultSetExtractor(), netId);
    return ci;
  }

  @Override
  public ContactInformation setContactInfo(String netId, ContactInformation contactInformation) {
    // TODO Auto-generated method stub
    return new ContactInformation();
  }

  @Override
  public ContactInformation getData(String netId) {
    // TODO Auto-generated method stub
    return new ContactInformation();
  }

}
