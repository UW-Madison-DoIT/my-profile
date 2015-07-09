package edu.wisc.my.profile.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONArray;
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
import edu.wisc.my.profile.model.User;
import edu.wisc.my.profile.service.LocalContactInformationService;
import edu.wisc.my.profile.service.SearchUsersService;

@Controller
@RequestMapping("/localContactInfo")
public class LocalContactAdminController {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  private String manifestAttribute;
  private String usernameAttribute;
  
  @Autowired
  private LocalContactInformationService service;
  
  @Autowired
  private SearchUsersService searchUsersService;
  
  @Value("${manifestAttribute}")
  public void setManifestGroupAttribute(String attr) {
    manifestAttribute = attr;
  }
  
  @Value("${usernameAttribute}")
  public void setUsernameAttr(String attr) {
    usernameAttribute = attr;
  }
  
  @RequestMapping(method = RequestMethod.GET, value="/searchUsers")
  public @ResponseBody void getUsers(HttpServletRequest request, @RequestParam("searchTerm") String searchTerm, HttpServletResponse response) {
    try {
      String username = request.getHeader(usernameAttribute);
      String manifestGroups = request.getHeader(manifestAttribute);
      if(StringUtils.isBlank(username)) {
        logger.warn("User hit admin service w/o username set");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
      List<User> users = searchUsersService.getUsers(username, manifestGroups, searchTerm);
      JSONObject jsonToReturn = new JSONObject();
      JSONArray userList = new JSONArray();
      for(User u:users){
          JSONObject user = new JSONObject();
          JSONObject attributes = new JSONObject();
          JSONArray userNameArray = new JSONArray();
          JSONArray displayNameArray = new JSONArray();
          
          userNameArray.put(u.getUid());
          displayNameArray.put(u.getDisplayName());
          
          user.put("username", userNameArray);
          user.put("givenName", u.getGivenName());
          user.put("sn", u.getSn());
          user.put("displayName", displayNameArray);
          
          attributes.put("attributes", user);
          userList.put(attributes);
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
