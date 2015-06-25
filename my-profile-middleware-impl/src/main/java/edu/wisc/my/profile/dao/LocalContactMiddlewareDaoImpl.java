package edu.wisc.my.profile.dao;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.wisc.my.profile.mapper.ContactInformationMapper;
import edu.wisc.my.profile.model.ContactInformation;

public class LocalContactMiddlewareDaoImpl implements MiddlewareDao {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  private JdbcTemplate jdbcTemplate;  
  
  public LocalContactMiddlewareDaoImpl(DataSource ds) {
    jdbcTemplate = new JdbcTemplate(ds);
  }

  @Override
  public ContactInformation getContactInfo(String netId) {
    ContactInformation ci = jdbcTemplate.query("select PERSONPROFILE.PERSONPROFILE.GET_PERSON_PROFILE( ? ) from dual", new LocalContactInfoResultSetExtractor(), netId);
    return ci;
  }

  @Override
  public ContactInformation setContactInfo(String netId, ContactInformation updatedContactInformation) {
    //hack until we refactor
    ContactInformation dbContactInfo = getContactInfo(netId);
    JSONObject json = ContactInformationMapper.mergeLocalContact(new ContactInformation[]{}, dbContactInfo, updatedContactInformation);
    json.put("NETID", netId);
    if(logger.isTraceEnabled()) {
      logger.trace("Saving the following JSON: " + json.toString());
    }
    ContactInformation ci = jdbcTemplate.query("select PERSONPROFILE.PERSONPROFILE.SAVE_PERSON_PROFILE( ? ) from dual", new LocalContactInfoResultSetExtractor(), json.toString());
    //return saved content
    return ci;
  }

  @Override
  public ContactInformation getData(String netId) {
    // TODO Auto-generated method stub
    return new ContactInformation();
  }

}
