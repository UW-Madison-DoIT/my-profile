package edu.wisc.my.profile.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import edu.wisc.my.profile.model.User;


public class SearchUsersMiddlewareDaoImpl implements SearchUsersMiddlewareDao{
    
    private DataSource datasource;
    
    public SearchUsersMiddlewareDaoImpl(DataSource ds) {
        this.datasource = ds;
      }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsersBySearchTerm(String searchTerm) {
        SearchUsersProcedure sup = new SearchUsersProcedure(datasource);
        Map<String, Object> result = new HashMap<String, Object>();
        result = sup.searchUsers(null, searchTerm.toUpperCase());
        Iterator<Entry<String, Object>> entries = result.entrySet().iterator();
        List<User> userList = new ArrayList<User>();
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
