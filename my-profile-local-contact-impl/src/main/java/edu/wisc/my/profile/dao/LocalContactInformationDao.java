package edu.wisc.my.profile.dao;

import edu.wisc.my.profile.model.ContactInformation;

public interface LocalContactInformationDao {
  public ContactInformation getData(String netId);
  
  public ContactInformation setData(String netId, ContactInformation contactInformation) throws Exception;
}
