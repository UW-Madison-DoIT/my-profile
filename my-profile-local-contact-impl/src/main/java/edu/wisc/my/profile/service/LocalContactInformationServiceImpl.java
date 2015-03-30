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
    return dao.getData(netId);
  }

  @Override
  public ContactInformation setContactInfo(String netId, ContactInformation contactInformation) throws Exception {
    contactInformation.setLastModified(DateTime.now());
    return dao.setData(netId, contactInformation);
  }

}
