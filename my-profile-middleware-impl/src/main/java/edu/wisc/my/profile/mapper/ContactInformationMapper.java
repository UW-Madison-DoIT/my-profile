package edu.wisc.my.profile.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
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
  
  public static String getCleanString(String dirty) {
    return StringEscapeUtils.escapeJson(dirty);
  }
  
  public static String getDirtyString(JSONObject obj, String key) {
    String result = null;
    if (null != obj) {
//      try {
        result = obj.getString(key);
//      } catch (JSONException e) {
//        // We should really be catching these here.
//      }
    }
    
    if (null != result) {
      result = StringEscapeUtils.unescapeJson(StringEscapeUtils.unescapeJson(result));
    }
    return result;
  }
  
  public static ContactInformation[] convertToEmergencyContactInformation(JSONObject json) {
    List <ContactInformation> eci = new ArrayList<ContactInformation>();
    
    try {
      for(int i = 1; i <=3; i++) {
        JSONObject jcontact = json.getJSONObject("EMERGENCY COUNT " + i);
        if(jcontact != null) {
          ContactInformation ci = new ContactInformation();
          ci.setPreferredName(getDirtyString(jcontact, "EMERGENCY NAME"));
          ci.setRelationship(getDirtyString(jcontact, "RELATION"));
          ci.setComments(getDirtyString(jcontact, "RELATION COMMENT"));
          //le emailz
          try {
            for(int j = 1; j <=3; j++) {
              JSONObject jemail = jcontact.getJSONObject("EMERGENCY EMAIL COUNT "+ j);
              String email = getDirtyString(jemail, "EMERGENCY EMAIL ADDRESS");
              String value = getDirtyString(jemail, "EMERGENCY EMAIL COMMENT");
              ci.getEmails().add(new TypeValue(value, email));
            }
          } catch (JSONException ex) {
//            logger.trace("Error parsing, probably just someone who doesn't have the max",ex);
            //nothing to see here, move along
          }
          
          //le phonz
          try {
            for(int j = 1; j <=3; j++) {
              JSONObject jphone = jcontact.getJSONObject("EMERGENCY PHONE COUNT "+ j);
              String phone = getDirtyString(jphone, "EMERGENCY PHONE NUMBER");
              String comment = getDirtyString(jphone, "EMERGENCY PHONE COMMENT");
              ci.getPhoneNumbers().add(new TypeValue(comment, phone));
            }
          } catch (JSONException ex) {
//            logger.trace("Error parsing, probably just someone who doesn't have the max",ex);
          }
          
          //add to parent
          eci.add(ci);
        }
        
      }
    } catch (JSONException ex) {
//      logger.trace("Error parsing, probably just someone who doesn't have the max",ex);
    }
    
    return eci.toArray(new ContactInformation[0]);
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
                String addressLine = getDirtyString(address, "ADDRESS LINE " + j);
                if(StringUtils.isNotBlank(addressLine)) {
                  ca.getAddressLines().add(addressLine);
                }
              } catch(JSONException ex) {
//                logger.trace(ex.getMessage());
                //eat exception as its probably that they just didn't have 4 address lines
              }
            }
            ca.setCity(getDirtyString(address, "CITY"));
            ca.setState(getDirtyString(address, "STATE"));
            ca.setPostalCode(getDirtyString(address, "ZIP"));
            ca.setCountry(getDirtyString(address, "COUNTRY"));
            ca.setComment(getDirtyString(address, "ADDRESS COMMENT"));
            ca.setType(getDirtyString(address, "ADDRESS TYPE"));
            ci.getAddresses().add(ca);
          }
        } catch (JSONException ex) {
//          logger.trace(ex.getMessage());
          //eat exception as its probably that they just didn't have 3 addresses
        }
      }
    }
    return ci;
  }
  
  /**
   * Extracts Phone Number from JSONObject
   * @param json
   * @return A TypeValue representing phone numbers, TypeValue may be empty if
   * no phone number exists, may return null if parameter is null
   */
  public static TypeValue[] convertToEmergencyPhoneNumber(JSONObject json){
      if(json == null){
          return null;
      }
      List<TypeValue> phoneNumbers = new ArrayList<TypeValue>();
      //phoneNumbers
      for(int i=1; i<=3; i++){
          try {
              JSONObject phoneNumberLine = json.getJSONObject("PHONE COUNT " + i);
              if(phoneNumberLine != null){
                  TypeValue phoneNumber = new TypeValue();
                  phoneNumber.setValue(getDirtyString(phoneNumberLine, "PHONE NUMBER"));
                  phoneNumber.setType(getDirtyString(phoneNumberLine, "PHONE COMMENT"));
                  phoneNumbers.add(phoneNumber);
              }
          }catch(JSONException ex){
              //Probably safe.  Will throw exception if PHONE COUNT +i does not return object
//              logger.trace(ex.getMessage());
          }
      }
      return phoneNumbers.toArray(new TypeValue[phoneNumbers.size()]);
  }
  
  public static JSONObject convertToJSONObject(ContactInformation[] emergencyContacts, ContactInformation ci, TypeValue[] phoneNumbers) {
    JSONObject json = new JSONObject();
    //populate local contact info
    int count=1;
    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MMM-YY");
    for(ContactAddress ca : ci.getAddresses()) {
      JSONObject address = new JSONObject();
      int alcount = 1;
      for(String al : ca.getAddressLines()) {
        address.put("ADDRESS LINE " + alcount++, getCleanString(al));
      }
      address.put("CITY", getCleanString(ca.getCity()));
      address.put("STATE", getCleanString(ca.getState()));
      address.put("ZIP", getCleanString(ca.getPostalCode()));
      address.put("COUNTRY", getCleanString(ca.getCountry()));
      
      if(ca.getComment()!=null && !ca.getComment().isEmpty()){
          StringBuilder addressComment = new StringBuilder(ca.getComment());
          if(addressComment.toString().endsWith("\"")){
              addressComment.append(" ");
          }
          address.put("ADDRESS COMMENT", getCleanString(addressComment.toString()));
      }else{
          address.put("ADDRESS COMMENT", getCleanString(ca.getComment()));
      }
      
      address.put("ADDRESS PRIORITY", count);
      address.put("ADDRESS DTTM", formatter.print(ci.getLastModified()));
      json.put("ADDRESS COUNT " + count++, address);
    }

    //Add Emergency Phone Number
    count = 1;
    for(TypeValue phoneNumber: phoneNumbers){
        if(phoneNumber.getType()!=null && phoneNumber.getValue()!=null){
            JSONObject emergencyPhoneNumber = new JSONObject();
            emergencyPhoneNumber.put("PHONE PRIORITY", count);
            emergencyPhoneNumber.put("PHONE NUMBER", getCleanString(phoneNumber.getValue()));
            emergencyPhoneNumber.put("PHONE COMMENT", getCleanString(phoneNumber.getType()));
            emergencyPhoneNumber.put("PHONE DTTM", formatter.print(phoneNumber.getLastModified()));
            json.put("PHONE COUNT "+count, emergencyPhoneNumber);
        }
    }

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
    emergencyContact.put("EMERGENCY NAME", getCleanString(eci.getPreferredName()));
    emergencyContact.put("RELATION", getCleanString(eci.getRelationship()));
    
    if(eci.getComments()!=null && !eci.getComments().isEmpty()){
        StringBuilder relationComment = new StringBuilder(eci.getComments());
        if(relationComment.toString().endsWith("\"")){
            relationComment.append(" ");
        }
        emergencyContact.put("RELATION COMMENT", getCleanString(relationComment.toString()));
    }else{
        emergencyContact.put("RELATION COMMENT", getCleanString(eci.getComments()));
    }
    
    //TODO: Get language from eci 
    //emergencyContact.put("LANGUAGE SPOKEN 1", eci.get);
    //emergencyContact.put("LANGUAGE SPOKEN 2", eci.get);
    emergencyContact.put("LANGUAGE SPOKEN 1", "ENG");//todo : bad, no ugh
    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MMM-YY");
    emergencyContact.put("EMERGENCY CONTACT DTTM", formatter.print(DateTime.now()));
    
    int pcount = 1;
    for(TypeValue phone : eci.getPhoneNumbers()) {
      if(!phone.isEmpty()) {
        JSONObject jphone = new JSONObject();
        jphone.put("EMERGENCY PHONE PRIORITY", pcount);
        jphone.put("EMERGENCY PHONE NUMBER", getCleanString(phone.getValue()));
        jphone.put("EMERGENCY PHONE COMMENT", getCleanString(phone.getType()));
        jphone.put("EMERGENCY PHONE DTTM", formatter.print(DateTime.now()));
        emergencyContact.put("EMERGENCY PHONE COUNT " + pcount++, jphone);
      }
    }
    
    int ecount = 1;
    for(TypeValue email : eci.getEmails()) {
      JSONObject jemail = new JSONObject();
      jemail.put("EMERGENCY EMAIL PRIORITY", 1);
      jemail.put("EMERGENCY EMAIL ADDRESS", getCleanString(email.getValue()));
      jemail.put("EMERGENCY EMAIL COMMENT", getCleanString(email.getType()));
      jemail.put("EMERGENCY EMAIL DTTM", formatter.print(DateTime.now()));
      emergencyContact.put("EMERGENCY EMAIL COUNT " + ecount++, jemail);
    }

    return emergencyContact;
  }
  
}
