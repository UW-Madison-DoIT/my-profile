package edu.wisc.my.profile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import edu.wisc.my.profile.model.ContactAddress;
import edu.wisc.my.profile.model.ContactInformation;

public class ContactInfoResultSetExtractor implements ResultSetExtractor<ContactInformation> {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Override
  public ContactInformation extractData(ResultSet rs) throws SQLException, DataAccessException {
    if(rs.next()) {
      String val = rs.getString(1);
      ContactInformation value = null;
      try {
        JSONObject json = new JSONObject(val);
        
        value = convertToObject(json, new ContactInformation());
      } catch (Exception e) {
        logger.error("Issue fetching user info",e);
      }
      return value;
    } else {
      return new ContactInformation();
    }
  }
  
  private ContactInformation convertToObject(JSONObject json, ContactInformation ci) {
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

}
