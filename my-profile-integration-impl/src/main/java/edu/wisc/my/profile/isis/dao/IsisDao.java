package edu.wisc.my.profile.isis.dao;

import java.util.List;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;

import edu.wisc.my.profile.model.ContactAddress;
import edu.wisc.my.profile.model.TypeValue;

/**
 * APIs for accessing data from ISIS (Copied from IsisPortlets)
 * 
 * @author Eric Dalquist
 * @version $Revision: 1.4 $
 */
public interface IsisDao {

    /**
     * Generate a login token for ISIS
     * 
     * @param ipStr User's IP Address
     * @param emplid User's emplid
     * @param tokenType What type of token to generate
     * @return
     */
    //IsisLoginToken getIsisLoginToken(String ipStr, String emplid, String tokenType);

    /**
     * @return true or false if isis is available or not
     */
    @Cacheable(
            cacheName = "isisAvailableCache",
            exceptionCacheName = "isisAvailableExceptionCache", //Cache exceptions to avoid repeated trying on a dead connection
            keyGenerator = @KeyGenerator (name = "StringCacheKeyGenerator"),
            selfPopulating = true)
    boolean isIsisAvailable();

    /**
     * Get the current address information for the user
     * 
     * @param emplid User's emplid
     * @return The user's address information
     */
    @Cacheable(
            cacheName = "isisAddressCache",
            keyGenerator = @KeyGenerator (
                    name = "StringCacheKeyGenerator", 
                    properties = {
                            @Property(name="includeMethod", value="false")
                    }
            )
    )
    ContactAddress getAddress(final String emplid);
    
    /**
     * Should be a noop, just marks that the user is going to update their address, used to ensure cached
     * data is cleared
     * 
     * @param emplid User's emplid
     */
    @TriggersRemove(
        cacheName = "isisAddressCache",
        keyGenerator = @KeyGenerator (
                name = "StringCacheKeyGenerator", 
                properties = {
                        @Property(name="includeMethod", value="false")
                }
        )
    )
    void updateAddress(final String emplid);

    /**
     * Get the current phone information for the user
     * 
     * @param emplid User's emplid
     * @return The user's phone information
     */
    @Cacheable(
            cacheName = "isisPhoneCache",
            keyGenerator = @KeyGenerator (
                    name = "StringCacheKeyGenerator", 
                    properties = {
                            @Property(name="includeMethod", value="false")
                    }
            )
    )
    List<TypeValue> getPhone(final String emplid);
    
    /**
     * Should be a noop, just marks that the user is going to update their phone, used to ensure cached
     * data is cleared
     * 
     * @param emplid User's emplid
     */
    @TriggersRemove(
            cacheName = "isisPhoneCache",
            keyGenerator = @KeyGenerator (
                    name = "StringCacheKeyGenerator", 
                    properties = {
                            @Property(name="includeMethod", value="false")
                    }
            )
    )
    void updatePhone(final String emplid);
}