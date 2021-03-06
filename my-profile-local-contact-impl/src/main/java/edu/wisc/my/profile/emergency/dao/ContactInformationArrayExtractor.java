package edu.wisc.my.profile.emergency.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import edu.wisc.my.profile.model.ContactInformation;

public class ContactInformationArrayExtractor implements ResultSetExtractor<ContactInformation[]> {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  private ObjectMapper om;
  
  public ContactInformationArrayExtractor() {
    om = new ObjectMapper();
    om.registerModule(new JodaModule());
  }

  @Override
  public ContactInformation[] extractData(ResultSet rs) throws SQLException, DataAccessException {
    if(rs.next()) {
      String val = rs.getString(2);
      ContactInformation[] value = null;
      try {
        value = om.readValue(val, ContactInformation[].class);
      } catch (Exception e) {
        logger.error("Issue fetching user info",e);
      }
      return value;
    } else {
      return new ContactInformation[0];
    }
  }
}
