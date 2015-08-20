package edu.wisc.my.profile.emergencyPhone.dao;

import javax.sql.DataSource;

import edu.wisc.my.profile.KeyValueDataStoreDao;
import edu.wisc.my.profile.model.TypeValue;

public class EmergencyPhoneNumberDaoImpl extends KeyValueDataStoreDao<TypeValue[]> implements EmergencyPhoneNumberDao {
  
  
  public EmergencyPhoneNumberDaoImpl(DataSource ds) {
      super(ds);
      super.setPostFix("emergencyPhoneNumber");
  }
  
  @Override
  public TypeValue[] getData(String key) {
      return super.getData(key, new EmergencyPhoneNumberExtractor());
  }
  
}
