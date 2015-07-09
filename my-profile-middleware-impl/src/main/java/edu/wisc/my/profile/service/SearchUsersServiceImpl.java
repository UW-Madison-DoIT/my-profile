package edu.wisc.my.profile.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.wisc.my.profile.dao.SearchUsersMiddlewareDao;
import edu.wisc.my.profile.model.User;

@Service
public class SearchUsersServiceImpl implements SearchUsersService{
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private SearchUsersMiddlewareDao searchUsersDao;
    
    private String adminGroup;
    
    @Value("${adminGroup}")
    public void setAdminGroup(String group) {
      adminGroup = group;
    }
    
    @Override
    public List<User> getUsers(String username, String manifestGroups, String searchTerm) {
        if(StringUtils.isNotBlank(adminGroup) && manifestGroups.contains(adminGroup)) {
            logger.info("User {} looked up users using this search term: {}.",username, searchTerm);
            return searchUsersDao.getUsersBySearchTerm(searchTerm);
        } else {
            //TODO : implement uw-spring-security
            throw new SecurityException("Security violation, forbidden.");
        }
    }
}
