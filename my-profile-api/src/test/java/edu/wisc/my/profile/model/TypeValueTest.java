package edu.wisc.my.profile.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TypeValueTest {
    
    TypeValue typeValue1;
    
    private final String type = "type";
    private final String value = "value";
    
    @Before
    public void setUp(){
        typeValue1 = new TypeValue();
        typeValue1.setType(type);
        typeValue1.setValue(value);
    }
    
    @Test
    public void testEquals(){
        TypeValue typeValue2 = new TypeValue();
        typeValue2.setType(type);
        typeValue2.setValue(value);
        assertTrue(typeValue1.equals(typeValue2));
        assertTrue(typeValue2.equals(typeValue1));
        assertTrue(typeValue1.equals(typeValue1));
    }
    
    @Test
    public void testEqualsNullValue(){
        TypeValue typeValue2 = new TypeValue();
        typeValue2.setType(type);
        typeValue2.setValue(null);
        assertFalse(typeValue1.equals(typeValue2));
        assertFalse(typeValue2.equals(typeValue1));
    }
    
    @Test
    public void testEqualsNullType(){
        TypeValue typeValue2 = new TypeValue();
        typeValue2.setType(null);
        typeValue2.setValue(value);
        assertFalse(typeValue1.equals(typeValue2));
        assertFalse(typeValue2.equals(typeValue1));
    }
    
    @Test
    public void testEqualsNullObject(){
        TypeValue typeValue2 = null;
        assertFalse(typeValue1.equals(typeValue2));
    }
    
    @Test
    public void testEqualsDifferentObject(){
        String test = "Hello";
        assertFalse(typeValue1.equals(test));
    }
    
    @Test
    public void testEqualsDifferentType(){
        TypeValue typeValue2 = new TypeValue();
        typeValue2.setType(type+"a");
        typeValue2.setValue(value);
        assertFalse(typeValue1.equals(typeValue2));
        assertFalse(typeValue2.equals(typeValue1));
        
        typeValue2.setType(type+" ");
        assertFalse(typeValue1.equals(typeValue2));
        assertFalse(typeValue2.equals(typeValue1));
        
        typeValue2.setType(" ");
        assertFalse(typeValue1.equals(typeValue2));
        assertFalse(typeValue2.equals(typeValue1));
        
        typeValue2.setType("");
        assertFalse(typeValue1.equals(typeValue2));
        assertFalse(typeValue2.equals(typeValue1));
    }
    
    @Test
    public void testEqualsDifferentValue(){
        TypeValue typeValue2 = new TypeValue();
        typeValue2.setValue(value+"a");
        typeValue2.setType(type);
        assertFalse(typeValue1.equals(typeValue2));
        assertFalse(typeValue2.equals(typeValue1));
        
        typeValue2.setValue(value+" ");
        assertFalse(typeValue1.equals(typeValue2));
        assertFalse(typeValue2.equals(typeValue1));
        
        typeValue2.setValue(" ");
        assertFalse(typeValue1.equals(typeValue2));
        assertFalse(typeValue2.equals(typeValue1));
        
        typeValue2.setValue("");
        assertFalse(typeValue1.equals(typeValue2));
        assertFalse(typeValue2.equals(typeValue1));
    }
  
}
