package edu.wisc.my.profile.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/contactInfo")
public class ContactInformationController {
  
  @Autowired
  private ContactInformationService ciService;
  
  private String[] emplIdAttributes;
  
  @Value("${emplIdAttributes:wisceduhrpersonid}")
  public void setEmplIdAttributeCSV(String emplIdCSV) {
    emplIdAttributes = emplIdCSV.split(",");
  }
  
  @RequestMapping(value = "/get", method = RequestMethod.GET) 
  public @ResponseBody ContactInformation getContactInfo(HttpServletRequest request) {
    
    String username = request.getHeader("remote_user");
    
    if(StringUtils.isBlank(username)) 
      return null;
    else {
      String emplId = SessionUtils.getAttribute(request, emplIdAttributes);
      if(!StringUtils.isBlank(emplId))
        return ciService.getContactInfo(emplId);
      else 
        return null;
    }
  }
}
