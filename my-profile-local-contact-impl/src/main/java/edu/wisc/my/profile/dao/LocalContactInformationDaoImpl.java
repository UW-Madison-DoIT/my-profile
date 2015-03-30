package edu.wisc.my.profile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import edu.wisc.my.profile.model.ContactInformation;

public class LocalContactInformationDaoImpl implements LocalContactInformationDao {
  
  private JdbcTemplate jdbcTemplate;
  private ObjectMapper om;
  private final static String INSERT_SQL = "INSERT INTO key_val (key, value) values (?,?)";
  private final static String SELECT_SQL = "SELECT * FROM key_val WHERE key = ?";
  private final static String DELETE_SQL = "DELETE FROM key_val WHERE key = ?";
  
  public LocalContactInformationDaoImpl(DataSource ds) {
    jdbcTemplate = new JdbcTemplate(ds);
    om = new ObjectMapper();
    om.registerModule(new JodaModule());
  }

  @Override
  public ContactInformation getContactInfo(String netId) {
    
    ContactInformation result = jdbcTemplate.query(SELECT_SQL, new ContactInformationExtractor(), netId);
    return result;
  }

  @Override
  public ContactInformation setContactInfo(String netId, ContactInformation contactInformation) {
    
    try {
      final String json = om.writeValueAsString(contactInformation);
      jdbcTemplate.update(DELETE_SQL, netId);
      jdbcTemplate.update(INSERT_SQL, netId, json);
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return contactInformation;
  }
  
  private class ContactInformationExtractor implements ResultSetExtractor<ContactInformation> {

    @Override
    public ContactInformation extractData(ResultSet rs) throws SQLException, DataAccessException {
      if(rs.next()) {
        String val = rs.getString(2);
        ContactInformation value = null;
        try {
          value = om.readValue(val, ContactInformation.class);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        return value;
      } else {
        return new ContactInformation();
      }
    }
    
  }

}
