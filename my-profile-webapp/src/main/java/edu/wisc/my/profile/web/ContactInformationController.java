package edu.wisc.my.profile.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.service.ContactInformationService;
import edu.wisc.my.profile.util.SessionUtils;

@Controller
@RequestMapping("/contactInfo.json")
public class ContactInformationController {
  
  protected static final Logger logger = LoggerFactory.getLogger(ContactInformationController.class);
  
  @Autowired
  private ContactInformationService ciService;
  
  private String[] emplIdAttributes;
  
  @Value("${emplIdAttributes:wisceduhrpersonid}")
  public void setEmplIdAttributeCSV(String emplIdCSV) {
    emplIdAttributes = emplIdCSV.split(",");
  }
  
  @RequestMapping(method = RequestMethod.GET) 
  public @ResponseBody ContactInformation getContactInfo(HttpServletRequest request) {
    
    String username = request.getRemoteUser();
    logger.debug("Received username " + username);
    
    if(StringUtils.isBlank(username)) {
      return null;
    } else {
      String emplId = SessionUtils.getAttribute(request, emplIdAttributes);
      logger.debug("Received emplId : " + emplId);
      if(!StringUtils.isBlank(emplId))
        return ciService.getContactInfo(emplId);
      else 
        return null;
    }
  }
}
