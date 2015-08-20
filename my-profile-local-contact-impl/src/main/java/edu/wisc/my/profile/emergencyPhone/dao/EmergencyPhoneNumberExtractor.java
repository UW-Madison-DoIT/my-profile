package edu.wisc.my.profile.emergencyPhone.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.model.TypeValue;

public class EmergencyPhoneNumberExtractor implements ResultSetExtractor<TypeValue[]> {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  private ObjectMapper om;
  
  public EmergencyPhoneNumberExtractor() {
    om = new ObjectMapper();
    om.registerModule(new JodaModule());
  }

  @Override
  public TypeValue[] extractData(ResultSet rs) throws SQLException, DataAccessException {
    if(rs.next()) {
      String val = rs.getString(2);
      TypeValue[] value = null;
      try {
        value = om.readValue(val, TypeValue[].class);
      } catch (Exception e) {
        logger.error("Issue fetching user info",e);
      }
      return value;
    } else {
      return new TypeValue[0];
    }
  }
}
