package edu.wisc.my.profile.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

import edu.wisc.my.profile.model.SearchTerm;
import edu.wisc.my.profile.model.User;


public class SearchUsersMiddlewareDaoImpl implements SearchUsersMiddlewareDao{
    
    private DataSource datasource;
    
    public SearchUsersMiddlewareDaoImpl(DataSource ds) {
        this.datasource = ds;
      }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsersBySearchTerm(SearchTerm searchTerm) {
        List<User> userList = new ArrayList<User>();
        //Return empty list if search term is null or no last name searched
        if(searchTerm == null || StringUtils.isEmpty(searchTerm.getLastName())){
            return userList;
        }
        
        SearchUsersProcedure sup = new SearchUsersProcedure(datasource);
        Map<String, Object> result = new HashMap<String, Object>();
        result = sup.searchUsers(searchTerm.getFirstName(), searchTerm.getLastName());
        Iterator<Entry<String, Object>> entries = result.entrySet().iterator();
        
        while (entries.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) entries.next();
            //the value is the list of users
            List<User> tempUserList = (List<User>) entry.getValue();
            userList.addAll(tempUserList);
        }
        Collections.sort(userList);
        return userList;
    }
    
}
