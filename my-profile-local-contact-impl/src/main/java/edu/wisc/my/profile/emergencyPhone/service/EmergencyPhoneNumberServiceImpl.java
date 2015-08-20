package edu.wisc.my.profile.emergencyPhone.service;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.wisc.my.profile.emergencyPhone.dao.EmergencyPhoneNumberDao;
import edu.wisc.my.profile.model.TypeValue;
import edu.wisc.my.profile.service.EmergencyPhoneNumberService;

@Service
public class EmergencyPhoneNumberServiceImpl implements EmergencyPhoneNumberService{
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private EmergencyPhoneNumberDao dao;
    
    private String adminGroup;
    
    @Autowired
    public void setEmergencyPhoneNumberDao(EmergencyPhoneNumberDao dao) {
      this.dao = dao;
    }
    
    @Value("${adminGroup}")
    public void setAdminGroup(String group) {
      adminGroup = group;
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

    @Override
    public TypeValue[] getEmergencyPhoneNumbers(String username, String manifestGroups, String netId) {
        if(StringUtils.isNotBlank(adminGroup) && manifestGroups.contains(adminGroup)){
            logger.info("User {} lookuped up user {} emergency phone number", username, netId);
            return dao.getData(netId);
        }else {
            //TODO : implement uw-spring-security
            logger.warn("Security violation: User {} looked up user{} emergency phone number", username, netId);
            throw new SecurityException("Security violation, forbidden");
        }
    }
    
}
