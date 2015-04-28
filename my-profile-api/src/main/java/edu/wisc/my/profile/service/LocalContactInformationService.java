package edu.wisc.my.profile.service;

import java.util.List;

import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.model.User;

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

  /**
   * Gets the contact information for another user. For admins only. blocked by spring security
   * @param username the username of the requestee (for the logging)
   * @param manifestGroups A list of manifest groups this entity is part of
   * @param netId the net id they searched for
   * @return the contact information of that individual
   */
  public ContactInformation getContactInfo(String username, String manifestGroups, String netId);

  /**
   * Returns an alphabetical list by userId list of users matching the searchTerm
   * on last Name and userId
   * @param username
   * @param manifestGroups
   * @param searchTerm
   * @return
   */
  public List<User> getUsers(String username, String manifestGroups, String searchTerm);
  
}
