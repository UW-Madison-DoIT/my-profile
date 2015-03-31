package edu.wisc.my.profile.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.service.LocalContactInformationService;

@Controller
@RequestMapping("/localContactInfo")
public class LocalContactInformationController {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private LocalContactInformationService service;
  
  @RequestMapping(method = RequestMethod.GET, value="/get.json")
  public @ResponseBody ContactInformation getContactInfo(HttpServletRequest request) {
    String username = request.getHeader("uid");
    if(StringUtils.isBlank(username)) {
      logger.warn("User hit service w/o username set");
      return null;
    }
    return service.getContactInfo(username);
  }
  
  @RequestMapping(method = RequestMethod.POST, value="/set")
  public @ResponseBody ContactInformation setContactInformation(HttpServletRequest request, 
                                                                  @RequestBody ContactInformation ci,
                                                                  HttpServletResponse response) {
    final String uid = request.getHeader("uid");
    //TODO validate
    if(StringUtils.isNotBlank(uid)) {
      try {
        ci = service.setContactInfo(uid, ci);
      } catch (Exception e) {
        logger.error("Issue setting data", e);
        response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        return ci;
      }
      return ci;
    } else {
      return ci;
    }
  }

}
