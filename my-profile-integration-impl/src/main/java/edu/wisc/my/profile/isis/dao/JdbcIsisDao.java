package edu.wisc.my.profile.isis.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import edu.wisc.my.profile.model.ContactAddress;
import edu.wisc.my.profile.model.TypeValue;

/**
 * Implementation of the IsisDao APIs using Spring JDBC's {@link SimpleJdbcCall} to perform calls
 * to Oracle stored procedures.
 * 
 * Note that this class is annotated as a Repository. Spring will automatically create an instance
 * of this class and configure all Autowired methods appropriately before calling the afterPropertiesSet
 * 
 * @author Eric Dalquist
 * @version $Revision: 1.8 $
 */
public class JdbcIsisDao implements InitializingBean, IsisDao {
    protected final Log logger = LogFactory.getLog(this.getClass());
    
    private DataSource dataSource;
    private int queryTimeout = 9;

    private SimpleJdbcTemplate simpleJdbcTemplate;
    private SimpleJdbcCall isisAuthenticateCall;
    private SimpleJdbcCall isisGetAddressCall;
    private SimpleJdbcCall isisGetPhoneCall;
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setQueryTimeout(int queryTimeout) {
        this.queryTimeout = queryTimeout;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
        jdbcTemplate.setQueryTimeout(this.queryTimeout);
        
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(jdbcTemplate);
        
        this.setupAuthenticateCall(jdbcTemplate);
        this.setupGetAddressCall(jdbcTemplate);
        this.setupGetPhoneCall(jdbcTemplate);
    }

    protected final void setupAuthenticateCall(JdbcTemplate jdbcTemplate) {
        this.isisAuthenticateCall = new SimpleJdbcCall(jdbcTemplate);
        
        //EASI2.ISIS_AUTHENTICATE
        //PROCEDURE SSO_PS8_token (
        //  emplid       IN VARCHAR2,
        //  m_ip         IN VARCHAR2,
        //  token_type   IN VARCHAR2,
        //  token       OUT VARCHAR2,
        //  return_code OUT NUMBER,
        //  return_mesg OUT VARCHAR2);
        
        this.isisAuthenticateCall.withProcedureName("EASI2.ISIS_AUTHENTICATE.SSO_PS8_token");
        this.isisAuthenticateCall.withoutProcedureColumnMetaDataAccess();
        this.isisAuthenticateCall.declareParameters(
                new SqlParameter("emplid", Types.VARCHAR),
                new SqlParameter("m_ip", Types.VARCHAR),
                new SqlParameter("token_type", Types.VARCHAR),
                
                new SqlOutParameter("token", Types.VARCHAR),
                new SqlOutParameter("return_code", Types.NUMERIC),
                new SqlOutParameter("return_mesg", Types.VARCHAR)
        );
        
        //one-time initialization of the call, do in a try/catch so that a database
        //issue during init doesn't cause init to fail
        try {
            this.isisAuthenticateCall.compile();
        }
        catch (Exception e) {
            this.logger.warn("Exception while compiling " + this.isisAuthenticateCall.getProcedureName() + ". The compile will be attempted again later.", e);
        }
    }

    protected final void setupGetAddressCall(JdbcTemplate jdbcTemplate) {
        this.isisGetAddressCall = new SimpleJdbcCall(jdbcTemplate);
        
        //EASI2.ISIS_PERS_INFO
        //PROCEDURE GET_ADDRESS (
        //  emplid           IN VARCHAR2,
        //  address1         OUT VARCHAR2,
        //  address2         OUT VARCHAR2,
        //  address3         OUT VARCHAR2,
        //  address4         OUT VARCHAR2,
        //  city             OUT VARCHAR2,
        //  state            OUT VARCHAR2,
        //  zip         OUT VARCHAR2,
        //  return_code OUT NUMBER,
        //  return_mesg OUT VARCHAR2);
        
        this.isisGetAddressCall.withProcedureName("EASI2.ISIS_PERS_INFO.get_address");
        this.isisGetAddressCall.withoutProcedureColumnMetaDataAccess();
        this.isisGetAddressCall.declareParameters(
                new SqlParameter("emplid", Types.VARCHAR),
                
                new SqlOutParameter("address1", Types.VARCHAR),
                new SqlOutParameter("address2", Types.VARCHAR),
                new SqlOutParameter("address3", Types.VARCHAR),
                new SqlOutParameter("address4", Types.VARCHAR),
                new SqlOutParameter("city", Types.VARCHAR),
                new SqlOutParameter("state", Types.VARCHAR),
                new SqlOutParameter("zip", Types.VARCHAR),
                new SqlOutParameter("return_code", Types.NUMERIC),
                new SqlOutParameter("return_mesg", Types.VARCHAR)
        );
        
        //one-time initialization of the call, do in a try/catch so that a database
        //issue during init doesn't cause init to fail
        try {
            this.isisGetAddressCall.compile();
        }
        catch (Exception e) {
            this.logger.warn("Exception while compiling " + this.isisGetAddressCall.getProcedureName() + ". The compile will be attempted again later.", e);
        }
    }

