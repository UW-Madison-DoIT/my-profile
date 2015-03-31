package edu.wisc.my.profile.dao;

import edu.wisc.my.profile.model.ContactInformation;

public interface LocalContactInformationDao {
  
  /**
   * Gets the data from the data store
   * @param netId the user
   * @return a contact info object. or an empty object if one didn't exist
   */
  public ContactInformation getData(String netId);
  
  /**
   * This deletes the old data (if exists), and adds in the new data object provided
   * @param netId The username
   * @param contactInformation the new contact info to save
   * @return the contact info that was saved
   * @throws Exception issues saving or parsing json
   */
  public ContactInformation setData(String netId, ContactInformation contactInformation) throws Exception;
}
