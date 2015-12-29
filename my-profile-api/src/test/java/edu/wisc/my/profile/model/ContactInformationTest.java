package edu.wisc.my.profile.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ContactInformationTest {
    
    ContactInformation contact1;
    ContactInformation contact2;
    private final String id = "ID";
    private final String legalName = "Foo Bar II";
    private final String preferredName = "Foozy the Bar";
    private final String relationship = "Trusted Friend";
    private final String comment = "This is a comment";
    private final List<TypeValue> phoneNumbers = Arrays.asList(new TypeValue("mobile", "6082621234"), new TypeValue("home", "6082629876"));
    private final List<TypeValue> emails = Arrays.asList(new TypeValue("email", "foo@bar.com"), new TypeValue("email", "bar@foobaz.com"));
    private List<ContactAddress> addresses = Arrays.asList(new ContactAddress(), new ContactAddress());
    
    @Before
    public void setUp(){
        contact1 = new ContactInformation();
        contact2 = new ContactInformation();
        contact1.setId(id);
        contact1.setLegalName(legalName);
        contact1.setPreferredName(preferredName);
        contact1.setRelationship(relationship);
        contact1.setComments(comment);
        contact1.setPhoneNumbers(phoneNumbers);
        contact1.setEmails(emails);
        contact1.setAddresses(addresses);
        contact2.setId(id);
        contact2.setLegalName(legalName);
        contact2.setPreferredName(preferredName);
        contact2.setRelationship(relationship);
        contact2.setComments(comment);
        contact2.setPhoneNumbers(phoneNumbers);
        contact2.setEmails(emails);
        contact2.setAddresses(addresses);
    }
    
    
    
    @Test
    public void testEquals(){
        assertTrue(contact1.equals(contact2));
        assertTrue(contact2.equals(contact1));
        assertTrue(contact1.equals(contact1));
    }
    
    @Test
    public void testEqualsNullObject(){
        contact2 = null;
        assertFalse(contact1.equals(contact2));
    }
    
    @Test
    public void testEqualsNotContactAddressObject(){
        assertFalse(contact1.equals("This is a string"));
    }
    
    @Test
    public void testEqualsId(){
        //Different String
        contact2.setId(id+" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Empty String
        contact2.setId("");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Only 1 space string
        contact2.setId(" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Compare empty string with 1 space
        contact1.setId("");
        contact2.setId(" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Null string
        contact2.setId(null);
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
    }
    
    @Test
    public void testEqualsLegalName(){
        //Different String
        contact2.setLegalName(legalName+" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Empty String
        contact2.setLegalName("");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Only 1 space string
        contact2.setLegalName(" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Compare empty string with 1 space
        contact1.setLegalName("");
        contact2.setLegalName(" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Null string
        contact2.setLegalName(null);
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
    }

    @Test
    public void testEqualsPreferredName(){
        //Different String
        contact2.setPreferredName(preferredName+" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Empty String
        contact2.setPreferredName("");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Only 1 space string
        contact2.setPreferredName(" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Compare empty string with 1 space
        contact1.setPreferredName("");
        contact2.setPreferredName(" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Null string
        contact2.setPreferredName(null);
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
    }
    
    @Test
    public void testEqualsRelationship(){
        //Different String
        contact2.setRelationship(relationship+" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Empty String
        contact2.setRelationship("");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Only 1 space string
        contact2.setRelationship(" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Compare empty string with 1 space
        contact1.setRelationship("");
        contact2.setRelationship(" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Null string
        contact2.setRelationship(null);
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
    }
    
    @Test
    public void testEqualsComments(){
        //Different String
        contact2.setComments(comment+" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Empty String
        contact2.setComments("");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Only 1 space string
        contact2.setComments(" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Compare empty string with 1 space
        contact1.setComments("");
        contact2.setComments(" ");
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
        //Null string
        contact2.setComments(null);
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
    }
    
    @Test
    public void testEqualsPhoneNumbers(){
        contact2.setPhoneNumbers(emails);
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
    }
    
    @Test
    public void testEqualsEmails(){
        contact2.setPhoneNumbers(emails);
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
    }
    
    @Test
    public void testEqualsAddresses(){
        contact2.setAddresses(Arrays.asList(new ContactAddress()));
        assertFalse(contact1.equals(contact2));
        assertFalse(contact2.equals(contact1));
    }
    
    
}
