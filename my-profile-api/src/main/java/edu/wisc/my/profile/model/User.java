package edu.wisc.my.profile.model;

import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Comparable{
    
    @JsonProperty("uid")
    public String uid;
    @JsonProperty("givenName")
    public String givenName;
    @JsonProperty("sn")
    public String sn;
    @JsonProperty("displayName")
    public String displayName;
    /**
     * @return the uid (userId)
     */
    public String getUid() {
        return uid;
    }
    /**
     * @param uid the uid (userId) to set
     */
    public void setUid(String uid) {
        this.uid = uid;
    }
    /**
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }
    /**
     * @param givenName the givenName to set
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
    /**
     * @return the sn (surname)
     */
    public String getSn() {
        return sn;
    }
    /**
     * @param sn the sn (surname) to set
     */
    public void setSn(String sn) {
        this.sn = sn;
    }
    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }
    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public int compareTo(Object o) {
        if(o == null){
            return -1;
        }
        User user = (User) o;
        return(this.uid.compareTo(user.uid));
    }
}
