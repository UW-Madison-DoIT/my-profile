package edu.wisc.my.profile.service;

import edu.wisc.my.profile.model.ContactInformation;

public interface ContactInformationService {
  
  public ContactInformation getContactInfo(String username, String emplid, String pvi);

}
