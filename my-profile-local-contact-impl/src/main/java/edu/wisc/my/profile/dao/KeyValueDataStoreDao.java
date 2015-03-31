package edu.wisc.my.profile.dao;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * A class that fetches and places a Java object into a key value store as a json object
 * The table name is key_val with a key and a value column
 * @author levett
 *
 * @param <T> This is the object type to be stored
 */
public abstract class KeyValueDataStoreDao<T> {
  
  private String postFix = null;
  
  private JdbcTemplate jdbcTemplate;
  private ObjectMapper om;
  private final static String INSERT_SQL = "INSERT INTO key_val (key, value) values (?,?)";
  private final static String SELECT_SQL = "SELECT * FROM key_val WHERE key = ?";
  private final static String DELETE_SQL = "DELETE FROM key_val WHERE key = ?";
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  public void setPostFix(String postFix) {
    this.postFix = postFix;
  }
  
  public String getPostFix() {
    return StringUtils.isNotBlank(postFix) ? ":" + postFix : "";
  }
  
  public KeyValueDataStoreDao(DataSource ds) {
    jdbcTemplate = new JdbcTemplate(ds);
    om = new ObjectMapper();
    om.registerModule(new JodaModule());
  }

  public T getData(String key, ResultSetExtractor<T> rse) {
    
    T result = jdbcTemplate.query(SELECT_SQL, rse, key + getPostFix());
    return result;
  }

  public T setData(String key, T t) throws Exception {
    
    try {
      final String json = om.writeValueAsString(t);
      jdbcTemplate.update(DELETE_SQL, key + getPostFix());
      jdbcTemplate.update(INSERT_SQL, key + getPostFix(), json);
    } catch (JsonProcessingException e) {
      logger.error("Issue updating user info",e);
      throw new Exception(e);
    }
    return t;
  }
}
