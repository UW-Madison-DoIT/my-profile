package edu.wisc.my.profile.dao;

import edu.wisc.my.profile.model.ContactInformation;

public interface LocalContactMiddlewareDao {

  ContactInformation getContactInfo(String netId);

  ContactInformation setContactInfo(String netId, ContactInformation contactInformation);
}
