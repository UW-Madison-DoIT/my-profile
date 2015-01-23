package edu.wisc.my.profile.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.service.ContactInformationService;

@Controller
@RequestMapping("/contactInfo")
public class ContactInformationController {
  
  @Autowired
  private ContactInformationService ciService;
  
  @RequestMapping(value = "/get", method = RequestMethod.GET) 
  public @ResponseBody ContactInformation getContactInfo(HttpServletRequest request) {
    String emplId = request.getHeader("remote_user");
    if(StringUtils.isBlank(emplId)) 
      return null;
    else
      return ciService.getContactInfo(emplId);
  }
}
