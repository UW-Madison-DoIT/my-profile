package edu.wisc.my.profile.service;

import edu.wisc.my.profile.model.ContactInformation;

public interface LocalContactInformationService {
  
  public ContactInformation getContactInfo(String netId);
  
  public ContactInformation setContactInfo(String netId, ContactInformation contactInformation) throws Exception;

}
