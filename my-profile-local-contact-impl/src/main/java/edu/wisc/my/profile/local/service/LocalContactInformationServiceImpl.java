package edu.wisc.my.profile.local.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.wisc.my.profile.local.dao.LocalContactInformationDao;
import edu.wisc.my.profile.local.dao.LocalUserDao;
import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.model.User;
import edu.wisc.my.profile.service.LocalContactInformationService;

@Service
public class LocalContactInformationServiceImpl implements LocalContactInformationService {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  private LocalContactInformationDao dao;
  
  private LocalUserDao userDao;
  
  
  private String adminGroup;
  
  @Value("${adminGroup}")
  public void setAdminGroup(String group) {
    adminGroup = group;
  }
  
  @Autowired
  public void setLocalContactInfoDao(LocalContactInformationDao lcio) {
    this.dao = lcio;
  }
  
  @Autowired
  public void setLocalUserDao(LocalUserDao lcio) {
    this.userDao = lcio;
  }
  

  @Override
  public ContactInformation getContactInfo(String netId) {
    return dao.getData(netId);
  }

  @Override
  public ContactInformation setContactInfo(String netId, ContactInformation contactInformation) throws Exception {
    contactInformation.setLastModified(DateTime.now());
    return dao.setData(netId, contactInformation);
  }

  @Override
  public ContactInformation getContactInfo(String username, String manifestGroups, String netId) {
    if(StringUtils.isNotBlank(adminGroup) && manifestGroups.contains(adminGroup)) {
      logger.info("User {} looked up user {}.",username, netId);
      return dao.getData(netId);
    } else {
      //TODO : implement uw-spring-security
      logger.warn("Security violation: User {} looked up user {}.",username, netId);
      throw new SecurityException("Security violation, forbidden.");
    }

  }

  @Override
  public List<User> getUsers(String username, String manifestGroups, String searchTerm) {
      if(StringUtils.isNotBlank(adminGroup) && manifestGroups.contains(adminGroup)) {
          logger.info("User {} looked up users using this search term: {}.",username, searchTerm);
          return userDao.getUsersBySearchTerm(searchTerm);
      } else {
          //TODO : implement uw-spring-security
          throw new SecurityException("Security violation, forbidden.");
      }
  }

}
