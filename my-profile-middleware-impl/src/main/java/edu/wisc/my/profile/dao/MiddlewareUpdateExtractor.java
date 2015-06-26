package edu.wisc.my.profile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


public class MiddlewareUpdateExtractor implements ResultSetExtractor<String> {
  
  public static final String SUCCESS="SUCCESS";
  public static final String FAILURE="FAILURE";
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public String extractData(ResultSet rs) throws SQLException, DataAccessException {
    if(rs.next()) {
      String val = rs.getString(1);
      return val;
    }
    //error or empty
    return FAILURE;
  }

}
