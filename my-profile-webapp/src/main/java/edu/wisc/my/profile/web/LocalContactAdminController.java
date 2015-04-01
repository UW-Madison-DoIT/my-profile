package edu.wisc.my.profile.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.service.LocalContactInformationService;

@Controller
@RequestMapping("/localContactInfo")
public class LocalContactAdminController {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  private String manifestAttribute;
  private String usernameAttribute;
  
  @Autowired
  private LocalContactInformationService service;
  
  @Value("${manifestAttribute}")
  public void setManifestGroupAttribute(String attr) {
    manifestAttribute = attr;
  }
  
  @Value("${usernameAttribute}")
  public void setUsernameAttr(String attr) {
    usernameAttribute = attr;
  }
  
  @RequestMapping(method = RequestMethod.GET, value="/adminLookup")
  public @ResponseBody ContactInformation getContactInfo(HttpServletRequest request, @RequestParam("netId") String netId, HttpServletResponse response) {
    try {
      String username = request.getHeader(usernameAttribute);
      String manifestGroups = request.getHeader(manifestAttribute);
      if(StringUtils.isBlank(username)) {
        logger.warn("User hit admin service w/o username set");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return null;
      }
      return service.getContactInfo(username,manifestGroups, netId);
    } catch (Exception e) {
      logger.error("Issue happened during lookup", e);
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      return null;
    }
  }

}
