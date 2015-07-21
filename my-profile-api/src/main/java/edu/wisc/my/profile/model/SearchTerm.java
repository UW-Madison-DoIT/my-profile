package edu.wisc.my.profile.model;

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
}
