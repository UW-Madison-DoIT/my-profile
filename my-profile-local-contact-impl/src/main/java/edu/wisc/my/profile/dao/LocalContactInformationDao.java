package edu.wisc.my.profile.dao;

import edu.wisc.my.profile.model.ContactInformation;

public interface LocalContactInformationDao {
  public ContactInformation getContactInfo(String netId);
  
  public ContactInformation setContactInfo(String netId, ContactInformation contactInformation);
}
