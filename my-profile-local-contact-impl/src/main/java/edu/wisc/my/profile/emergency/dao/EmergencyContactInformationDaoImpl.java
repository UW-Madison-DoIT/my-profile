package edu.wisc.my.profile.emergency.dao;

import javax.sql.DataSource;

import edu.wisc.my.profile.KeyValueDataStoreDao;
import edu.wisc.my.profile.model.ContactInformation;

public class EmergencyContactInformationDaoImpl extends KeyValueDataStoreDao<ContactInformation[]>  implements EmergencyContactInformationDao {

  public EmergencyContactInformationDaoImpl(DataSource ds) {
    super(ds);
    super.setPostFix("emergencyContact");
  }

  @Override
  public ContactInformation[] getData(String key) {
    return super.getData(key, new ContactInformationArrayExtractor());
  }
}
