package edu.wisc.my.profile.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("$$hashKey")
public class ContactAddress {
  private String type;
  private List<String> addressLines = new ArrayList<String>();
  private String city;
  private String country;
  private String state;
  private String postalCode;
  private String comment;
  private DateTime effectiveDate;
  private DateTime expirationDate;
  private boolean edit;
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public List<String> getAddressLines() {
    return addressLines;
  }
  public void setAddressLines(List<String> addressLines) {
    this.addressLines = addressLines;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getPostalCode() {
    return postalCode;
  }
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }
  public String getComment() {
    return comment;
  }
  public void setComment(String comment) {
    this.comment = comment;
  }
  public boolean isEdit() {
    return edit;
  }
  public void setEdit(boolean edit) {
    this.edit = edit;
  }
  public DateTime getEffectiveDate() {
    return effectiveDate;
  }
  public void setEffectiveDate(DateTime effectiveDate) {
    this.effectiveDate = effectiveDate;
  }
  public DateTime getExpirationDate() {
    return expirationDate;
  }
  public void setExpirationDate(DateTime expirationDate) {
    this.expirationDate = expirationDate;
  }
}
