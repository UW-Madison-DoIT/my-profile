package edu.wisc.my.profile.dao;

import java.util.List;

import edu.wisc.my.profile.model.SearchTerm;
import edu.wisc.my.profile.model.User;

public interface SearchUsersMiddlewareDao{
    
    /**
     * Gets the List of users that matches the search term
     * @param searchTerm
     * @return An alphabetized list of users based on userId. Empty for no
     *         search hits or if searchTerm has no last name criteria or null
     */
    public List<User> getUsersBySearchTerm(SearchTerm searchTerm);
    
}
