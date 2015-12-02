package edu.wisc.my.profile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;


import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.StoredProcedure;

import edu.wisc.my.profile.model.User;


public class SearchUsersProcedure extends StoredProcedure {
    
    public SearchUsersProcedure(DataSource dataSource){
        super(dataSource, "PERSONPROFILE.PERSONPROFILE.GET_SEARCH_PERSON3");
        this.declareParameter( new SqlOutParameter( "output", oracle.jdbc.OracleTypes.CURSOR, new RowMapper<User>() { 
            public User mapRow(ResultSet argResults, int argRowNum ) throws SQLException {
                User tempUser = new User();
                tempUser.setFirstName(argResults.getString("FIRST_NAME"));
                tempUser.setPvi(argResults.getString("PVI"));
                tempUser.setLastName(argResults.getString("LAST_NAME"));
                tempUser.setMiddleName(argResults.getString("MIDDLE_NAME"));
                tempUser.setBirthDate(argResults.getDate("BIRTHDATE"));
                tempUser.setCampusID((argResults.getString("ISIS_CAMPUS_ID")));
                return tempUser;
            } 
          }) 
        );
        this.declareParameter(new SqlParameter("firstName", Types.VARCHAR));
        this.declareParameter(new SqlParameter("lastName", Types.VARCHAR));
        this.setFunction(true);
        compile();
    }
    
    /**
     * Searches are returns users based on inputs
     * @param firstName search will append wild cards before and after input.  Can be null
     * @param lastName search will append wild cards before and after input.  Can be null
     * @return Map of users in <"output", ListOfUsers> format
     */
    public Map<String, Object> searchUsers(String firstName, String lastName){
        final Map<String, String> args = new LinkedHashMap<String, String>();
        args.put("firstName", (firstName!=null ? firstName.toUpperCase() : null));
        args.put("lastName", (lastName!=null ? lastName.toUpperCase() : null));
        Map<String, Object> output = this.execute(args);
        return output;
    }
    
}
