package edu.wisc.my.profile.dao;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.wisc.my.profile.mapper.ContactInformationMapper;
import edu.wisc.my.profile.model.ContactInformation;

public class LocalContactMiddlewareDaoImpl implements LocalContactMiddlewareDao {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private EmergencyContactMiddlewareDao ecdao;
  
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
  public ContactInformation setContactInfo(String netId, ContactInformation updatedContactInformation) throws Exception {
    //hack until we refactor
    ContactInformation[] emergencyContacts = ecdao.getData(netId);
    JSONObject json = ContactInformationMapper.convertToJSONObject(emergencyContacts, updatedContactInformation);
    json.put("NETID", netId);
    if(logger.isTraceEnabled()) {
      logger.trace("Saving the following JSON: " + json.toString());
    }
    String result = jdbcTemplate.query("select PERSONPROFILE.PERSONPROFILE.SAVE_PERSON_PROFILE( ? ) from dual", new MiddlewareUpdateExtractor(), json.toString());
    
    if(MiddlewareUpdateExtractor.SUCCESS.equals(result)) {
      //  return saved content
      return updatedContactInformation;
    } else {
      throw new Exception("There was an issue saving the local contact information");
    }
    
    
  }
}
