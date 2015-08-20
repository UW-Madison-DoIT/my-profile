package edu.wisc.my.profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.wisc.my.profile.dao.EmergencyPhoneNumberDao;
import edu.wisc.my.profile.model.TypeValue;

@Service
public class EmergencyPhoneNumberServiceImpl implements EmergencyPhoneNumberService {
    
    @Autowired
    private EmergencyPhoneNumberDao dao;

    @Override
    public TypeValue[] getEmergencyPhoneNumbers(String netId) {
        return dao.getPhoneNumbers(netId);
    }

    @Override
    public TypeValue[] setEmergencyPhoneNumbers(String netId, TypeValue[] phoneNumbers) throws Exception{
        return dao.setPhoneNumbers(netId, phoneNumbers);
    }

}
