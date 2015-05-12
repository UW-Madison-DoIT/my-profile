package edu.wisc.my.profile.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class ContactInformation {
  private DateTime lastModified;
  private String id;
  private String legalName;
  private String preferredName;
  private String relationship;
  private List<TypeValue> languages;
  private List<TypeValue> phoneNumbers = new ArrayList<TypeValue>();
  private List<TypeValue> emails = new ArrayList<TypeValue>();
  private List<ContactAddress> addresses = new ArrayList<ContactAddress>();
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getLegalName() {
    return legalName;
  }
  public void setLegalName(String legalName) {
    this.legalName = legalName;
  }
  public String getPreferredName() {
    return preferredName;
  }
  public void setPreferredName(String preferredName) {
    this.preferredName = preferredName;
  }
  public List<TypeValue> getPhoneNumbers() {
    return phoneNumbers;
  }
  public void setPhoneNumbers(List<TypeValue> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }
  public List<TypeValue> getEmails() {
    return emails;
  }
  public void setEmails(List<TypeValue> emails) {
    this.emails = emails;
  }
  public List<ContactAddress> getAddresses() {
    return addresses;
  }
  public void setAddresses(List<ContactAddress> addresses) {
    this.addresses = addresses;
  }
  public DateTime getLastModified() {
    return lastModified;
  }
  public void setLastModified(DateTime lastModified) {
    this.lastModified = lastModified;
  }
  public String getRelationship() {
    return relationship;
  }
  public void setRelationship(String relationship) {
    this.relationship = relationship;
  }
  public List<TypeValue> getLanguages() {
    return languages;
  }
  public void setLanguages(List<TypeValue> languages) {
    this.languages = languages;
  }
}
