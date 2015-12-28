package edu.wisc.my.profile.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
  
  @Override
  public String toString(){
      Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
      return gson.toJson(this);
      
  }
  
  /**
   * Use for logging when masking values is needed.
   * Data that is considered sensitive will be masked.  Fields that are masked are
   * type, the address information, and user inputed comments
   * @return a JSON representation with the value data masked, useful for logging
   */
  public String toStringForLogging(){
      StringBuilder builder = new StringBuilder();
      builder.append("{")
      .append("\"type\":\""+this.type+"\",")
      //Adress lines
      .append("\"addressLines\":[");
      int addressLineCount=0;
      for(String addressLine: this.addressLines){
          if(addressLineCount>0){
              builder.append(",\"");
          }
          builder.append(StringUtils.repeat("X", 
                  addressLine!=null?addressLine.length():0)+"\"");
          addressLineCount++;
      }
      builder.append("],")
      //end Address Lines
      .append("\"city\":\""+StringUtils.repeat("X", 
              this.city!=null?this.city.length():0)+"\",")
      .append("\"country\":\""+StringUtils.repeat("X", 
              this.country!=null?this.country.length():0)+"\",")
      .append("\"state\":\""+StringUtils.repeat("X", 
              this.state!=null?this.state.length():0)+"\",")
      .append("\"postalCode\":\""+StringUtils.repeat("X", 
              this.postalCode!=null?this.postalCode.length():0)+"\",")
      .append("\"comment\":\""+StringUtils.repeat("X", 
              this.comment!=null?this.comment.length():0)+"\",")
      .append("\"edit\":"+this.edit+",")
      .append("}");
      String maskedString = builder.toString();
      return maskedString;
  }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(this.type).
                append(this.addressLines).
                append(this.city).
                append(this.country).
                append(this.state).
                append(this.postalCode).
                append(this.comment).
                append(this.effectiveDate).
                append(this.expirationDate).
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
        if (!(obj instanceof ContactAddress)){
            return false;
        }
        ContactAddress rhs = (ContactAddress) obj;
        return new EqualsBuilder().
                append(this.type, rhs.type).
                append(this.addressLines, rhs.addressLines).
                append(this.city, rhs.city).
                append(this.country, rhs.country).
                append(this.state, rhs.state).
                append(this.postalCode, rhs.postalCode).
                append(this.comment, rhs.comment).
                append(this.effectiveDate, rhs.effectiveDate).
                append(this.expirationDate, rhs.expirationDate).
            isEquals();
    }
}
