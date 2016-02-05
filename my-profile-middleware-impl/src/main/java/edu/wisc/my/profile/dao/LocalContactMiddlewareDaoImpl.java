package edu.wisc.my.profile.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.wisc.my.profile.mapper.ContactInformationMapper;
import edu.wisc.my.profile.model.ContactAddress;
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
        //Fetch results from Middleware
        ContactInformation savedContactInformation = this.getContactInfo(netId);
        //Check to see if MW results are the same as were attempted to save
        
        //If we send a null type or comment, MW will add a empty string for us!
        List<ContactAddress> updatedTypedAddressList = new ArrayList<ContactAddress>();
        for(ContactAddress address : updatedContactInformation.getAddresses()){
            if(address.getType()==null){
                address.setType(StringUtils.EMPTY);
            }
            if(address.getComment()==null){
                address.setComment(StringUtils.EMPTY);
            }
            updatedTypedAddressList.add(address);
        }
        updatedContactInformation.setAddresses(updatedTypedAddressList);
        
        //If we send a empty comment field, MW will set a null for us!
        List<ContactAddress> updatedCommentAddressList = new ArrayList<ContactAddress>();
        for(ContactAddress address: savedContactInformation.getAddresses()){
            if(address.getComment()==null){
                address.setComment(StringUtils.EMPTY);
            }
            updatedCommentAddressList.add(address);
        }
        savedContactInformation.setAddresses(updatedCommentAddressList);
        
        if(!updatedContactInformation.equals(savedContactInformation)){
            throw new Exception("Local contact information was not successfully "
                    + "saved.  Expected to save: "
                    +updatedContactInformation.toStringForLogging()
                    +" but actually saved: "
                    +savedContactInformation.toStringForLogging());
        }
      //  return saved content
      return updatedContactInformation;
    } else {
      throw new Exception("There was an issue saving the local contact information");
    }
    
    
  }
}
