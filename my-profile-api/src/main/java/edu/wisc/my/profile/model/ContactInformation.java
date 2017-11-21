package edu.wisc.my.profile.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.wisc.my.profile.log.MaskedLoggable;

public class ContactInformation implements MaskedLoggable {
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
  
  /**
   * Returns a JSON string of the object
   */
  @Override
  public String toString(){
      //Converter not needed if we used java 8
      Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
      return gson.toJson(this);
  }

  /**
   * Use for logging when masking values is needed.
   * Data that is considered sensitive will be masked.  Fields that are masked are
   * preferredName, relationship, user inputed comments, phone numbers, emails, and address information
   * @return a JSON representation with the value data masked, useful for logging
   */
  @Override
  public String toStringForLogging(){
      StringBuilder builder = new StringBuilder();
      builder.append("{")
      .append("\"lastModified\":\""+this.lastModified+"\",")
      .append("\"edit\":"+this.edit+",")
      .append("\"id\":\""+this.id+"\",")
      .append("\"preferredName\":\""+StringUtils.repeat("X", 
              this.preferredName!=null?this.preferredName.length():0)+"\",")
      .append("\"relationship\":\""+StringUtils.repeat("X", 
              this.relationship!=null?this.relationship.length():0)+"\",")
      .append("\"comments\":\""+StringUtils.repeat("X", 
              this.comments!=null?this.comments.length():0)+"\",")
      //PhoneNumbers
      .append("\"phoneNumbers\":[");
      int phoneNumberCount=0;
      for(TypeValue phoneNumber: this.phoneNumbers){
          if(phoneNumberCount>0){
              builder.append(",");
          }
          builder.append(phoneNumber.toStringForLogging());
          phoneNumberCount++;
      }
      builder.append("],")
      //end PhoneNumbers
      //start emails
      .append("\"emails\":[");
      int emailCount=0;
      for(TypeValue email: this.emails){
          if(emailCount>0){
              builder.append(",");
          }
          builder.append(email.toStringForLogging());
          emailCount++;
      }
      builder.append("],")
      //end emails
      //start address
      .append("\"addresses\":[");
      int addressCount=0;
      for(ContactAddress address: this.addresses){
          if(addressCount>0){
              builder.append(",");
          }
          builder.append(address.toStringForLogging());
          addressCount++;
      }
      builder.append("]")
      //end addresses
      .append("}");
      String maskedString = builder.toString();
      return maskedString;
  }
  
  @Override
  public int hashCode() {
      return new HashCodeBuilder(17, 31).
              append(this.id).
              append(this.legalName).
              append(this.preferredName).
              append(this.relationship).
              append(this.lastModified).
              append(this.comments).
              append(this.phoneNumbers).
              append(this.emails).
              append(this.addresses!=null? new HashSet<ContactAddress>(this.addresses):this.addresses).
          toHashCode();
  }
  
  @Override
  public boolean equals(Object obj) {
      if (obj == null) {
          return false;
      }
      if(obj == this){
          return true;
      }
      if (!(obj instanceof ContactInformation)){
          return false;
      }

      ContactInformation rhs = (ContactInformation) obj;
      
      return new EqualsBuilder().
              append(this.id, rhs.id).
              append(this.legalName, rhs.legalName).
              append(this.preferredName, rhs.preferredName).
              append(this.relationship, rhs.relationship).
              append(this.comments, rhs.comments).
              append(this.phoneNumbers, rhs.phoneNumbers).
              append(this.emails, rhs.emails).
              append(this.addresses!=null? new HashSet<ContactAddress>(this.addresses):this.addresses, rhs.addresses!=null? new HashSet<ContactAddress>(rhs.addresses):rhs.addresses).
          isEquals();
  }
  
  
}
