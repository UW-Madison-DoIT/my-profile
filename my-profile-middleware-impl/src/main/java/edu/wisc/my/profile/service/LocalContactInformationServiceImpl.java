package edu.wisc.my.profile.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.wisc.my.profile.dao.LocalContactMiddlewareDao;
import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.model.User;

@Service
public class LocalContactInformationServiceImpl implements LocalContactInformationService {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private LocalContactMiddlewareDao dao;
  private String adminGroup;
  
  @Value("${adminGroup}")
  public void setAdminGroup(String group) {
    adminGroup = group;
  }
  
  @Override
  public ContactInformation getContactInfo(String netId) {
    return dao.getContactInfo(netId);
  }

  @Override
  public ContactInformation setContactInfo(String netId, ContactInformation contactInformation)
      throws Exception {
    
    //reset time on last modified
    contactInformation.setLastModified(DateTime.now());
    
    //save
    return dao.setContactInfo(netId, contactInformation);
  }

  @Override
  public ContactInformation getContactInfo(String username, String manifestGroups, String netId) {
    if(StringUtils.isNotBlank(adminGroup) && manifestGroups.contains(adminGroup)) {
      logger.info("User {} looked up user {}.",username, netId);
      return dao.getContactInfo(netId);
    } else {
      //TODO : implement uw-spring-security
      logger.warn("Security violation: User {} looked up user {}.",username, netId);
      throw new SecurityException("Security violation, forbidden.");
    }
  }

  @Override
  public List<User> getUsers(String username, String manifestGroups, String searchTerm) {
    // TODO implement this once method is provided
    return null;
  }
  
}
