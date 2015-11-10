package edu.wisc.my.profile.dao;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.wisc.my.profile.mapper.ContactInformationMapper;
import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.model.TypeValue;

public class LocalContactMiddlewareDaoImpl implements LocalContactMiddlewareDao {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private EmergencyContactMiddlewareDao ecdao;
  
  @Autowired
  private EmergencyPhoneNumberDao pNumDao;
  
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
    TypeValue[] phoneNumbers = pNumDao.getPhoneNumbers(netId);
    JSONObject json = ContactInformationMapper.convertToJSONObject(emergencyContacts, updatedContactInformation, phoneNumbers);
    json.put("NETID", netId);
    logger.trace("Saving the following JSON: {}" + json);
    String result = jdbcTemplate.query("select PERSONPROFILE.PERSONPROFILE.SAVE_PERSON_PROFILE( ? ) from dual", new MiddlewareUpdateExtractor(), json.toString());
    
    if(MiddlewareUpdateExtractor.SUCCESS.equals(result)) {
      //  return saved content
      return updatedContactInformation;
    } else {
      throw new Exception("Could not save contact info for netid '" + netId + "'; "
          + "Contact information was " + updatedContactInformation + ".");
    }
    
    
  }
}
