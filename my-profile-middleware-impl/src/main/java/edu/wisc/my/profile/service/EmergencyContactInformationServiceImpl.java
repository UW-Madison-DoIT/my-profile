package edu.wisc.my.profile.service;

import org.springframework.stereotype.Service;

import edu.wisc.my.profile.model.ContactInformation;

@Service
public class EmergencyContactInformationServiceImpl implements EmergencyContactInformationService {

  @Override
  public ContactInformation[] getContactInfo(String netId) {
    // TODO Auto-generated method stub
    return  new ContactInformation[] {};
  }

  @Override
  public ContactInformation[] setContactInfo(String netId, ContactInformation[] contactInformation)
      throws Exception {
    // TODO Auto-generated method stub
    return new ContactInformation[] {};
  }

  @Override
  public ContactInformation[] getContactInfo(String username, String manifestGroups, String netId) {
    // TODO Auto-generated method stub
    return  new ContactInformation[] {};
  }

}
