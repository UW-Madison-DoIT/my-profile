package edu.wisc.my.profile.service;

import java.util.List;

import edu.wisc.my.profile.model.SearchTerm;
import edu.wisc.my.profile.model.User;

public interface SearchUsersService {
    
    /**
     * Get a list of users based on search term
     * @param username of requester
     * @param manifestGroups to authorize by
     * @param searchTerm search criteria
     * @return an alphabetized list based on last name, first name. Will return
     * empty list if searchTerm is null or searchTerm.last name is empty
     */
    List<User> getUsers(String username, String manifestGroups, SearchTerm searchTerm);
    
}
