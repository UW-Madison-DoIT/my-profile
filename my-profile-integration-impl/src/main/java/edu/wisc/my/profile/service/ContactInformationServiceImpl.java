package edu.wisc.my.profile.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.wisc.hr.dao.bnsemail.BusinessEmailUpdateDao;
import edu.wisc.hr.dao.person.ContactInfoDao;
import edu.wisc.hr.dm.bnsemail.PreferredEmail;
import edu.wisc.hr.dm.person.Address;
import edu.wisc.hr.dm.person.PersonInformation;
import edu.wisc.my.profile.model.ContactAddress;
import edu.wisc.my.profile.model.ContactInformation;
import edu.wisc.my.profile.model.TypeValue;

@Service
public class ContactInformationServiceImpl implements ContactInformationService {
  
  private String businessEmailRolesPreferences = "businessEmailRoles";
  private static enum AddrTypes {Home, Office};
  private ContactInfoDao contactInfoDao;
  private BusinessEmailUpdateDao businessEmailUpdateDao;
  //private PreferredNameService preferredNameService;
  
  public void setBusinessEmailRolesPreferences(String businessEmailRolesPreferences) {
      this.businessEmailRolesPreferences = businessEmailRolesPreferences;
  }

  @Autowired
  public void setBusinessEmailUpdateDao(BusinessEmailUpdateDao businessEmailUpdateDao) {
      this.businessEmailUpdateDao = businessEmailUpdateDao;
  }
  /*
  
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
      mapAddressToContactAddress(contactInformation, personalData.getOfficeAddress(), AddrTypes.Office);
    }

    if(personalData.getHomeAddress() != null) {
      mapAddressToContactAddress(contactInformation, personalData.getHomeAddress(), AddrTypes.Home);
    }
    
    PreferredEmail preferedEmail = businessEmailUpdateDao.getPreferedEmail(emplId);
    if(preferedEmail != null) {
      contactInformation.getEmails().add(new TypeValue(null,preferedEmail.getEmail()));
    }
    
    
    return contactInformation;
  }
  
  private void mapAddressToContactAddress(ContactInformation info, Address address, AddrTypes type) {
    ContactAddress newAddr = new ContactAddress();
    List<String> lines = new ArrayList<String>();
    if(!StringUtils.isBlank(address.getRoomNumber())) {
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
    newAddr.setType(type.toString());
    
    info.getAddresses().add(newAddr);
    
    if(!StringUtils.isBlank(address.getPrimaryPhone())) {
      info.getPhoneNumbers().add(new TypeValue(type.toString(),address.getPrimaryPhone()));
      
    }
  }

}