    protected final void setupGetPhoneCall(JdbcTemplate jdbcTemplate) {
        this.isisGetPhoneCall = new SimpleJdbcCall(jdbcTemplate);

        //EASI2.ISIS_PERS_INFO
        //PROCEDURE GET_PHONE (
        //  emplid           IN VARCHAR2,
        //  mail         OUT VARCHAR2,
        //  cell         OUT VARCHAR2,
        //  return_code OUT NUMBER,
        //  return_mesg OUT VARCHAR2);

        this.isisGetPhoneCall.withProcedureName("EASI2.ISIS_PERS_INFO.get_phone");
        this.isisGetPhoneCall.withoutProcedureColumnMetaDataAccess();
        this.isisGetPhoneCall.declareParameters(
                new SqlParameter("emplid", Types.VARCHAR),
                
                new SqlOutParameter("mail", Types.VARCHAR),
                new SqlOutParameter("cell", Types.VARCHAR),
                new SqlOutParameter("return_code", Types.NUMERIC),
                new SqlOutParameter("return_mesg", Types.VARCHAR)
        );
        
        //one-time initialization of the call, do in a try/catch so that a database
        //issue during init doesn't cause init to fail
        try {
            this.isisGetPhoneCall.compile();
        }
        catch (Exception e) {
            this.logger.warn("Exception while compiling " + this.isisGetPhoneCall.getProcedureName() + ". The compile will be attempted again later.", e);
        }
    }

    /* (non-Javadoc)
	 * @see edu.wisc.portlet.cg.dao.jdbc.IsisSingleSignOnDao#getIsisToken(java.lang.String, java.lang.String, java.lang.String)
	 */
/*    @Override
    public IsisLoginToken getIsisLoginToken(final String ipStr, final String emplid, final String tokenType) {
        final Map<String, Object> args = new LinkedHashMap<String, Object>();
        args.put("emplid", emplid);
        args.put("m_ip", ipStr);
        args.put("token_type", tokenType);
        
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Generating login token for: " + args);
        }

        final Map<String, Object> results = this.isisAuthenticateCall.execute(args);
        
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Retrieved " + results + " for: " + args);
        }
        
        final IsisResponseStatus responseStatus = this.getResponseStatus(results);
        
        final String token = (String) results.get("token");
        final IsisLoginToken isisLoginToken = new IsisLoginToken(responseStatus, token);
		
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Generated login token " + isisLoginToken + " for: " + args);
        }

        return isisLoginToken;
	}*/
    
    @Override
    public ContactAddress getAddress(final String emplid) {
        final Map<String, Object> args = new LinkedHashMap<String, Object>();
        args.put("emplid", emplid);
        
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Getting address for: " + args);
        }

        final Map<String, Object> results = this.isisGetAddressCall.execute(args);
        
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Retrieved " + results + " for: " + args);
        }
        
        
        final ContactAddress isisAddress = new ContactAddress();
        
        for (int i = 1 ; i < 5; i++) {
          String line = StringUtils.trim((String)results.get("address" + i));
          if (!StringUtils.isBlank(line)) 
            isisAddress.getAddressLines().add(line);
        }
        isisAddress.setCity((String)results.get("city"));
        isisAddress.setState((String)results.get("state"));
        isisAddress.setPostalCode((String)results.get("zip"));
        isisAddress.setType("Student Home");
        
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Retrieved " + isisAddress + " for: " + args);
        }

        return isisAddress;
    }
    
    @Override
    public List<TypeValue> getPhone(final String emplid) {
        final Map<String, Object> args = new LinkedHashMap<String, Object>();
        args.put("emplid", emplid);
        
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Getting phone for: " + args);
        }

        final Map<String, Object> results = this.isisGetPhoneCall.execute(args);
        
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Retrieved " + results + " for: " + args);
        }
        List<TypeValue> phones = new ArrayList<TypeValue>();
        
        String mailPhoneValue = StringUtils.trim((String)results.get("mail"));
        if(!StringUtils.isBlank(mailPhoneValue)) {
          phones.add(new TypeValue("Student Mail", mailPhoneValue));
        }
        
        String cellPhoneValue = StringUtils.trim((String)results.get("cell"));
        if(!StringUtils.isBlank(cellPhoneValue)) {
          phones.add(new TypeValue("Student Cell", cellPhoneValue));
        }
        
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Retrieved " + phones + " for: " + args);
        }

        return phones;
    }

    /* (non-Javadoc)
     * @see edu.wisc.portlet.isis.dao.IsisDao#isIsisAvailable()
     */
    @Override
    public boolean isIsisAvailable() {
        try {
            final int result = this.simpleJdbcTemplate.queryForInt("SELECT 1 FROM DUAL");
            return result == 1;
        }
        catch (DataAccessException dae) {
            this.logger.warn("isisAvailable check failed due to exception, returning false", dae);
            return false;
        }
    }

    @Override
    public void updateAddress(String emplid) {
        //should do nothing
    }

    @Override
    public void updatePhone(String emplid) {
        //should do nothing
    }
}
