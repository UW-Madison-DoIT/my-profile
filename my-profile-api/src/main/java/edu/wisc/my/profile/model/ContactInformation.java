package edu.wisc.my.profile.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ContactInformation {
  private DateTime lastModified;
  private boolean edit;
  private String id;
  private String legalName;
  private String preferredName;
  private String relationship;
  private String comments;
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
  public String getComments() {
    return comments;
  }
  public void setComments(String comments) {
    this.comments = comments;
  }
  public boolean isEdit() {
    return edit;
  }
  public void setEdit(boolean edit) {
    this.edit = edit;
  }
  @Override
  public String toString(){
      //Converter not needed if we used java 8
      Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
      return gson.toJson(this);
  }
}
