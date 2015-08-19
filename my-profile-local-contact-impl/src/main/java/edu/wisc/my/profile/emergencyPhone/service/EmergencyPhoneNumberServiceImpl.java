package edu.wisc.my.profile.emergencyPhone.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.wisc.my.profile.emergencyPhone.dao.EmergencyPhoneNumberDao;
import edu.wisc.my.profile.model.TypeValue;
import edu.wisc.my.profile.service.EmergencyPhoneNumberService;

@Service
public class EmergencyPhoneNumberServiceImpl implements EmergencyPhoneNumberService{

    private EmergencyPhoneNumberDao dao;
    
    @Autowired
    public void setEmergencyPhoneNumberDao(EmergencyPhoneNumberDao dao) {
      this.dao = dao;
    }
    
    @Override
    public TypeValue[] getEmergencyPhoneNumbers(String netId) {
        return dao.getData(netId);
    }

    @Override
    public TypeValue[] setEmergencyPhoneNumbers(String netId,
            TypeValue[] phoneNumbers) throws Exception {
        for(TypeValue phoneNumber: phoneNumbers){
            phoneNumber.setLastModified(DateTime.now());
        }
        return dao.setData(netId, phoneNumbers);
    }
    
}
