package edu.wisc.my.profile.service;

import java.util.List;

import edu.wisc.my.profile.model.User;

public interface SearchUsersService {
    
    /**
     * Get a list of users based on search term
     * @param searchTerm
     * @return an alphabetized list based on last name, first name
     */
    List<User> getUsers(String username, String manifestGroups, String searchTerm);
    
}
