package edu.wisc.my.profile.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionUtils {
  
  protected static final Logger logger = LoggerFactory.getLogger(SessionUtils.class);
  
  public static String getHeader(HttpServletRequest request, String ... attributes) {
    Object valObject = null;
    
    for (String attribute : attributes) {
      valObject = request.getHeader(attribute);
      if(valObject != null) {
        logger.debug("Found valid value using attribute : " + attribute);
        break;
      }
    }
    if(valObject instanceof String)
      return (String) valObject;
    else 
      return null;
  }
}
