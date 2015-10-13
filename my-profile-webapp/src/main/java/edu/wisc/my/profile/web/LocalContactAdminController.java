package edu.wisc.my.profile.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.model.SearchTerm;
import edu.wisc.my.profile.model.TypeValue;
import edu.wisc.my.profile.model.User;
import edu.wisc.my.profile.service.EmergencyContactInformationService;
import edu.wisc.my.profile.service.EmergencyPhoneNumberService;
import edu.wisc.my.profile.service.LocalContactInformationService;
import edu.wisc.my.profile.service.SearchUsersService;

@Controller
@RequestMapping("/localContactInfo")
public class LocalContactAdminController {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  private String manifestAttribute;
  private String usernameAttribute;
  
  @Autowired
  private EmergencyContactInformationService emergencyService;
  
  @Autowired
  private LocalContactInformationService localService;
  
  @Autowired
  private SearchUsersService searchUsersService;
  
  @Autowired
  private EmergencyPhoneNumberService emPhoneNumberService;
  
  @Value("${manifestAttribute}")
  public void setManifestGroupAttribute(String attr) {
    manifestAttribute = attr;
  }
  
  @Value("${usernameAttribute}")
  public void setUsernameAttr(String attr) {
    usernameAttribute = attr;
  }
  
  @RequestMapping(method = RequestMethod.GET, value="/searchUsers")
  public @ResponseBody void getUsers(HttpServletRequest request, @RequestParam("searchTerms") JSONObject searchTerms, HttpServletResponse response) {
    try {
      String username = request.getHeader(usernameAttribute);
      String manifestGroups = request.getHeader(manifestAttribute);
      if(StringUtils.isBlank(username)) {
        logger.warn("User hit admin service w/o username set");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
      SearchTerm searchTerm = new SearchTerm();
      searchTerm.setFirstName(searchTerms.optString("firstName"));
      searchTerm.setLastName(searchTerms.optString("lastName"));
      List<User> users = searchUsersService.getUsers(username, manifestGroups, searchTerm);
      JSONObject jsonToReturn = new JSONObject();
      JSONArray userList = new JSONArray();
      for(User u:users){
          JSONObject user = new JSONObject();
          user.put("lastName", u.getLastName());
          user.put("firstName", u.getFirstName());
          user.put("pvi", u.getPvi());
          user.put("middleName", u.getMiddleName());
          user.put("birthDate", u.getBirthDate());
          userList.put(user);
      }
      jsonToReturn.put("people", userList);
      response.setContentType("application/json");
      response.getWriter().write(jsonToReturn.toString());
      } catch (Exception e) {
      logger.error("Issue happened during lookup", e);
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
  }
  
  @RequestMapping(method = RequestMethod.GET, value="/adminLookup")
  public @ResponseBody void getContactInfo(HttpServletRequest request, @RequestParam("netId") String netId, HttpServletResponse response) {
    try {
      String username = request.getHeader(usernameAttribute);
      String manifestGroups = request.getHeader(manifestAttribute);
      Gson gson = new Gson();
      
      if(StringUtils.isBlank(username)) {
        logger.warn("User hit admin service w/o username set");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
      ContactInformation[] emergencyInfo = emergencyService.getContactInfo(username, manifestGroups, netId);
      ContactInformation localInfo = localService.getContactInfo(username,manifestGroups, netId);
      TypeValue[] emergencyPhoneNumbers = emPhoneNumberService.getEmergencyPhoneNumbers(username, manifestGroups, netId);
      String localContactInfoString = gson.toJson(localInfo);
      String emergencyContactInfoString = gson.toJson(emergencyInfo);
      String emergencyPhoneNumbersString = gson.toJson(emergencyPhoneNumbers);
      response.setContentType("application/json");
      response.getWriter().write("{\"emergency\":"+ emergencyContactInfoString +
              " , \"local\":"+localContactInfoString+
              " , \"emergencyPhoneNumbers\":"+emergencyPhoneNumbersString+"}");
    } catch (Exception e) {
      logger.error("Issue happened during lookup", e);
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
  }

}
