package edu.wisc.my.profile.service;

import edu.wisc.my.profile.model.ContactInformation;

public interface LocalContactInformationService {
  
  /**
   * Gets the users local contact information
   * @param netId the uid or net id of the user
   * @return returns a contact info object
   */
  public ContactInformation getContactInfo(String netId);
  
  /**
   * Sets the contact information for said user
   * This also updates the lastModified timestamp to now()
   * @param netId the username
   * @param contactInformation the contact info object to set
   * @return returns the same contact information
   * @throws Exception issues saving to data store
   */
  public ContactInformation setContactInfo(String netId, ContactInformation contactInformation) throws Exception;

}
