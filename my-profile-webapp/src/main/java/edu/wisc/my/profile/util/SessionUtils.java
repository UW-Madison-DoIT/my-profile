package edu.wisc.my.profile.util;

import javax.servlet.http.HttpServletRequest;

public class SessionUtils {
  public static String getAttribute(HttpServletRequest request, String ... attributes) {
    Object valObject = null;
    
    for (String attribute : attributes) {
      valObject = request.getAttribute(attribute);
      if(valObject != null)
        break;
    }
    if(valObject instanceof String)
      return (String) valObject;
    else 
      return null;
  }
}
