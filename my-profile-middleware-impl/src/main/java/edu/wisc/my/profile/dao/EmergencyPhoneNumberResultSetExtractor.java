package edu.wisc.my.profile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import edu.wisc.my.profile.mapper.ContactInformationMapper;
import edu.wisc.my.profile.model.TypeValue;

public class EmergencyPhoneNumberResultSetExtractor implements ResultSetExtractor<TypeValue[]> {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Override
  public TypeValue[] extractData(ResultSet rs) throws SQLException, DataAccessException {
    if(rs.next()) {
      String val = rs.getString(1);
      TypeValue[] value = null;
      try {
        JSONObject json = new JSONObject(val);
        value = ContactInformationMapper.convertToEmergencyPhoneNumber(json);
        return value;
      } catch (Exception e) {
        logger.error("Issue fetching user info",e);
      }
    }
    //error or empty
    return null;
  }

}
