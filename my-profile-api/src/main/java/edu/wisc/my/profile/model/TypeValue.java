package edu.wisc.my.profile.model;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.wisc.my.profile.log.MaskedLoggable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TypeValue implements MaskedLoggable {
  
  public TypeValue() {
    
  }
  
  public TypeValue(String type, String value) {
    this.type = type;
    this.value = value;
  }
  
  private String type;
  private String value;
  private DateTime lastModified;
  
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
  
  public DateTime getLastModified() {
      return lastModified;
  }
  
  public void setLastModified(DateTime lastModified) {
      this.lastModified = lastModified;
  }
  
  public boolean isEmpty() {
    return StringUtils.isBlank(type) && StringUtils.isBlank(value);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }
  
  @Override
  public String toString(){
      //Converter not needed if we used java 8
      Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
      return gson.toJson(this);
  }
  
  /**
   * Use for logging when masking values is needed.  Value information is masked.
   * @return a JSON representation with the value data masked
   */
  @Override
  public String toStringForLogging(){
      StringBuilder builder = new StringBuilder()
      .append("{\"type\":\"")
      .append(this.type)
      .append("\",\"value\":\"")
      .append(StringUtils.repeat("X", this.value!=null?this.value.length():0)+"\",")
      .append("{\"lastModified\":\"")
      .append(this.lastModified)
      .append("\"}");
      return builder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TypeValue other = (TypeValue) obj;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }
}
