package edu.wisc.my.profile.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.wisc.hr.dao.person.ContactInfoDao;
import edu.wisc.hr.dm.person.Address;
import edu.wisc.hr.dm.person.PersonInformation;
import edu.wisc.my.profile.model.ContactAddress;
import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.model.TypeValue;

@Service
public class ContactInformationServiceImpl implements ContactInformationService {
  
  private String businessEmailRolesPreferences = "businessEmailRoles";
  private ContactInfoDao contactInfoDao;
  //private BusinessEmailUpdateDao businessEmailUpdateDao;
  //private PreferredNameService preferredNameService;
  
  public void setBusinessEmailRolesPreferences(String businessEmailRolesPreferences) {
      this.businessEmailRolesPreferences = businessEmailRolesPreferences;
  }

  /*@Autowired
  public void setBusinessEmailUpdateDao(BusinessEmailUpdateDao businessEmailUpdateDao) {
      this.businessEmailUpdateDao = businessEmailUpdateDao;
  }
  
  @Autowired
  public void setPreferredNameService(PreferredNameService service) {
      this.preferredNameService = service;
  }*/

  @Autowired
  public void setContactInfoDao(ContactInfoDao contactInfoDao) {
      this.contactInfoDao = contactInfoDao;
  }

  @Override
  public ContactInformation getContactInfo(String emplId) {
    ContactInformation contactInformation = new ContactInformation();
    
    //get basic information
    PersonInformation personalData = contactInfoDao.getPersonalData(emplId);
    contactInformation.setLegalName(personalData.getName());
    contactInformation.setId(emplId);
    
    //get address info and phone info
    if(personalData.getOfficeAddress() != null) {
      mapAddressToContactAddress(contactInformation, personalData.getOfficeAddress());
    }

    if(personalData.getHomeAddress() != null) {
      mapAddressToContactAddress(contactInformation, personalData.getHomeAddress());
    }
    
    
    return contactInformation;
  }
  
  private void mapAddressToContactAddress(ContactInformation info, Address address) {
    ContactAddress newAddr = new ContactAddress();
    List<String> lines = new ArrayList<String>();
    if(address.getRoomNumber() != null) {
      lines.add("Room " + address.getRoomNumber());
    }
    
    if(!StringUtils.isBlank(address.getAddress1())) {
      lines.add(address.getAddress1());
    }
    if(!StringUtils.isBlank(address.getAddress2())) {
      lines.add(address.getAddress2());
    }
    if(!StringUtils.isBlank(address.getAddress3())) {
      lines.add(address.getAddress3());
    }
    
    newAddr.setAddressLines(lines);
    newAddr.setCity(address.getCity());
    newAddr.setPostalCode(address.getZip());
    newAddr.setState(address.getState());
    
    info.getAddresses().add(newAddr);
    
    if(address.getPrimaryPhone() != null) {
      info.getPhoneNumbers().add(new TypeValue(null,address.getPrimaryPhone()));
      
    }
  }

}
