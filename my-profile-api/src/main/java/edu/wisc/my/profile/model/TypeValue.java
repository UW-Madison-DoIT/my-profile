package edu.wisc.my.profile.model;

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
}
