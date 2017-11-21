package edu.wisc.my.profile.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import edu.wisc.my.profile.log.MaskedLoggables;

import edu.wisc.my.profile.model.TypeValue;
import edu.wisc.my.profile.service.EmergencyPhoneNumberService;

@Controller
@RequestMapping("/emergencyPhoneNumber")
public class EmergencyPhoneNumberController {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private EmergencyPhoneNumberService emPhoneNumberService;
    
    @RequestMapping(method = RequestMethod.GET, value="/get.json")
    public @ResponseBody TypeValue[] getPhoneNumber(HttpServletRequest request, HttpServletResponse response){
      TypeValue[] phoneNumbers = null;
      String username = request.getHeader("uid");
      
      if(StringUtils.isBlank(username)){
          logger.warn("User hit get emergency phone number service w/o username set");
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          return null;
      }
      
      try {
          phoneNumbers = emPhoneNumberService.getEmergencyPhoneNumbers(username);
      } catch (Exception e) {
          logger.error("Issue happened during lookup", e);
          response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
      }
      return phoneNumbers;
    }
    
    @RequestMapping(method = RequestMethod.POST, value="/set")
    public @ResponseBody TypeValue[] setPhoneNumber(HttpServletRequest request, @RequestBody TypeValue[] phoneNumbers, HttpServletResponse response){
        final String uid = request.getHeader("uid");
        
        if(StringUtils.isBlank(uid)){
            logger.warn("User hit get emergency phone number service w/o username set");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        
        try {
            emPhoneNumberService.setEmergencyPhoneNumbers(uid, (TypeValue[]) phoneNumbers);
            logger.info("User {} saved phone numbers successfully", uid);
        } catch (Exception e) {
            String maskedData = MaskedLoggables.toStringForLogging(phoneNumbers);
            logger.error("Issue while user {} attempted to save {}", uid, maskedData, e);
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
        
        return emPhoneNumberService.getEmergencyPhoneNumbers(uid);
    }
    
}
