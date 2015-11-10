package edu.wisc.my.profile.dao;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.wisc.my.profile.mapper.ContactInformationMapper;
import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.model.TypeValue;

public class EmergencyPhoneNumberDaoImpl implements EmergencyPhoneNumberDao {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private EmergencyContactMiddlewareDao ecdao;
  
  @Autowired
  private LocalContactMiddlewareDao lcdao;
  
  private JdbcTemplate jdbcTemplate;  
  
  public EmergencyPhoneNumberDaoImpl(DataSource ds) {
    jdbcTemplate = new JdbcTemplate(ds);
  }
  
  @Override
  public TypeValue[] getPhoneNumbers(String netId) {
      TypeValue[] tv = jdbcTemplate.query("select PERSONPROFILE.PERSONPROFILE.GET_PERSON_PROFILE( ? ) from dual", new EmergencyPhoneNumberResultSetExtractor(), netId);
      return tv;
  }

    @Override
    public TypeValue[] setPhoneNumbers(String netId, TypeValue[] phoneNumbers) throws Exception {
        // Keep the hack alive as in EmergencyContactMiddleWareDaoImpl and LocalContactMiddlewareDaoImpl
        // Get the other info, add the new stuff and save the one object
        ContactInformation[] emergencyContacts = ecdao.getData(netId);
        ContactInformation localInfo = lcdao.getContactInfo(netId);
        TypeValue[] phNumberWithDate = phoneNumbers;
        for(TypeValue phoneNumber: phoneNumbers){
            phoneNumber.setLastModified(DateTime.now());
        }
        JSONObject json = ContactInformationMapper.convertToJSONObject(emergencyContacts, localInfo, phNumberWithDate);
        json.put("NETID", netId);
        logger.trace("Saving the following JSON: {}" + json);
        String result = jdbcTemplate.query("select PERSONPROFILE.PERSONPROFILE.SAVE_PERSON_PROFILE( ? ) from dual", new MiddlewareUpdateExtractor(), json.toString());
        
        if(MiddlewareUpdateExtractor.SUCCESS.equals(result)) {
          //return saved content
          return phoneNumbers;
        } else {
          throw new Exception("Could not save phone numbers for netid '" + netId + "'; "
              + "The phone numbers were " + phoneNumbers + "'");
        }
        
      }
}
