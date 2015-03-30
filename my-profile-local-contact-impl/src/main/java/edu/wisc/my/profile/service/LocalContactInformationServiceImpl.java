package edu.wisc.my.profile.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.wisc.my.profile.dao.LocalContactInformationDao;
import edu.wisc.my.profile.model.ContactInformation;

@Service
public class LocalContactInformationServiceImpl implements LocalContactInformationService {
  
  private LocalContactInformationDao dao;
  
  @Autowired
  public void setLocalContactInfoDao(LocalContactInformationDao lcio) {
    this.dao = lcio;
  }
  

  @Override
  public ContactInformation getContactInfo(String netId) {
    return dao.getContactInfo(netId);
  }

  @Override
  public ContactInformation setContactInfo(String netId, ContactInformation contactInformation) {
    contactInformation.setLastModified(DateTime.now());
    return dao.setContactInfo(netId, contactInformation);
  }

}
