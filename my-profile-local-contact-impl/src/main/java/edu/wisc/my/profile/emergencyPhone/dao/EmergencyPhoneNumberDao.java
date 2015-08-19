package edu.wisc.my.profile.emergencyPhone.dao;

import edu.wisc.my.profile.model.TypeValue;

public interface EmergencyPhoneNumberDao {
    
    /**
     * Gets emergency phone numbers for user
     * @param netid
     * @return a TypeValue representing phone numbers
     */
    public TypeValue[] getData(String netId);
    
    
    /**
     * Sets emergency phone numbers returning the saved phone numbers
     * @param netid
     * @param phoneNumbers
     * @return the saved phone numbers
     * @throws Exception
     */
    public TypeValue[] setData(String netId, TypeValue[] phoneNumbers) throws Exception;
    
}
