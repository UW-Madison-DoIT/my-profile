package edu.wisc.my.profile.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class ContactAddressTest {
    
    ContactAddress address1;
    ContactAddress address2;
    private final String type = "type";
    private final List<String> addressLines = Arrays.asList("One Two", "Three Four");
    private final String city = "city";
    private final String country = "country";
    private final String state = "state";
    private final String postalCode = "12345";
    private final String comment = "This is a comment";
    private final DateTime effectiveDate = DateTime.now();
    private final DateTime expirationDate = DateTime.now();
    
    @Before
    public void setUp(){
        address1 = new ContactAddress();
        address2 = new ContactAddress();
        address1.setType(type);
        address1.setAddressLines(addressLines);
        address1.setCity(city);
        address1.setCountry(country);
        address1.setState(state);
        address1.setPostalCode(postalCode);
        address1.setComment(comment);
        address1.setEffectiveDate(effectiveDate);
        address1.setExpirationDate(expirationDate);
        address2.setType(type);
        address2.setAddressLines(addressLines);
        address2.setCity(city);
        address2.setCountry(country);
        address2.setState(state);
        address2.setPostalCode(postalCode);
        address2.setComment(comment);
        address2.setEffectiveDate(effectiveDate);
        address2.setExpirationDate(expirationDate);
    }
    
    @Test
    public void testEquals(){
        assertTrue(address1.equals(address2));
        assertTrue(address2.equals(address1));
        assertTrue(address1.equals(address1));
    }
    
    @Test
    public void testEqualsNullObject(){
        address2 = null;
        assertFalse(address1.equals(address2));
    }
    
    @Test
    public void testEqualsNotContactAddressObject(){
        assertFalse(address1.equals("This is a string"));
    }
    
    @Test
    public void testEqualsType(){
        //Different String
        address2.setType(type+" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Empty String
        address2.setType("");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Only 1 space string
        address2.setType(" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Compare empty string with 1 space
        address1.setType("");
        address2.setType(" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Null string
        address2.setType(null);
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
    }
    
    @Test
    public void testEqualsAddressLines(){
        //Empty List
        List<String> emptyList = Arrays.asList("", "");
        address2.setAddressLines(emptyList);
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Null List
        address2.setAddressLines(null);
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
    }
    
    @Test
    public void testEqualsCity(){
        //Different String
        address2.setCity(city+" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Empty String
        address2.setCity("");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Only 1 space string
        address2.setCity(" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Compare empty string with 1 space
        address1.setCity("");
        address2.setCity(" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Null string
        address2.setCity(null);
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
    }
    
    @Test
    public void testEqualsCountry(){
        //Different String
        address2.setCountry(country+" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Empty String
        address2.setCountry("");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Only 1 space string
        address2.setCountry(" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Compare empty string with 1 space
        address1.setCountry("");
        address2.setCountry(" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Null string
        address2.setCountry(null);
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
    }
    
    @Test
    public void testEqualsState(){
        //Different String
        address2.setState(state+" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Empty String
        address2.setState("");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Only 1 space string
        address2.setState(" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Compare empty string with 1 space
        address1.setState("");
        address2.setState(" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Null string
        address2.setState(null);
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
    }
    
    @Test
    public void testEqualsPostalCode(){
        //Different String
        address2.setPostalCode(postalCode+" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Empty String
        address2.setPostalCode("");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Only 1 space string
        address2.setPostalCode(" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Compare empty string with 1 space
        address1.setPostalCode("");
        address2.setPostalCode(" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Null string
        address2.setPostalCode(null);
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
    }
    
    @Test
    public void testEqualsComment(){
        //Different String
        address2.setComment(comment+" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Empty String
        address2.setComment("");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Only 1 space string
        address2.setComment(" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Compare empty string with 1 space
        address1.setComment("");
        address2.setComment(" ");
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Null string
        address2.setComment(null);
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
    }
    
    @Test
    public void testEqualsEffectiveDate(){
        //Different EffectiveDate
        //Add 1 minute in case tests run lightning fast
        address2.setEffectiveDate(DateTime.now().plusMinutes(1));
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Null Date
        address2.setEffectiveDate(null);
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
    }
    
    @Test
    public void testEqualsExpirationDate(){
        //Different EffectiveDate
        //Subtract 1 minute in case tests run lightning fast
        address2.setExpirationDate(DateTime.now().minusMinutes(1));
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
        //Null Date
        address2.setExpirationDate(null);
        assertFalse(address1.equals(address2));
        assertFalse(address2.equals(address1));
    }
    
}
