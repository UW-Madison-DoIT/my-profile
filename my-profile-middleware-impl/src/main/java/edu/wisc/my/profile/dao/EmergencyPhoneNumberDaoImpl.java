package edu.wisc.my.profile.dao;

import java.util.Arrays;

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
            //Fetch results from Middleware
            TypeValue[] savedPhoneNumbers = this.getPhoneNumbers(netId);
            //Check to see if MW results are the same as were attempted to saved
            if(!Arrays.equals(savedPhoneNumbers, phoneNumbers)){
                //Build user inputed phone number logging string
                StringBuilder expectedBuilder = new StringBuilder()
                .append("[");
                int phoneNumberCounter = 0;
                for(TypeValue phoneNumber: phoneNumbers){
                    if(phoneNumberCounter>0){
                        expectedBuilder.append(", ");
                    }
                    expectedBuilder.append(phoneNumber.toStringForLogging());
                    phoneNumberCounter++;
                }
                expectedBuilder.append("]");
                //Build actually saved phone numbers logging string
                StringBuilder actualSavedBuilder = new StringBuilder()
                .append("[");
                phoneNumberCounter = 0;
                for(TypeValue phoneNumber: savedPhoneNumbers){
                    if(phoneNumberCounter>0){
                        actualSavedBuilder.append(", ");
                    }
                    actualSavedBuilder.append(phoneNumber.toStringForLogging());
                    phoneNumberCounter++;
                }
                actualSavedBuilder.append("]");
                throw new Exception("Phone number information was not "
                        + "successfully saved.  Expected to save: "
                        +expectedBuilder+" but actually saved: "
                        +actualSavedBuilder.toString());
            }
          //return saved content
          return phoneNumbers;
        } else {
          throw new Exception("There was an issue saving the emergency contacts");
        }
        
      }
}
