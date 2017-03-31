package edu.wisc.my.profile.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.wisc.my.profile.dao.EmergencyPhoneNumberDao;
import edu.wisc.my.profile.model.TypeValue;

@Service
public class EmergencyPhoneNumberServiceImpl implements EmergencyPhoneNumberService {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    private String adminGroup;
    
    @Autowired
    private EmergencyPhoneNumberDao dao;
    
    @Value("${adminGroup}")
    public void setAdminGroup(String group) {
      adminGroup = group;
    }

    @Override
    public TypeValue[] getEmergencyPhoneNumbers(String netId) {
        return dao.getPhoneNumbers(netId);
    }

    @Override
    public TypeValue[] setEmergencyPhoneNumbers(String netId, TypeValue[] phoneNumbers) throws Exception{
        //Limit the characters going to uw-middleware to 25
        for(TypeValue phoneNumber : phoneNumbers){
            phoneNumber.setValue(StringUtils.left(phoneNumber.getValue(), 25));
        }
        return dao.setPhoneNumbers(netId, phoneNumbers);
    }

    @Override
    public TypeValue[] getEmergencyPhoneNumbers(String username, String manifestGroups, String netId) {
        if(StringUtils.isNotBlank(adminGroup) && manifestGroups.contains(adminGroup)){
            logger.info("User {} lookuped up user {} emergency phone number", username, netId);
            return dao.getPhoneNumbers(netId);
        }else {
            //TODO : implement uw-spring-security
            logger.warn("Security violation: User {} looked up user{} emergency phone number", username, netId);
            throw new SecurityException("Security violation, forbidden");
        }
    }

}
