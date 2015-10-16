package edu.wisc.my.profile.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SearchTerm{
    
    private String firstName;
    private String lastName;
    
    /**
     * Constructs a SearchTerm
     */
    public SearchTerm(){
    }
    
    /**
     * Constructs a SearchTerm with firstName and lastName
     * @param firstName
     * @param lastName
     */
    public SearchTerm(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("firstName", this.firstName)
            .append("lastName", this.lastName)
            .toString();
    }
}
