package edu.wisc.my.profile.dao;

import java.util.List;

import edu.wisc.my.profile.model.User;

public interface SearchUsersMiddlewareDao{
    
    /**
     * Gets the List of users that matches the search term
     * @param searchTerm
     * @return An alphabetized list of users based on Last Name, first name. Empty for no search hits
     */
    public List<User> getUsersBySearchTerm(String searchTerm);
    
}
