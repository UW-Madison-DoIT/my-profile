package edu.wisc.my.profile.service;

import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.wisc.my.profile.dao.EmergencyContactMiddlewareDao;
import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.model.TypeValue;

@Service
public class EmergencyContactInformationServiceImpl implements EmergencyContactInformationService {
  
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private EmergencyContactMiddlewareDao dao;
  private String adminGroup;
  
  @Value("${adminGroup}")
  public void setAdminGroup(String group) {
    adminGroup = group;
  }

  @Override
  public ContactInformation[] getContactInfo(String netId) {
    return dao.getData(netId);
  }

  @Override
  public ContactInformation[] setContactInfo(String netId, ContactInformation[] contactInformation)
      throws Exception {
      for(ContactInformation contact: contactInformation){
          List<TypeValue> phoneNumbersWithLimitedChars = contact.getPhoneNumbers();
          for(ListIterator<TypeValue> iter = phoneNumbersWithLimitedChars.listIterator(); iter.hasNext();){
              TypeValue phoneNumber = iter.next();
              phoneNumber.setValue(StringUtils.left(phoneNumber.getValue(), 25));
              iter.set(phoneNumber);
          }
          contact.setPhoneNumbers(phoneNumbersWithLimitedChars);
      }
      
    return dao.setData(netId, contactInformation);
  }

  @Override
  public ContactInformation[] getContactInfo(String username, String manifestGroups, String netId) {
    if(StringUtils.isNotBlank(adminGroup) && manifestGroups.contains(adminGroup)) {
      logger.info("User {} looked up user {}.",username, netId);
      return dao.getData(netId);
    } else {
      //TODO : implement uw-spring-security
      logger.warn("Security violation: User {} looked up user {}.",username, netId);
      throw new SecurityException("Security violation, forbidden.");
    }
  }

}
