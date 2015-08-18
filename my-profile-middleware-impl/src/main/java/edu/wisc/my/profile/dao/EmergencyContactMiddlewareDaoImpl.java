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

public class EmergencyContactMiddlewareDaoImpl implements EmergencyContactMiddlewareDao {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  private JdbcTemplate jdbcTemplate;  
  @Autowired
  private LocalContactMiddlewareDao lcdao;
  
  @Autowired
  private EmergencyPhoneNumberDao pNumDao;
  
  public EmergencyContactMiddlewareDaoImpl(DataSource ds) {
    jdbcTemplate = new JdbcTemplate(ds);
  }

  @Override
  public ContactInformation[] getData(String netId) {
    ContactInformation[] cis = jdbcTemplate.query("select PERSONPROFILE.PERSONPROFILE.GET_PERSON_PROFILE( ? ) from dual", new EmergencyContactInfoResultSetExtractor(), netId);
    return cis;
  }

  @Override
  public ContactInformation[] setData(String netId, ContactInformation[] emergencyContacts)
      throws Exception {
    //TODO: Fix this gross hack
    ContactInformation localInfo = lcdao.getContactInfo(netId);
    TypeValue[] phoneNumbers = pNumDao.getPhoneNumbers(netId);
    JSONObject json = ContactInformationMapper.convertToJSONObject(emergencyContacts, localInfo, phoneNumbers);
    json.put("NETID", netId);
    if(logger.isTraceEnabled()) {
      logger.trace("Saving the following JSON: " + json.toString());
    }
    String result = jdbcTemplate.query("select PERSONPROFILE.PERSONPROFILE.SAVE_PERSON_PROFILE( ? ) from dual", new MiddlewareUpdateExtractor(), json.toString());
    
    if(MiddlewareUpdateExtractor.SUCCESS.equals(result)) {
      //return saved content
      return emergencyContacts;
    } else {
      throw new Exception("There was an issue saving the emergency contacts");
    }
    
  }

}
