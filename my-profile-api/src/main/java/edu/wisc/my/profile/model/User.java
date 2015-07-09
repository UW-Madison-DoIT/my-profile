package edu.wisc.my.profile.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Comparable{


    @JsonProperty("firstName")
    public String firstName;
    @JsonProperty("lastName")
    public String lastName;
    @JsonProperty("PVI")
    public String pvi;
    @JsonProperty("middleName")
    public String middleName;
    
   
    /**
     * @return the givenName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param givenName the givenName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }
    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    /**
     * @return the sn (surname)
     */
    public String getLastName() {
        return this.lastName;
    }
    /**
     * @param sn the sn (surname) to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * @return the pvi
     */
    public String getPvi() {
        return pvi;
    }
    /**
     * @param pvi the pvi to set
     */
    public void setPvi(String pvi) {
        this.pvi = pvi;
    }

    @Override
    public int compareTo(Object o) {
        if(o == null){
            return -1;
        }
        User user = (User) o;
        int result = this.lastName.compareTo(user.lastName);
        //If last names are not equal return the comparison
        if(result!=0){
            return result;
        }else{ //Else compare users given name
            return this.firstName.compareTo(user.firstName);
        }
    }
    
   
    
}
