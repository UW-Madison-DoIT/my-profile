package edu.wisc.my.profile.service;

import edu.wisc.my.profile.model.ContactInformation;

import org.springframework.stereotype.Service;

@Service
public class ContactInformationServiceImpl implements ContactInformationService {
  
  @Override
  public ContactInformation getContactInfo(String username, String emplId, String pvi) {
    ContactInformation contactInformation = new ContactInformation();
    return contactInformation;
  }

}