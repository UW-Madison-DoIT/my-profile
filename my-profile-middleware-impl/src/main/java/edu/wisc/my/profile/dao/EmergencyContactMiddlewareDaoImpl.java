package edu.wisc.my.profile.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.wisc.my.profile.model.ContactInformation;

public class EmergencyContactMiddlewareDaoImpl implements EmergencyContactMiddlewareDao {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  private JdbcTemplate jdbcTemplate;  
  
  public EmergencyContactMiddlewareDaoImpl(DataSource ds) {
    jdbcTemplate = new JdbcTemplate(ds);
  }

  @Override
  public ContactInformation[] getData(String netId) {
    ContactInformation[] cis = jdbcTemplate.query("select PERSONPROFILE.PERSONPROFILE.GET_PERSON_PROFILE( ? ) from dual", new EmergencyContactInfoResultSetExtractor(), netId);
    return cis;
  }

  @Override
  public ContactInformation[] setData(String netId, ContactInformation[] contactInformation)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

}
