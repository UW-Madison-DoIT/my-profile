package edu.wisc.my.profile.model;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TypeValue {
  
  public TypeValue() {
    
  }
  
  public TypeValue(String type, String value) {
    this.type = type;
    this.value = value;
  }
  
  private String type;
  private String value;
  
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
