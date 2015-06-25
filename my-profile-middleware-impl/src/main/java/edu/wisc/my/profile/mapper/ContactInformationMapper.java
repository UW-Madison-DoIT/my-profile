package edu.wisc.my.profile.mapper;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wisc.my.profile.model.ContactAddress;
import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.model.TypeValue;

public final class ContactInformationMapper {
  
  protected static final Logger logger = LoggerFactory.getLogger(ContactInformationMapper.class);
  
  public static ContactInformation[] convertToEmergencyContactInformation(JSONObject json) {
    List <ContactInformation> eci = new ArrayList<ContactInformation>();
    
    try {
      for(int i = 1; i <=3; i++) {
        JSONObject jcontact = json.getJSONObject("EMERGENCY COUNT " + i);
        if(jcontact != null) {
          ContactInformation ci = new ContactInformation();
          ci.setPreferredName(jcontact.getString("EMERGENCY NAME"));
          ci.setRelationship(jcontact.getString("RELATION"));
          ci.setComments(jcontact.getString("RELATION COMMENT"));
          //le emailz
          try {
            for(int j = 1; j <=3; j++) {
              JSONObject jemail = jcontact.getJSONObject("EMERGENCY EMAIL COUNT "+ j);
              String email = jemail.getString("EMERGENCY EMAIL ADDRESS");
              String value = jemail.getString("EMERGENCY EMAIL COMMENT");
              ci.getEmails().add(new TypeValue(value, email));
            }
          } catch (JSONException ex) {
            logger.trace("Error parsing, probably just someone who doesn't have the max",ex);
            //nothing to see here, move along
          }
          
          //le phonz
          try {
            for(int j = 1; j <=3; j++) {
              JSONObject jphone = jcontact.getJSONObject("EMERGENCY PHONE COUNT "+ j);
              String phone = jphone.getString("EMERGENCY PHONE NUMBER");
              String comment = jphone.getString("EMERGENCY PHONE COMMENT");
              ci.getPhoneNumbers().add(new TypeValue(comment, phone));
            }
          } catch (JSONException ex) {
            logger.trace("Error parsing, probably just someone who doesn't have the max",ex);
          }
          
          //add to parent
          eci.add(ci);
        }
        
      }
    } catch (JSONException ex) {
      logger.trace("Error parsing, probably just someone who doesn't have the max",ex);
    }
    
    return eci.toArray(new ContactInformation[3]);
  }

  public static ContactInformation convertToLocalContactInformation(JSONObject json, ContactInformation ci) {
    if(json != null) {
      ci.setId(json.get("NETID").toString());
      
      //addresses
      for(int i = 1 ; i <= 3; i++) {
        try {
          JSONObject address = json.getJSONObject("ADDRESS COUNT " + i);
          if (address != null) {
            ContactAddress ca = new ContactAddress () ;
            for (int j = 1; j <= 4; j++) {
              try {
                String addressLine = address.getString("ADDRESS LINE " + j);
                if(StringUtils.isNotBlank(addressLine)) {
                  ca.getAddressLines().add(addressLine);
                }
              } catch(JSONException ex) {
                logger.trace(ex.getMessage());
                //eat exception as its probably that they just didn't have 4 address lines
              }
            }
            ca.setCity(address.getString("CITY"));
            ca.setState(address.getString("STATE"));
            ca.setPostalCode(address.getString("ZIP"));
            ca.setCountry(address.getString("COUNTRY"));
            ca.setComment(address.getString("ADDRESS COMMENT"));
            ca.setType(address.getString("ADDRESS TYPE"));
            ci.getAddresses().add(ca);
          }
        } catch (JSONException ex) {
          logger.trace(ex.getMessage());
          //eat exception as its probably that they just didn't have 3 addresses
        }
      }
    }
    return ci;
  }
  
  public static JSONObject convertToJSONObject(ContactInformation[] emergencyContacts, ContactInformation ci) {
    JSONObject json = new JSONObject();
    //populate local contact info
    int count=1;
    for(ContactAddress ca : ci.getAddresses()) {
      JSONObject address = new JSONObject();
      int alcount = 1;
      for(String al : ca.getAddressLines()) {
        address.put("ADDRESS LINE " + alcount++, al);
      }
      address.put("CITY", ca.getCity());
      address.put("STATE", ca.getState());
      address.put("ZIP", ca.getPostalCode());
      address.put("COUNTRY", ca.getCountry());
      address.put("ADDRESS COMMENT", ca.getComment());
      address.put("ADDRESS PRIORITY", count);
      DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MMM-YY");
      address.put("ADDRESS DTTM", formatter.print(ci.getLastModified()));
      json.put("ADDRESS COUNT " + count++, address);
    }
    
    //TODO : populate local phone 
      
    //populate emergency contact info
    count = 1;
    for(ContactInformation eci : emergencyContacts) {
      JSONObject emergencyContact = jsonifyEmergencyContact(eci);
      emergencyContact.put("EMERGENCY CONTACT PRIORITY", count);
      json.put("EMERGENCY COUNT " + count++, emergencyContact);
    }
    
    return json;
  }
  
  private static JSONObject jsonifyEmergencyContact(ContactInformation eci) {
    JSONObject emergencyContact = new JSONObject();
    emergencyContact.put("NAME", eci.getPreferredName());
    emergencyContact.put("RELATION", eci.getRelationship());
    emergencyContact.put("RELATION COMMENT", eci.getComments());
    //TODO: Get language from eci 
    //emergencyContact.put("LANGUAGE SPOKEN 1", eci.get);
    //emergencyContact.put("LANGUAGE SPOKEN 2", eci.get);
    emergencyContact.put("LANGUAGE SPOKEN 1", "ENG");//todo : bad, no ugh
    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MMM-YY");
    emergencyContact.put("EMERGENCY CONTACT DTTM", formatter.print(DateTime.now()));
    
    int pcount = 1;
    for(TypeValue phone : eci.getPhoneNumbers()) {
      JSONObject jphone = new JSONObject();
      jphone.put("EMERGENCY PHONE PRIORITY", pcount);
      jphone.put("EMERGENCY PHONE NUMBER", phone.getValue());
      jphone.put("EMERGENCY PHONE COMMENT", phone.getType());
      jphone.put("EMERGENCY PHONE DTTM", formatter.print(DateTime.now()));
      emergencyContact.put("EMERGENCY PHONE COUNT " + pcount++, jphone);
    }
    
    int ecount = 1;
    for(TypeValue email : eci.getEmails()) {
      JSONObject jemail = new JSONObject();
      jemail.put("EMERGENCY EMAIL PRIORITY", 1);
      jemail.put("EMERGENCY EMAIL ADDRESS", email.getValue());
      jemail.put("EMERGENCY EMAIL COMMENT", email.getType());
      jemail.put("EMERGENCY EMAIL DTTM", formatter.print(DateTime.now()));
      emergencyContact.put("EMERGENCY EMAIL COUNT " + ecount++, jemail);
    }
      
    
    
    return emergencyContact;
  }
  
}
