package edu.wisc.my.profile.service;

import edu.wisc.my.profile.model.TypeValue;

public interface EmergencyPhoneNumberService{
    
    /**
     * Gets a user's emergency Phone Numbers
     * @param netid the uid or net id of user
     * @return a TypeValue array representing the phone numbers
     */
    public TypeValue[] getEmergencyPhoneNumbers(String netid);
    
    /**
     * Sets the emergency phone number for a given user and returns the saved
     * phone numbers
     * @param phoneNumbers
     * @return the phones number of that individual
     * @throws Exception 
     */
    public TypeValue[] setEmergencyPhoneNumbers(String netid, TypeValue[] phoneNumbers) throws Exception;
    
}
